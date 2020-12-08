package top.vuhe.view.window

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context
import top.vuhe.model.Context.question
import top.vuhe.model.entity.Formula
import java.awt.BorderLayout
import java.awt.FlowLayout
import java.awt.GridLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import kotlin.collections.ArrayList

object QuestionPanel : JPanel() {
    init {
        layout = BorderLayout(5, 5)
        add(FormulasPanel, BorderLayout.CENTER)
        add(FunctionPanel, BorderLayout.SOUTH)
    }

    /**
     * 刷新
     */
    fun refresh() {
        FormulasPanel.update()
        FunctionPanel.update()
    }
}

object FormulasPanel : JPanel() {
    private val log: Logger = LoggerFactory.getLogger(FormulasPanel::class.java)
    private val labels: List<FormulaComponent>

    init {
        layout = GridLayout(10, 5, 5, 5)
        val list = ArrayList<FormulaComponent>(Context.FORMULA_NUM + 1)
        for (i in 0 until Context.FORMULA_NUM) {
            val formulaComponent = FormulaComponent()
            add(formulaComponent)
            list.add(formulaComponent)
        }
        labels = list
    }

    /**
     * 循环调用标签中的显示方法
     */
    fun showAns() {
        log.info("显示所有算式答案")
        for (i in labels) {
            i.showAns()
        }
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
        private var formula: Formula? = null
        private val formulaText = JLabel()
        private val ansText = JLabel()

        init {
            setSize(100, 10)
            add(formulaText)
            add(ansText)
            // 设置题目加载
            formulaText.text = "加载中……"
            // 默认不显示答案
            ansText.isVisible = false
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
         * @param formula 算式
         */
        fun setFormula(formula: Formula) {
            // 设置问题文字
            formulaText.text = formula.toString()

            // 设置答案文字
            ansText.text = formula.ans.toString()
            // 默认不显示答案
            ansText.isVisible = false

            // 记录算式
            this.formula = formula
        }
    }
}

object FunctionPanel: JPanel() {
    private val log: Logger = LoggerFactory.getLogger(FunctionPanel::class.java)
    private val showAns = JButton("显示答案")
    init {
        layout = FlowLayout(FlowLayout.CENTER)

        // 目前仅有一个显示答案的按钮
        // 在显示答案之后会将按钮禁用
        showAns.addActionListener {
            val panel = FormulasPanel
            panel.showAns()
            showAns.isEnabled = false
            log.info("显示答案（button）")
        }

        // 添加
        add(showAns)

        log.info("获取功能按钮面板")
    }

    /**
     * 用于接受来自UI刷新的通知
     */
    fun update() {
        showAns.isEnabled = true
    }
}