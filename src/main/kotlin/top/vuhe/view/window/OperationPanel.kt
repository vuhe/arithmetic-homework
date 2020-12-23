package top.vuhe.view.window

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import top.vuhe.controller.ControllerUnit
import top.vuhe.model.Context
import top.vuhe.view.MainFrame
import java.io.File
import java.util.*
import javax.swing.*

object OperationPanel : JPanel() {
    private val log = LoggerFactory.getLogger(OperationPanel::class.java)
    private val buildQuestion= JButton("创建习题集")
    private val selectedExercise = JComboBox<String>()
    private val startExercise = JButton("开始练习")

    /**
     * 初始化 创建习题按钮
     */
    init {
        buildQuestion.addActionListener {
            ControllerUnit.buildQuestionToFile()
            refreshExerciseList()
        }
    }

    /**
     * 初始化 习题选择框
     */
    init {
        refreshExerciseList()
    }

    /**
     * 初始化 开始练习按钮
     */
    init {
        startExercise.addActionListener {
            if (selectedExercise.selectedIndex <= 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "请先选择一套习题",
                    "警告",
                    JOptionPane.WARNING_MESSAGE)
            } else {
                Context.file = selectedExercise.selectedItem as String
                MainFrame.loading()
            }
        }
    }

    /**
     * 主要页面 初始化
     */
    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)

        add(JLabel("若无习题，请先生成"))
        add(buildQuestion)
        add(selectedExercise)
        add(startExercise)
    }

    private fun refreshExerciseList() {
        selectedExercise.removeAllItems()
        selectedExercise.addItem("请选择一套习题")
        val filesName = LinkedList<String>()
        runBlocking(Dispatchers.IO) {
            val path = File(Context.FILE_PATH)
            if (!path.exists()) {
                path.mkdirs()
            }
            val files = path.listFiles() ?: arrayOf()
            for (f in files) {
                filesName.add(f.name)
            }
        }
        for (name in filesName) {
            selectedExercise.addItem(name)
        }
    }
}