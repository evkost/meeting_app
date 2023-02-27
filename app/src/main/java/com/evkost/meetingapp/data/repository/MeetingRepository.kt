package com.evkost.meetingapp.data.repository

import com.evkost.meetingapp.model.Member
import com.evkost.meetingapp.model.MemberType
import kotlinx.coroutines.flow.*

class MeetingRepository {
    private val _currentUserStream: MutableStateFlow<Member> = MutableStateFlow(
        Member(
            id = 1,
            voiceEnabled = true,
            videoEnabled = false,
            name = "Chad",
            imageUrl = "https://i1.sndcdn.com/artworks-aWdn8qyz4xKvPiAF-TzcV6A-t500x500.jpg",
            type = MemberType.CURRENT_USER
        )
    )

    val currentUserStream: Flow<Member> = _currentUserStream.asStateFlow()

    val meetingMembersStream: Flow<List<Member>> = flowOf(
        listOf(
            Member(
                id = 2,
                voiceEnabled = false,
                videoEnabled = false,
                name = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
                imageUrl = "https://img.freepik.com/free-vector/colorful-rainbow-background_23-2147805840.jpg?w=2000",
                type = MemberType.ANOTHER_USER
            )
        )
    )

    fun toggleVoice(value: Boolean) {
        _currentUserStream.update { member ->
            member.copy(voiceEnabled = value)
        }
    }

    fun toggleVideo(value: Boolean) {
        _currentUserStream.update { member ->
            member.copy(videoEnabled = value)
        }
    }
}