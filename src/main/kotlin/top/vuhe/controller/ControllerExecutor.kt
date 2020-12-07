package top.vuhe.controller

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context
import top.vuhe.model.entity.Question
import java.util.concurrent.*

object ControllerExecutor {
    private val log: Logger = LoggerFactory.getLogger(ControllerExecutor::class.java)
    private val pool: ExecutorService

    init {
        // CachedThreadPool 线程池
        pool = ThreadPoolExecutor(
            0, Int.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            SynchronousQueue()
        ) { r: Runnable -> Thread(r, "ControllerThread") }
    }

    fun invokeLater(task: Runnable): Future<*> {
        return pool.submit(task)
    }

    fun <T> invokeLater(task: Callable<T>): Future<T>? {
        return pool.submit(task)
    }

    /**
     * 创建新习题任务
     */
    fun buildQuestion(): Future<*> {
        return invokeLater {
            log.info("创建线程更新习题")
            val questionFactory: Factory<Question> = QuestionFactory
            val question = questionFactory.produce()
            Context.question = question
            log.info("创建完成")
        }
    }
}