package top.vuhe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.view.MainFrame

/**
 * 主方法类
 *
 * @author vuhe
 */
object MainApplication {
    private val log: Logger = LoggerFactory.getLogger(MainApplication::class.java)

    fun start() {
        log.info("系统初始化")
        MainFrame.refresh()
    }
}

fun main(args: Array<String>) = runBlocking(Dispatchers.Main) {
    MainApplication.start()
}