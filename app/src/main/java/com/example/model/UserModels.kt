package com.example.model

enum class AppMode {
    DESIGN_DEMO,
    INTEGRATED
}

data class UserProfile(
    val id: String = "usr_ceaser_992",
    val name: String = "Alex Mercer",
    val email: String = "alex.mercer@ceaser.ai",
    val ecosystemRole: String = "Ecosystem Pro • Pioneer",
    val avatarInitial: String = "AM",
    val isSignedIn: Boolean = true
)

data class BackendApiConfig(
    val baseUrl: String = "https://api.ceaser.ecosystem.net",
    val authToken: String = "ceaser_bearer_demo_9843a",
    val timeoutSeconds: Int = 15,
    val isReachable: Boolean = true
)
