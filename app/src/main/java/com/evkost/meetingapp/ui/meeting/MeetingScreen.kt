package com.evkost.meetingapp.ui.meeting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.evkost.meetingapp.*
import com.evkost.meetingapp.R
import com.evkost.meetingapp.model.Member
import com.evkost.meetingapp.model.MemberType
import com.evkost.meetingapp.ui.meeting.component.*
import kotlinx.collections.immutable.persistentListOf

//@Preview(showBackground = true)
//@Composable
//fun MeetingScreenPreview() {
//    MeetingScreen(
//        uiState = MeetingState.Done(
//            Member(id = 1, false, true, "", "", MemberType.ANOTHER_USER),
//            persistentListOf()
//        ),
//        onToggleVideo = {},
//        onToggleVoice = {},
//        onShowMembersBottomSheet = {},
//        onFinishActivity = {},
//        onStartSmsActivity = {}
//    )
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MeetingScreen(
    uiState: MeetingState.Done,
    onToggleVoice: (Boolean) -> Unit,
    onToggleVideo: (Boolean) -> Unit,
    onShowMembersBottomSheet: () -> Unit,
    onFinishActivity: () -> Unit,
    onStartSmsActivity: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isHelloDialogShowing by remember { mutableStateOf(false) }
    var isReversed by remember { mutableStateOf(false) }
    val members by remember(uiState.members, isReversed) {
        derivedStateOf {
            if (isReversed) {
                uiState.members.reversed()
            } else {
                uiState.members
            }
        }
    }

    if (isHelloDialogShowing) {
        AlertDialog(
            onDismissRequest = { isHelloDialogShowing = false },
            title = {
                Text(text = stringResource(R.string.hello_dialog_title))
            },
            text = {
                Text(text = stringResource(R.string.hello_dialog_text))
            },
            confirmButton = {
                TextButton(
                    onClick = { isHelloDialogShowing = false }
                ) {
                    Text(text = stringResource(R.string.confirm_button_text))
                }
            }
        )
    }

    ConstraintLayout(
        modifier = modifier,
    ) {
        val (topButtonsRow, membersGrid, bottomButtonsRow) = createRefs()

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .constrainAs(topButtonsRow) {
                    top.linkTo(parent.top)
                }
        ) {
            ChatButton(
                onClick = onStartSmsActivity
            )

            UsersButton(
                userCount = members.size,
                onClick = onShowMembersBottomSheet
            )

            ReplaceUsersButton(
                onClick = { isReversed = !isReversed }
            )
        }

        LazyColumn(
            modifier = modifier
                .padding(horizontal = 5.dp)
                .constrainAs(membersGrid) {
                    linkTo(
                        top = topButtonsRow.bottom,
                        bottom = bottomButtonsRow.top,
                        bottomMargin = 16.dp
                    )
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(members, key = { it.id }) { member ->
                MemberCard(
                    member = member,
                    modifier = Modifier
                        .height(270.dp)
                        .animateItemPlacement()
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomButtonsRow) {
                    linkTo(
                        start = parent.start,
                        startMargin = 16.dp,
                        end = parent.end,
                        endMargin = 16.dp
                    )
                    bottom.linkTo(anchor = parent.bottom, margin = 16.dp)
                }
        ) {
            VideoButton(
                onCheckedChange = onToggleVideo,
                isChecked = uiState.currentUser.videoEnabled
            )

            VoiceButton(
                onCheckedChange = onToggleVoice,
                isChecked = uiState.currentUser.voiceEnabled
            )

            HelloButton(
                onClick = { isHelloDialogShowing = true }
            )

            QuitButton(
                onClick = onFinishActivity
            )
        }
    }
}