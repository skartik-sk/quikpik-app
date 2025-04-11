package com.example.quikpik.presentation.feature.post.components

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter.State.Empty.painter
import coil3.compose.rememberAsyncImagePainter
import com.example.quikpik.R
import com.example.quikpik.domain.model.PostModel

@Composable
fun PostCard(post: PostModel, onLikeClick: () -> Unit, onCommentClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = post.image),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = post.createdBy, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                Text(text = "New Delhi, India", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More Options")
        }

        // Post Image
        Image(
           painter= rememberAsyncImagePainter(model = post.image),
            contentDescription = "Post Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
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
            IconButton(onClick = onLikeClick) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Like")
            }
            IconButton(onClick = onCommentClick) {
                Icon(imageVector = Icons.Default.Send, contentDescription = "Comment")
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { /* Bookmark action */ }) {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "Bookmark")
            }
        }

        // Likes and Description
        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Liked by Lucas and 903 others")
                    }
                },
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = post.caption,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2
            )
            Text(text = "Wed, 26 January 2021", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostCardPreview() {
    PostCard(
        post = PostModel(
            id = "1",
            image = "https://cdn.pixabay.com/photo/2016/07/07/16/46/dice-1502706_640.jpg",
            comments = emptyList(),
            caption = "Million Parrots in India Like a Family... (More)",
            likes = listOf("Lucas", "John", "Emma"),
            createdBy = "cameron_will",
            createdAt = "Wed, 26 January 2021"
        ),
        onLikeClick = {},
        onCommentClick = {}
    )
}