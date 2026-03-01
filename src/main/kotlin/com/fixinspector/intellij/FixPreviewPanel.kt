package com.fixinspector.intellij

import com.fixinspector.core.FixParser
import com.fixinspector.core.ParsedField
import com.fixinspector.core.ParsedMessage
import com.fixinspector.core.ParseIssue
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import javax.swing.*
import javax.swing.table.DefaultTableModel

class FixPreviewPanel : JPanel(BorderLayout()) {
    private val rawTextArea = JTextArea()
    private val tableModel = DefaultTableModel()
    private val table = JTable(tableModel)
    private val refreshButton = JButton("Refresh")

    init {
        rawTextArea.lineWrap = true
        val split = JSplitPane(JSplitPane.HORIZONTAL_SPLIT)
        val left = JBScrollPane(rawTextArea)
        val rightPanel = JPanel(BorderLayout())
        tableModel.addColumn("Tag")
        tableModel.addColumn("FieldName")
        tableModel.addColumn("Value")
        tableModel.addColumn("Description")
        rightPanel.add(JBScrollPane(table), BorderLayout.CENTER)
        rightPanel.add(refreshButton, BorderLayout.NORTH)
        split.leftComponent = left
        split.rightComponent = rightPanel
        split.resizeWeight = 0.5
        add(split, BorderLayout.CENTER)

        refreshButton.addActionListener {
            refresh()
        }
    }

    fun setRawText(text: String) {
        rawTextArea.text = text
    }

    fun getPreferredFocusedComponent(): JComponent? = rawTextArea

    fun refresh(): ParsedMessage {
        val raw = rawTextArea.text
        val parsed = FixParser.parse(raw)
        applyParsed(parsed)
        return parsed
    }

    private fun applyParsed(parsed: ParsedMessage) {
        tableModel.setRowCount(0)
        for (node in parsed.nodes) {
            when (node) {
                is ParsedField -> {
                    val name = node.spec?.name ?: "Unknown"
                    val desc = node.spec?.description ?: ""
                    val enumDesc = node.spec?.enums?.get(node.rawValue)?.name
                    val valueDisplay = if (enumDesc != null) "${node.rawValue} (${enumDesc})" else node.rawValue
                    tableModel.addRow(arrayOf(node.tag.toString(), name, valueDisplay, desc))
                }
                else -> {
                    // group nodes not handled yet
                    tableModel.addRow(arrayOf("group", "", "", ""))
                }
            }
        }

        // show issues in a simple dialog for now
        if (parsed.issues.isNotEmpty()) {
            val msgs = parsed.issues.joinToString("\n") { it.message }
            JOptionPane.showMessageDialog(this, msgs, "Parse issues", JOptionPane.WARNING_MESSAGE)
        }
    }
}

