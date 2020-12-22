package top.vuhe.view

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.controller.ControllerUnit.buildQuestion
import top.vuhe.view.window.LoadingPanel
import top.vuhe.view.window.QuestionPanel
import java.awt.CardLayout
import javax.swing.JFrame

object MainFrame : JFrame("加减法口算练习系统") {
    private val log: Logger = LoggerFactory.getLogger(MainFrame::class.java)
    private val CARD_LAYOUT = CardLayout()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(550, 400)
        isResizable = false
        layout = CARD_LAYOUT

        // 设置两个切换页面
        add(LoadingPanel, "loading")
        add(QuestionPanel, "question")

        // 默认显示加载中
        CARD_LAYOUT.show(contentPane, "loading")

        // 准备好后再显示，减少空白等待时间
        isVisible = true
    }

    /**
     * 刷新主页面
     */
    fun refresh() {
        log.info("刷新主页面")
        startLoading()

        // 等待题目生成完毕
        buildQuestion()

        endLoading()
        log.info("主页面刷新完成")
    }

    /**
     * 开始加载
     */
    private fun startLoading() {
        // 显示加载
        CARD_LAYOUT.show(contentPane, "loading")
    }

    /**
     * 完成加载
     */
    private fun endLoading() {
        // 刷新面板信息
        QuestionPanel.refresh()

        // 显示题目
        CARD_LAYOUT.show(contentPane, "question")
    }
}