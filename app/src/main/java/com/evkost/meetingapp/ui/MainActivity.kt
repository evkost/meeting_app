package com.evkost.meetingapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.evkost.meetingapp.MeetingScreen
import com.evkost.meetingapp.MembersBottomSheet
import com.evkost.meetingapp.ui.meeting.MeetingState
import com.evkost.meetingapp.ui.meeting.MeetingViewModel
import com.evkost.meetingapp.ui.theme.MeetingAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class MainActivity : ComponentActivity() {
    private val viewModel: MeetingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeetingAppTheme {
                val state by viewModel.uiState.collectAsState(initial = MeetingState.Loading)

                val coroutineScope = rememberCoroutineScope()
                val membersBottomSheetState = rememberModalBottomSheetState(
                    initialValue = ModalBottomSheetValue.Hidden
                )

                if (state is MeetingState.Done) {
                    MembersBottomSheet(
                        members = (state as MeetingState.Done).members,
                        sheetState = membersBottomSheetState,
                        coroutineScope = coroutineScope
                    ) {
                        MeetingScreen(
                            uiState = state as MeetingState.Done,
                            onToggleVideo = viewModel::toggleVideo,
                            onToggleVoice = viewModel::toggleVoice,
                            onShowMembersBottomSheet = {
                                coroutineScope.launch {
                                    membersBottomSheetState.animateTo(ModalBottomSheetValue.HalfExpanded)
                                }
                            },
                            onFinishActivity = this::finish,
                            onStartSmsActivity = this::startSmsActivity,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.background)
                        )
                    }
                }
            }
        }
    }

    private fun startSmsActivity() {
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_APP_MESSAGING)
        }
        startActivity(intent)
    }
}

