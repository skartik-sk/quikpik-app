package com.example.quikpik.presentation.feature.post.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.quikpik.domain.model.PostModel


// feature/post/components/PostList.kt
@Composable
fun PostList(posts: List<PostModel>, onPostClick: (PostModel) -> Unit) {
    LazyColumn {
        items(posts.size) { post ->
            PostCard(posts[post],
                onLikeClick = { /* handle like */ },
                onCommentClick = { /* handle comment */ }
            )
        }
    }
}