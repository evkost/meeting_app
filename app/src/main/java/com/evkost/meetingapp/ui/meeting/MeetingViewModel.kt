package com.evkost.meetingapp.ui.meeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evkost.meetingapp.data.repository.MeetingRepository
import com.evkost.meetingapp.model.Member
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

sealed interface MeetingState {
    object Loading : MeetingState

    data class Done(
        val currentUser: Member,
        val members: ImmutableList<Member>
    ) : MeetingState
}

class MeetingViewModel(
    private val meetingRepository: MeetingRepository = MeetingRepository()
) : ViewModel() {
    val uiState: Flow<MeetingState> = combine(
        meetingRepository.currentUserStream, meetingRepository.meetingMembersStream
    ) { currentUser, meetingMembers ->
        MeetingState.Done(
            currentUser = currentUser,
            members = persistentListOf(currentUser).addAll(meetingMembers)
        )
    }

    fun toggleVoice(value: Boolean) {
        viewModelScope.launch {
            meetingRepository.toggleVoice(value)
        }
    }

    fun toggleVideo(value: Boolean) {
        viewModelScope.launch {
            meetingRepository.toggleVideo(value)
        }
    }
}