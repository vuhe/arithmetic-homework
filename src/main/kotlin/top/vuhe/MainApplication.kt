package top.vuhe

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.view.MainFrame
import javax.swing.SwingUtilities

/**
 * 主方法类
 *
 * @author vuhe
 */
class MainApplication {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(MainApplication::class.java)

        fun start(args: Array<String>) {
            log.info("系统初始化")
            // UI 线程
            SwingUtilities.invokeLater {
                MainFrame
            }
        }
    }
}

fun main(args: Array<String>) {
    MainApplication.start(args)
}