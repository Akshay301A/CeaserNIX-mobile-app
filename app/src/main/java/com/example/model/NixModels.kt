package com.example.model

enum class Sender {
    USER,
    NIX_ASSISTANT,
    SYSTEM
}

data class ChatMessage(
    val id: String,
    val sender: Sender,
    val text: String,
    val timestamp: String,
    val codeSnippet: String? = null,
    val suggestedActions: List<String> = emptyList(),
    val isAudioResponse: Boolean = false,
    val hardwareContextNote: String? = null
)

data class PromptChip(
    val id: String,
    val label: String,
    val prompt: String
)
