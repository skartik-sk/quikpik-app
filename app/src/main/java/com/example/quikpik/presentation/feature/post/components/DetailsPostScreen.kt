package com.example.quikpik.presentation.feature.post.components

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.quikpik.R
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.DetailPostModel1
import com.example.quikpik.domain.model.UserModel
import com.example.quikpik.domain.model.commentModel
import com.example.quikpik.domain.model.commentModel1
import com.example.quikpik.presentation.common.MeViewmodle
import com.example.quikpik.presentation.components.ButtonType
import com.example.quikpik.presentation.components.CustomLoading
import com.example.quikpik.presentation.components.PrimaryButton
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun DetailsPostScreen(
    navController: NavHostController,
    viewModel: PostDetailsViewModel = hiltViewModel(),
    meViewModel: MeViewmodle
) {
    val state = viewModel.state.collectAsState().value
    val currentUser = meViewModel.state.collectAsState().value.userData
    val post = state.post
    val comments = state.comments
    val context = LocalContext.current
    var isFollowing = remember { mutableStateOf(currentUser?.following?.contains(post?.createdBy?.id)) }
    Log.d("DetailsPostScreen", "Comments: $comments")
    // Show error if any
    LaunchedEffect(state.error) {
        if (state.error.isNotBlank()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            CustomLoading()
        } else if (post != null && currentUser != null) {
            DetailPostContent(
                post = post,
                comments = comments,
                currentUser = currentUser,
                onLikeClick = { viewModel.likePost(post.id,currentUser.id) },
                onBookmarkClick= { meViewModel.bookmarkPost(post.id) },
                onBackClick = { navController.navigateUp() },
                onAddComment = { comment -> viewModel.addComment(post.id, comment) },
                isAddingComment = state.isAddingComment,
                newCommentText = state.newCommentText,
                onNewCommentTextChange = viewModel::updateNewCommentText,
                onFollowClick = { userId ->
                    meViewModel.follow(userId)
                    isFollowing.value = !isFollowing.value!!
                },
                onUnFollowClick = { userId ->
                    meViewModel.unfollow(userId)
                    isFollowing.value = !isFollowing.value!!
                }

            )
        }
    }
}

@Composable
fun DetailPostContent(
    post: DetailPostModel1,
    comments: List<commentModel1>,
    currentUser: UserModel,
    onLikeClick: () -> Unit,
    onBookmarkClick:()->Unit,
    onFollowClick: (String) -> Unit,
    onUnFollowClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onAddComment: (String) -> Unit,
    isAddingComment: Boolean,
    newCommentText: String,
    onNewCommentTextChange: (String) -> Unit

) {
    post.likes.contains(currentUser.id)
    currentUser.savedPosts.any { it == post.id }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top bar with back button
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://main--quikpikweb.netlify.app/Homepostview/${post.id}")
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        val context = LocalContext.current
        var showProfilePopup = remember { mutableStateOf(false) }
        val isFollowing = currentUser.following.contains(post.createdBy.id)


        // Scrollable content
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
//            // Post header
            item{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    var isLiked = remember { mutableStateOf(post.likes.contains(currentUser.id)) }
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
                            if (currentUser.id != post.createdBy.id) {
                                PrimaryButton(
                                    onclick = if(isFollowing) {
                                        { onUnFollowClick(post.createdBy.id) }
                                    } else {
                                        { onFollowClick(post.createdBy.id) }
                                    },
//                        modifier = Modifier.background(color = if (isFollowing) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary),
                                    verient =
                                        when (isFollowing) {

                                            true -> ButtonType.SECONDARY
                                            false -> ButtonType.PRIMARY
                                        },
                                    text = if (isFollowing) "Following" else "Follow")

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
                            .clip(RoundedCornerShape(12.dp)),
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
                            onLikeClick()
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


                        IconButton(onClick = {
                            context.startActivity(shareIntent)
                        }) {

                            Icon(
                                imageVector = Icons.Outlined.Send, contentDescription = "share",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        IconButton(onClick = { onBookmarkClick() }) {
                            Icon(
                                painter = painterResource(if (currentUser.savedPosts.contains(post.id)) R.drawable.bookmarkfilled else R.drawable.bookmarkoutline),
                                contentDescription = "Bookmark",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    // Likes and Description
                    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                        var likesText = ""
                        if (post.likes.isNotEmpty()) {
                            if (post.likes.contains(currentUser.id)) {
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
//
//
//            // Post image
//
//
//            // Action buttons
//            // Caption
//
//
//            // Comments section header
            item {
                Divider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Comments (${comments.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
//
//            // Comments list
            items(comments.size) {  index->
                CommentItem(comment =comments[index] )
            }
//
//            // Empty state for comments
            if (comments.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No comments yet. Be the first to comment!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        // Comment input field
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 8.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = currentUser.profileImage,
                    contentDescription = "Your Profile Image",
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newCommentText,
                    onValueChange = onNewCommentTextChange,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    placeholder = { Text("Add a comment...") },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { onAddComment(newCommentText) },
                    enabled = newCommentText.isNotBlank() && !isAddingComment
                ) {
                    if (isAddingComment) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send Comment",
                            tint = if (newCommentText.isBlank())
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            else
                                MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: commentModel1) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        AsyncImage(
            model = comment.commenter.profileImage,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = comment.commenter.username,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = comment.comment,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}