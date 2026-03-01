package com.fixinspector.intellij

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.fileEditor.FileEditorProvider
import com.intellij.openapi.fileEditor.FileEditorState
import com.intellij.openapi.fileEditor.FileEditorPolicy
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import javax.swing.*

class FixFileEditorProvider : FileEditorProvider {
    override fun accept(project: Project, file: VirtualFile): Boolean {
        val ext = file.extension
        if (ext != null && ext.equals("fix", ignoreCase = true)) return true
        // Fallback: accept files that look like FIX (contain = and digits start)
        val name = file.name
        return name.endsWith(".fix", ignoreCase = true)
    }

    override fun createEditor(project: Project, file: VirtualFile): FileEditor {
        return FixFileEditor(project, file)
    }

    override fun getEditorTypeId(): String = "fix-inspector-editor"

    override fun getPolicy(): FileEditorPolicy = FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR

    override fun readState(sourceElement: org.jdom.Element, project: Project, file: VirtualFile): FileEditorState? = null
}

