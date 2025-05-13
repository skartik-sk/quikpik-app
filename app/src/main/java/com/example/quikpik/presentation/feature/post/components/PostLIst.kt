package com.example.quikpik.presentation.feature.post.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.example.quikpik.domain.model.DetailPostModel
import com.example.quikpik.domain.model.PostModel
import com.example.quikpik.domain.model.UserModel


// feature/post/components/PostList.kt
@Composable
fun PostList(posts: List<DetailPostModel>, onPostClick: (String) -> Unit,
             userdata: UserModel,
             onLikeClick: (String) -> Unit,   // Add this parameter
             onBookmarkClick: (String) -> Unit
                , onFollowClick: (String) -> Unit,
                onUnFollowClick: (String) -> Unit,
             ) {
    LazyColumn {
        items(posts.size) { post ->
            PostCard(posts[post],

                onCommentClick = { /* handle comment */ },
                userdata= userdata,
                onLikeClick = onLikeClick,
                onBookmarkClick = onBookmarkClick,
                onPostClick = onPostClick
                , onFollowClick = onFollowClick,
                onUnFollowClick = onUnFollowClick
            // Pass the post to the onPostClick callback



            )
        }
    }
}