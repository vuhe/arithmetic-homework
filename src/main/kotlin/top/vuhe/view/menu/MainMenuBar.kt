package top.vuhe.view.menu

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.swing.JMenuBar

object MainMenuBar : JMenuBar() {
    private val log: Logger = LoggerFactory.getLogger(MainMenuBar::class.java)

    init {
        add(QuestionTypeMenu)
        log.info("获取菜单栏")
    }
}