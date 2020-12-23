package top.vuhe.view.window

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context.FORMULA_NUM
import top.vuhe.model.Context.question
import top.vuhe.model.entity.Question
import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.KeyAdapter
import kotlin.collections.ArrayList
import java.awt.event.KeyEvent
import javax.swing.*


object QuestionPanel : JPanel() {
    init {
        layout = BorderLayout(5, 5)
        add(FormulasPanel, BorderLayout.CENTER)
        add(FunctionPanel, BorderLayout.SOUTH)
    }

    /**
     * 刷新
     */
    fun build() {
        FormulasPanel.update()
        FunctionPanel.update()
    }
}

object FormulasPanel : JPanel() {
    private val log: Logger = LoggerFactory.getLogger(FormulasPanel::class.java)
    private val labels = ArrayList<FormulaComponent>(FORMULA_NUM + 1)

    init {
        layout = GridLayout(10, 5, 5, 5)
    }

    /**
     * 循环调用标签中的显示方法
     */
    fun checkAns(): Boolean {
        log.info("显示所有算式答案")
        val isAllDone = labels.fold(true) { a, label -> a && label.hasUserAns() }
        if (isAllDone) {
            for (i in labels) {
                i.checkAns()
            }
        } else {
            JOptionPane.showMessageDialog(
                null,
                "未做完前不能提交",
                "警告",
                JOptionPane.WARNING_MESSAGE
            )
        }
        return isAllDone
    }

    /**
     * 用于接受来自UI刷新的信息通知
     */
    fun update() {
        val question = question
        for (q in question) {
            val f = FormulaComponent(q)
            labels.add(f)
            add(f)
        }
    }

    class FormulaComponent(private val node: Question.Node) : JPanel() {
        private val formulaText = JLabel()
        private val userAns = JTextField(2)
        private val ansText = JLabel()

        /**
         * 属性设置
         */
        init {

            // TODO-对状态进行复原
            // 设置问题文字
            formulaText.text = node.formula.toString()

            // 设置答案文字
            ansText.text = node.ans.toString()
            // 默认不显示答案
            ansText.isVisible = false

            userAns.addKeyListener(object : KeyAdapter() {
                override fun keyTyped(e: KeyEvent) {
                    val key = "0123456789" + 8.toChar()
                    if (key.indexOf(e.keyChar) < 0) {
                        //如果不是数字则取消
                        e.consume()
                    }
                    // 设置状态
                    node.state = if (userAns.text == "") {
                        Question.State.NotDo
                    } else {
                        Question.State.Done
                    }
                }
            })
        }

        init {
            setSize(100, 10)
            add(formulaText)
            add(userAns)
            add(ansText)
        }

        /**
         * 对一个标签中信息进行替换，显示答案
         */
        fun checkAns() {
            // 禁止再编辑
            userAns.isEditable = false
            val userInput = userAns.text
            // 检查结果，设置状态
            if (userInput == ansText.text) {
                ansText.foreground = Color.GREEN
                node.state = Question.State.Correct
            } else {
                ansText.foreground = Color.RED
                node.state = Question.State.Wrong
            }
            ansText.isVisible = true
        }

        fun hasUserAns(): Boolean {
            return userAns.text != ""
        }
    }
}

object FunctionPanel : JPanel() {
    private val log: Logger = LoggerFactory.getLogger(FunctionPanel::class.java)
    private val showAns = JButton("检查答案")

    // TODO-重置问题
    private val reset = JButton("重置")

    // TODO-保存状态，写入文件
    private val save = JButton("保存")

    init {
        // 在显示答案之后会将按钮禁用
        showAns.addActionListener {
            if (FormulasPanel.checkAns()) {
                showAns.isEnabled = false
                log.info("显示答案（button）")
            }
        }
    }

    init {
        layout = FlowLayout(FlowLayout.CENTER)

        // 添加
        add(showAns)
        add(reset)
        add(save)

        log.info("获取功能按钮面板")
    }

    /**
     * 用于接受来自UI刷新的通知
     */
    fun update() {
        showAns.isEnabled = true
    }
}