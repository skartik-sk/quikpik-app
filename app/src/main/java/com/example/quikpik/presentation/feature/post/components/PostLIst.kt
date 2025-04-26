package com.example.quikpik.presentation.feature.post.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.PostModel
import com.example.quikpik.domain.model.UserModel


// feature/post/components/PostList.kt
@Composable
fun PostList(posts: List<DetailPostModel>, onPostClick: (DetailPostModel) -> Unit,
             userdata: UserModel
             ) {
    LazyColumn {
        items(posts.size) { post ->
            PostCard(posts[post],
                onLikeClick = { /* handle like */ },
                onCommentClick = { /* handle comment */ },
                userdata= userdata
            )
        }
    }
}