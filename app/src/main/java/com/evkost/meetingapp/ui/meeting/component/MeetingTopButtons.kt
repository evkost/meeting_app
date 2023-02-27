package com.evkost.meetingapp.ui.meeting.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.evkost.meetingapp.R

@Preview(showBackground = true)
@Composable
private fun UserButtonPreview() {
    UsersButton(userCount = 2, onClick = { })
}

@Composable
fun ChatButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(dimensionResource(R.dimen.top_button_size))
    ) {
        Icon(
            imageVector = Icons.Outlined.Sms,
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.top_button_icon_size)),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun ReplaceUsersButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(dimensionResource(R.dimen.top_button_size))
    ) {
        Icon(
            imageVector = Icons.Outlined.ViewAgenda,
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.top_button_icon_size)),
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersButton(
    userCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier.size(dimensionResource(R.dimen.top_button_size))
    ) {
        Box {
            Icon(
                imageVector = Icons.Outlined.Group,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(dimensionResource(R.dimen.top_button_icon_size)),
                tint = MaterialTheme.colorScheme.onBackground
            )

            Badge(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Text(
                    text = userCount.toString(),
                    fontSize = 12.sp,
                )
            }
        }
    }
}