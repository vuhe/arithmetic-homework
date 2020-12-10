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
class MainApplication {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(MainApplication::class.java)

        @JvmStatic
        fun main(args: Array<String>) = runBlocking(Dispatchers.Main) {
            log.info("系统初始化")
            MainFrame.refresh()
        }
    }
}
