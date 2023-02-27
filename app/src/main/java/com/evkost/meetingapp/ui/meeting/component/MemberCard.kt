package com.evkost.meetingapp

import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.evkost.meetingapp.model.Member
import com.evkost.meetingapp.model.MemberType


@Preview(showBackground = true)
@Composable
private fun MemberCardPreview() {
    MemberCard(
        member = Member(
            id = 1,
            voiceEnabled = true,
            videoEnabled = false,
            name = "Name",
            imageUrl = "https://img.freepik.com/free-vector/colorful-rainbow-background_23-2147805840.jpg?w=2000",
            type = MemberType.ANOTHER_USER
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(250.dp)
    )
}

@Composable
fun MemberCard(
    member: Member,
    modifier: Modifier = Modifier
) {
    MemberInternal(
        member = member,
        modifier = modifier
    ) {
        AsyncImage(
            model = member.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .align(Alignment.Center)
                .size(80.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = 7.dp,
                    vertical = 3.dp
                )
        ) {
            Text(
                text = if (member.type == MemberType.CURRENT_USER) {
                    stringResource(R.string.current_user_name)
                } else {
                    member.name
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f, fill = false)
            )

            Crossfade(
                targetState = member.voiceEnabled,
                animationSpec = tween(durationMillis = 200)
            ) { enabled ->
                if (enabled) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = null
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.MicOff,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
private fun MemberInternal(
    member: Member,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    if (Build.VERSION.SDK_INT >= VERSION_CODES.S) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onBackground,
            shape = RoundedCornerShape(3),
            modifier = modifier
        ) {
            SubcomposeAsyncImage(
                model = member.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (this@SubcomposeAsyncImage.painter.state is AsyncImagePainter.State.Success) {
                        this@SubcomposeAsyncImage.SubcomposeAsyncImageContent(
                            modifier = Modifier
                                .blur(100.dp)
                                .align(Alignment.Center)
                                .fillMaxSize()
                        )
                    }
                    content()
                }
            }
        }
    } else {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            shape = RoundedCornerShape(3),
            modifier = modifier
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                content = content
            )
        }
    }
}