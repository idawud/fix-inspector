package com.fixinspector.core

// Models for FIX parsing and spec

enum class Severity { INFO, WARNING, ERROR }

data class ParseIssue(
    val message: String,
    val severity: Severity = Severity.ERROR,
    val position: Int? = null
)

sealed class MessageNode

data class ParsedField(
    val tag: Int,
    val rawValue: String,
    val spec: FieldSpec? = null,
    val index: Int = -1
) : MessageNode()

data class GroupNode(
    val tag: Int,
    val rawValue: String,
    val spec: FieldSpec? = null,
    val instances: List<List<MessageNode>> = emptyList(),
    val index: Int = -1
) : MessageNode()

data class ParsedMessage(
    val nodes: List<MessageNode> = emptyList(),
    val issues: List<ParseIssue> = emptyList()
)

data class FieldSpec(
    val tag: Int,
    val name: String,
    val type: String? = null,
    val description: String? = null,
    val enums: Map<String, EnumSpec>? = null
)

data class EnumSpec(
    val name: String,
    val description: String? = null
)
