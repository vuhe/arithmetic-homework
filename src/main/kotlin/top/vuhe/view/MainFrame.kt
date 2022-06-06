package top.vuhe.view

import top.vuhe.tool.Serialization
import top.vuhe.Context
import java.awt.CardLayout
import java.io.File
import javax.swing.JFrame

object MainFrame : JFrame("加减法口算练习系统") {
    private val CARD_LAYOUT = CardLayout()

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(800, 500)
        isResizable = false
        layout = CARD_LAYOUT

        // 设置三个切换页面
        add(OperationPanel, "operation")
        add(LoadingPanel, "loading")
        add(QuestionPanel, "question")

        // 默认进入是为选择界面
        CARD_LAYOUT.show(contentPane, "operation")

        // 准备好后再显示，减少空白等待时间
        isVisible = true
    }

    fun loading() {
        startLoading()

        Serialization.readQuestionFromFile(
            File("${Context.FILE_PATH}/${Context.file}")
        )

        endLoading()
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
        QuestionPanel.build()

        // 显示题目
        CARD_LAYOUT.show(contentPane, "question")
    }
}
