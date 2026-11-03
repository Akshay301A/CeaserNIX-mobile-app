package com.example.data

import com.example.model.*
import com.squareup.moshi.JsonClass
import retrofit2.Response
import retrofit2.http.*

// -------------------------------------------------------------
// DTOs matching CEASER Ecosystem API Contracts
// -------------------------------------------------------------

@JsonClass(generateAdapter = true)
data class DeviceStatusResponse(
    val deviceId: String,
    val name: String,
    val status: String,
    val batteryGlasses: Int,
    val batteryCase: Int,
    val firmwareVersion: String,
    val isCharging: Boolean,
    val audioRouting: String
)

@JsonClass(generateAdapter = true)
data class DeviceUpdateRequest(
    val audioRouting: String? = null,
    val ancMode: String? = null,
    val wearDetection: Boolean? = null,
    val touchControls: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class NixQueryRequest(
    val prompt: String,
    val conversationId: String? = null,
    val hardwareContext: Map<String, String>? = null
)

@JsonClass(generateAdapter = true)
data class NixQueryResponse(
    val messageId: String,
    val responseText: String,
    val codeSnippet: String? = null,
    val suggestedActions: List<String> = emptyList(),
    val timestamp: String
)

@JsonClass(generateAdapter = true)
data class UserLoginRequest(
    val email: String,
    val password: String
)

@JsonClass(generateAdapter = true)
data class UserAuthResponse(
    val userId: String,
    val name: String,
    val email: String,
    val token: String,
    val ecosystemTier: String
)

// -------------------------------------------------------------
// Retrofit API Service Interface
// -------------------------------------------------------------

interface CeaserApiService {

    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body request: UserLoginRequest
    ): Response<UserAuthResponse>

    @GET("/api/v1/devices/glasses/status")
    suspend fun getGlassesStatus(
        @Header("Authorization") bearerToken: String
    ): Response<DeviceStatusResponse>

    @PATCH("/api/v1/devices/glasses/settings")
    suspend fun updateGlassesSettings(
        @Header("Authorization") bearerToken: String,
        @Body request: DeviceUpdateRequest
    ): Response<DeviceStatusResponse>

    @POST("/api/v1/nix/query")
    suspend fun queryNix(
        @Header("Authorization") bearerToken: String,
        @Body request: NixQueryRequest
    ): Response<NixQueryResponse>

    @POST("/api/v1/sync/desktop")
    suspend fun triggerDesktopSync(
        @Header("Authorization") bearerToken: String
    ): Response<Map<String, String>>
}
