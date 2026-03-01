package com.fixinspector.intellij

import com.fixinspector.core.FixParser
import com.fixinspector.core.FixSpecLoader
import com.fixinspector.core.ParsedMessage
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorLocation
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import java.beans.PropertyChangeListener
import javax.swing.*

class FixFileEditor(private val project: Project, private val file: VirtualFile) : FileEditor {
    private val component: JComponent
    private val previewPanel: FixPreviewPanel

    init {
        FixSpecLoader.loadFromResource("/fix-spec.json")
        previewPanel = FixPreviewPanel()
        component = previewPanel
        // load file contents and set in preview
        val content = String(file.contentsToByteArray())
        previewPanel.setRawText(content)
        previewPanel.refresh()
    }

    override fun getComponent(): JComponent = component

    override fun getPreferredFocusedComponent(): JComponent? = previewPanel.getPreferredFocusedComponent()

    override fun getName(): String = "FIX Inspector"

    override fun setState(state: com.intellij.openapi.fileEditor.FileEditorState) {}

    override fun isModified(): Boolean = false

    override fun isValid(): Boolean = file.isValid

    override fun addPropertyChangeListener(listener: PropertyChangeListener) {}

    override fun removePropertyChangeListener(listener: PropertyChangeListener) {}

    override fun getCurrentLocation(): FileEditorLocation? = null

    override fun dispose() {}
}

