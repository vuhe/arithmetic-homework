package top.vuhe.view.menu

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import top.vuhe.model.Context.setProportionNumber
import top.vuhe.view.MainFrame
import javax.swing.ButtonGroup
import javax.swing.JMenu
import javax.swing.JRadioButtonMenuItem

object QuestionTypeMenu : JMenu() {
    private val log: Logger = LoggerFactory.getLogger(QuestionTypeMenu::class.java)

    init {
        text = "习题模式"

        // 按钮组
        val questionType = ButtonGroup()

        // 默认选中 50% 50% 混合模式
        val mix: JRadioButtonMenuItem = TypeRadioButton.HalfTypeButton
        mix.isSelected = true
        questionType.add(mix)
        add(mix)

        // 全加法模式
        val plus: JRadioButtonMenuItem = TypeRadioButton.AllAddTypeButton
        questionType.add(plus)
        add(plus)

        // 全减法模式
        val minus: JRadioButtonMenuItem = TypeRadioButton.AllSubTypeButton
        questionType.add(minus)
        add(minus)

        log.info("创建「习题模式」菜单")
    }
}

sealed class TypeRadioButton(name: String, plus: Int, minus: Int) : JRadioButtonMenuItem() {
    init {
        text = name
        addActionListener {
            // 更改全局比例
            setProportionNumber(plus, minus)

            // 通知主视图更新信息
            MainFrame.refresh()
        }
    }

    class MixTypeButton(p: Int, m: Int) : TypeRadioButton("混合题目", p, m)
    object HalfTypeButton : TypeRadioButton("混合题目", 50, 50)
    object AllAddTypeButton : TypeRadioButton("全加法题目", 100, 0)
    object AllSubTypeButton : TypeRadioButton("全减法题目", 0, 100)

}