package top.vuhe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.view.MainFrame


private val log: Logger = LoggerFactory.getLogger("main")

fun main(args: Array<String>) = runBlocking(Dispatchers.Main) {
    log.info("系统初始化")
    val start = MainFrame
}
