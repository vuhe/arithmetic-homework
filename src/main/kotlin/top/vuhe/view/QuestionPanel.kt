package top.vuhe.view

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.tool.Serialization
import top.vuhe.Context.FORMULA_NUM
import top.vuhe.Context.question
import top.vuhe.model.Question
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
            labels.forEach { it.checkAns() }
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
        var isDone = true
        question.forEach {
            val f = FormulaComponent(it)
            labels.add(f)
            add(f)
            isDone = isDone &&
                    (it.state == Question.State.Correct ||
                            it.state == Question.State.Wrong)
        }
        if (isDone) {
            FunctionPanel.isDone()
        }
    }

    fun reset() {
        labels.forEach { it.reset() }
    }

    fun save() {
        labels.forEach { it.save() }
    }

    class FormulaComponent(private val node: Question.Node) : JPanel() {
        private val formulaText = JLabel()
        private val userAns = JTextField(2)
        private val ansText = JLabel()

        /**
         * 属性设置
         */
        init {
            // 设置问题文字
            formulaText.text = node.formula.toString()

            // 设置答案文字
            ansText.text = node.formula.ans.toString()
            // 默认不显示答案
            ansText.isVisible = false

            userAns.addKeyListener(object : KeyAdapter() {
                override fun keyTyped(e: KeyEvent) {
                    //如果不是数字则取消
                    if (!e.keyChar.isDigit()) e.consume()
                }
            })

            // 习题复原
            if (node.state != Question.State.NotDo) {
                userAns.text = node.userAns.toString()
                if (node.state != Question.State.Done) {
                    checkAns()
                }
            }
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

        fun reset() {
            userAns.text = ""
            ansText.isVisible = false
            node.state = Question.State.NotDo
            node.userAns = null
        }

        fun save() {
            // 设置状态
            if (node.state == Question.State.NotDo
                && userAns.text != ""
            ) {
                node.state = Question.State.Done
            }
            if (node.state != Question.State.NotDo) {
                node.userAns = userAns.text.toInt()
            }
        }
    }
}

object FunctionPanel : JPanel() {
    private val log: Logger = LoggerFactory.getLogger(FunctionPanel::class.java)
    private val showAns = JButton("检查答案")
    private val reset = JButton("重置")
    private val save = JButton("保存")

    init {
        // 在显示答案之后会将按钮禁用
        showAns.addActionListener {
            if (FormulasPanel.checkAns()) {
                showAns.isEnabled = false
                log.info("显示答案（button）")
            }
        }
        reset.addActionListener {
            FormulasPanel.reset()
            showAns.isEnabled = true
        }
        save.addActionListener {
            FormulasPanel.save()
            Serialization.writeQuestionToFile(question)
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
    fun isDone() {
        showAns.isEnabled = false
    }
}
