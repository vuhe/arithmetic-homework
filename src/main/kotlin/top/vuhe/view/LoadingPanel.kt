package top.vuhe.view

import org.slf4j.LoggerFactory
import java.awt.FlowLayout
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * 加载中页面
 *
 * @author vuhe
 */
object LoadingPanel : JPanel() {
    private val log = LoggerFactory.getLogger(LoadingPanel::class.java)

    init {
        layout = FlowLayout(FlowLayout.CENTER)
        add(JLabel("加载中……"))
        log.info("获取加载页面")
    }
}
