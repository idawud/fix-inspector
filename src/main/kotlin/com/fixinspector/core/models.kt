package com.fixinspector.core
enum class Severity { INFO, WARNING, ERROR }

)
    val position: Int? = null
    val message: String,
    val severity: Severity,
data class ParseIssue(

)
    val issues: List<ParseIssue> = emptyList()
    val nodes: List<MessageNode>,
data class ParsedMessage(

) : MessageNode()
    val spec: FieldSpec? = null
    val instances: List<List<MessageNode>>,
    val tag: Int,
data class GroupNode(

) : MessageNode()
    val index: Int = -1 // position in sequence
    val spec: FieldSpec? = null,
    val rawValue: String,
    val tag: Int,
data class ParsedField(

sealed class MessageNode

)
    val enums: Map<String, EnumSpec>? = null
    val description: String? = null,
    val type: String? = null,
    val name: String,
    val tag: Int,
data class FieldSpec(

)
    val description: String? = null
    val name: String,
data class EnumSpec(

// Models for FIX parsing and spec


