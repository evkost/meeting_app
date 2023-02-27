package com.evkost.meetingapp.ui.meeting.component

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.evkost.meetingapp.R

@Preview(showBackground = true)
@Composable
private fun QuitButtonPreview() {
    QuitButton(onClick = { })
}

@Preview(showBackground = true)
@Composable
private fun VideoButtonPreview() {
    var enabled by remember { mutableStateOf(true) }
    VideoButton(onCheckedChange = { enabled = it }, isChecked = enabled)
}

@Preview(showBackground = true)
@Composable
private fun VoiceButtonPreview() {
    var enabled by remember { mutableStateOf(true) }
    VoiceButton(onCheckedChange = { enabled = it }, isChecked = enabled)
}

@Composable
private fun MeetingToggleButton(
    onCheckedChange: (Boolean) -> Unit,
    isChecked: Boolean,
    content: @Composable () -> Unit,
    checkedContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Crossfade(
        targetState = isChecked,
        animationSpec = tween(durationMillis = 200)
    ) { enabled ->
        FilledIconToggleButton(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            shape = CircleShape,
            colors = IconButtonDefaults.filledIconToggleButtonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                checkedContainerColor = MaterialTheme.colorScheme.onSurface,
                checkedContentColor = MaterialTheme.colorScheme.surface
            ),
            modifier = modifier
        ) {
            if (enabled) {
                content()
            } else {
                checkedContent()
            }
        }
    }
}

@Composable
fun VideoButton(
    onCheckedChange: (Boolean) -> Unit,
    isChecked: Boolean,
    modifier: Modifier = Modifier
) {
    MeetingToggleButton(
        onCheckedChange = onCheckedChange,
        isChecked = isChecked,
        content = {
            Icon(
                imageVector = Icons.Default.Videocam,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.bottom_button_icon_size))
            )
        },
        checkedContent = {
            Icon(
                imageVector = Icons.Default.VideocamOff,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.bottom_button_icon_size))
            )
        },
        modifier = modifier.size(dimensionResource(R.dimen.bottom_button_size))
    )
}

@Composable
fun VoiceButton(
    onCheckedChange: (Boolean) -> Unit,
    isChecked: Boolean,
    modifier: Modifier = Modifier
) {
    MeetingToggleButton(
        onCheckedChange = onCheckedChange,
        isChecked = isChecked,
        content = {
            Icon(
                imageVector = Icons.Default.Mic,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.bottom_button_icon_size))
            )
        },
        checkedContent = {
            Icon(
                imageVector = Icons.Default.MicOff,
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.bottom_button_icon_size))
            )
        },
        modifier = modifier.size(dimensionResource(R.dimen.bottom_button_size))
    )
}

@Composable
fun HelloButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        shape = CircleShape,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier.size(dimensionResource(R.dimen.bottom_button_size))
    ) {
        Icon(
            imageVector = Icons.Default.WavingHand,
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.bottom_button_icon_size))
        )
    }
}

@Composable
fun QuitButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        shape = CircleShape,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
        ),
        modifier = modifier.size(dimensionResource(R.dimen.bottom_button_size))
    ) {
        Icon(
            imageVector = Icons.Default.CallEnd,
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.bottom_button_icon_size))
        )
    }
}