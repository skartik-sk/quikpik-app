package com.example.quikpik.presentation.feature.post.components

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.example.quikpik.R
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.UserModel
import com.example.quikpik.domain.model.createdBy
import com.example.quikpik.presentation.components.ButtonType
import com.example.quikpik.presentation.components.PrimaryButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit


@Composable
fun UserProfileBox(
    user: createdBy,
    currentUser: UserModel,
    onFollowClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    val isFollowing = currentUser.following.contains(user.id)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = user.profileImage,
                    contentDescription = "Profile image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = user.username,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = user.bio,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }

                if (currentUser.id != user.id) {
                    androidx.compose.material3.Button(
                        onClick = onFollowClick,
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = if (isFollowing)
                                MaterialTheme.colorScheme.surfaceVariant
                            else
                                MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = if (isFollowing) "Following" else "Follow",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = user.followers.size.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Followers",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = user.following.size.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Following",
                        style = MaterialTheme.typography.bodySmall
                    )
                }


            }
        }
    }
}


@Composable
fun PostCard(
    post: DetailPostModel,
    onCommentClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,   // Add this parameter
    onBookmarkClick: (String) -> Unit,
    onPostClick: (String) -> Unit,
    userdata: UserModel,
    onFollowClick: (String) -> Unit,
    onUnFollowClick: (String) -> Unit,
) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "https://main--quikpikweb.netlify.app/Homepostview/${post.id}")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, null)
    val context = LocalContext.current
    var showProfilePopup = remember { mutableStateOf(false) }
    var isFollowing = remember { mutableStateOf(userdata.following.contains(post.createdBy.id)) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        var isLiked = remember { mutableStateOf(post.likes.contains(userdata.id)) }
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
//            Log.d("PostCard", "Profile Image URL: ${post.createdBy.profileImage}")
            AsyncImage(
                model = post.createdBy.profileImage,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
            )


//
            Spacer(modifier = Modifier.width(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = post.createdBy.username,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable(
                        onClick = {
                            showProfilePopup.value = !showProfilePopup.value

                            // Handle profile click
                        }
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                if (userdata.id != post.createdBy.id) {
                    PrimaryButton(
                        onclick = {
                           if(isFollowing.value) onUnFollowClick(post.createdBy.id) else onFollowClick(post.createdBy.id)
                            isFollowing.value = !isFollowing.value
                                  },
//                        modifier = Modifier.background(color = if (isFollowing) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary),
                        verient =
                            when (isFollowing.value) {

                                true -> ButtonType.SECONDARY
                                false -> ButtonType.PRIMARY
                            },
                        text = if (isFollowing.value) "Following" else "Follow")

                }
//                IconButton(onClick = {}) {
//
//                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Options")
//                }
            }
        }
        Log.d("PostCard", "Profile Image URL: ${post.image}")
        // Post Image
        AsyncImage(
            model = post.image,
            contentDescription = " Image",
            placeholder = painterResource(R.drawable.placeholder),
            error = painterResource(R.drawable.error_image),
            onError = {
                Log.e("PostCard", "Error loading image: ${it.result.throwable.message}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = { onPostClick(post.id) }),
            contentScale = ContentScale.Crop
        )

        // Actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {
                onLikeClick(post.id)
                isLiked.value = !isLiked.value
            }) {
                Icon(
                    imageVector = if (isLiked.value) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                    contentDescription = "Like",
                    modifier = Modifier.size(20.dp),
                    tint = if (isLiked.value)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.onSurface


                )
            }
            IconButton(onClick = { onPostClick(post.id)  }) {
                Icon(
                    imageVector = Icons.Outlined.Create, contentDescription = "comment",
                    modifier = Modifier.size(20.dp)
                )

            }

            IconButton(onClick = {
                context.startActivity(shareIntent)
            }) {

                Icon(
                    imageVector = Icons.Outlined.Send, contentDescription = "share",
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { onBookmarkClick(post.id) }) {
                Icon(
                    painter = painterResource(if (userdata.savedPosts.contains(post.id)) R.drawable.bookmarkfilled else R.drawable.bookmarkoutline),
                    contentDescription = "Bookmark",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Likes and Description
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            var likesText = ""
            if (post.likes.isNotEmpty()) {
                if (post.likes.contains(userdata.id)) {
                    if(post.likes.size > 1)
                        likesText = "Liked by You and ${post.likes.size - 1} others "
                    else
                    likesText = "Liked by You and ${post.likes.size - 1} others "
                } else {
                    likesText = "Liked by ${post.likes.size} "
                }
            } else {
                likesText = "No Likes"
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(likesText)
                    }
                },
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = post.caption,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2
            )
            Text(
                text = formatPostDate(post.createdAt),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }


    }
}

fun formatPostDate(createdAt: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val postDate = dateFormat.parse(createdAt)
    val currentDate = Date()

    postDate?.let {
        val diffInMillis = currentDate.time - postDate.time
        val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)

        return when {
            diffInHours < 24 -> "$diffInHours hours ago"
            diffInHours < 48 -> "1 day ago"
            diffInHours < 720 -> "${diffInHours / 24} days ago"
            else -> {
                val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                outputFormat.format(postDate)
            }
        }
    }
    return "Invalid date"
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostCardPreview() {

//    PostCard(
//        post = DetailPostModel(
//            id = "1",
//            image = "https://cdn.pixabay.com/photo/2016/07/07/16/46/dice-1502706_640.jpg",
//            comments = emptyList(),
//            caption = "Million Parrots in India Like a Family... (More)",
//            likes = listOf("Lucas", "John", "Emma"),
//            createdBy = createdBy(
//                id = "1",
//                profileImage = "https://cdn.pixabay.com/photo/2016/07/07/16/46/dice-1502706_640.jpg",
//                username = "Lucas",
//                bio = "Nature Lover",
//                followers = listOf("John", "Emma"),
//                following = listOf("Lucas", "John")
//            ),
//            createdAt = "Wed, 26 January 2021"
//        ),
//        onLikeClick = {},
//        onCommentClick = {},
//        userdata =
//    )
}