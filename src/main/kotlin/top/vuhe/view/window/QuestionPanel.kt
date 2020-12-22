package top.vuhe.view.window

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context.FORMULA_NUM
import top.vuhe.model.Context.question
import top.vuhe.model.entity.Question
import java.awt.BorderLayout
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
    private val labels: List<FormulaComponent>

    init {
        layout = GridLayout(10, 5, 5, 5)
        val list = ArrayList<FormulaComponent>(FORMULA_NUM + 1)
        for (i in 0 until FORMULA_NUM) {
            val formulaComponent = FormulaComponent()
            add(formulaComponent)
            list.add(formulaComponent)
        }
        labels = list
    }

    /**
     * 循环调用标签中的显示方法
     */
    fun showAns():Boolean {
        log.info("显示所有算式答案")
        val isAllDone = labels.fold(true) { a, label -> a && label.hasUserAns() }
        if (isAllDone) {
            for (i in labels) {
                i.showAns()
            }
        } else {
            JOptionPane.showMessageDialog(
                null,
                "未做完前不能提交",
                "警告",
                JOptionPane.WARNING_MESSAGE)
        }
        return isAllDone
    }

    /**
     * 用于接受来自UI刷新的信息通知
     */
    fun update() {
        val question = question
        // 算式 和 算式标签迭代器
        val itProblem = question.iterator()
        val itLabel = labels.iterator()
        // 循环设置
        while (itLabel.hasNext() && itProblem.hasNext()) {
            itLabel.next().setFormula(itProblem.next())
        }
    }

    class FormulaComponent : JPanel() {
        private val formulaText = JLabel()
        private val userAns = JTextField(2)
        private val ansText = JLabel()

        /**
         * 属性设置
         */
        init {
            userAns.addKeyListener(object : KeyAdapter() {
                override fun keyTyped(e: KeyEvent) {
                    val key = "0123456789" + 8.toChar()
                    if (key.indexOf(e.keyChar) < 0) {
                        //如果不是数字则取消
                        e.consume()
                    }
                }
            })
            // 设置题目加载
            formulaText.text = "加载中……"
            // 默认不显示答案
            ansText.isVisible = false
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
        fun showAns() {
            ansText.isVisible = true
        }

        /**
         * 对单个算式标签设置
         *
         * @param node 算式信息
         */
        fun setFormula(node: Question.Node) {
            // 设置问题文字
            formulaText.text = node.formula.toString()

            // 设置答案文字
            ansText.text = node.ans.toString()
            // 默认不显示答案
            ansText.isVisible = false
        }

        fun hasUserAns(): Boolean {
            return userAns.text != ""
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
            if (FormulasPanel.showAns()) {
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