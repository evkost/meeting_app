package com.evkost.meetingapp.model

data class Member(
    val id: Long,
    val voiceEnabled: Boolean,
    val videoEnabled: Boolean,
    val imageUrl: String,
    val name: String,
    val type: MemberType
)

enum class MemberType {
    CURRENT_USER, ANOTHER_USER
}