package com.example.quikpik.di

import com.example.quikpik.domain.repo.AuthRepo
import com.example.quikpik.domain.repo.PostRepo
import com.example.quikpik.domain.repo.ProfileRepo
import com.example.quikpik.domain.repo.UserFeedRepo
import com.example.quikpik.domain.usecase.auth.AuthForgot
import com.example.quikpik.domain.usecase.auth.AuthLogin
import com.example.quikpik.domain.usecase.auth.AuthLogout
import com.example.quikpik.domain.usecase.auth.AuthSignup
import com.example.quikpik.domain.usecase.AuthUseCases
import com.example.quikpik.domain.usecase.PostUseCases
import com.example.quikpik.domain.usecase.ProfileUseCases
import com.example.quikpik.domain.usecase.UserFeedUseCases
import com.example.quikpik.domain.usecase.feed.Follow
import com.example.quikpik.domain.usecase.feed.GetFeed
import com.example.quikpik.domain.usecase.feed.GetFollowers
import com.example.quikpik.domain.usecase.feed.UnFollow
import com.example.quikpik.domain.usecase.post.AddComment
import com.example.quikpik.domain.usecase.post.DeleteComment
import com.example.quikpik.domain.usecase.post.DeletePost
import com.example.quikpik.domain.usecase.post.GetAllPosts
import com.example.quikpik.domain.usecase.post.GetPostById
import com.example.quikpik.domain.usecase.post.GetSavedPosts
import com.example.quikpik.domain.usecase.post.GetUserPosts
import com.example.quikpik.domain.usecase.post.LikePost
import com.example.quikpik.domain.usecase.post.SavePost
import com.example.quikpik.domain.usecase.post.UpdatePostCaption
import com.example.quikpik.domain.usecase.post.UploadPost
import com.example.quikpik.domain.usecase.profile.DeleteAccount
import com.example.quikpik.domain.usecase.profile.GetUserProfile
import com.example.quikpik.domain.usecase.profile.UpdateUserProfile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object  UseCaseModule {


    @Provides
    @Singleton
    fun provideAuthUseCase(authRepo: AuthRepo)= AuthUseCases (
        authLogin = AuthLogin(authRepo),
        authSignup = AuthSignup(authRepo),
        authForgot = AuthForgot(authRepo),
        authLogout = AuthLogout(authRepo)
    )


    @Provides
    @Singleton
    fun provideProfileUseCase(profileRepo: ProfileRepo) = ProfileUseCases(
        getUserProfile = GetUserProfile(profileRepo),
        updateUserProfile = UpdateUserProfile(profileRepo),
        deleteAccount = DeleteAccount(profileRepo)
    )

    @Provides
    @Singleton
    fun providePostUseCase(postRepo: PostRepo) = PostUseCases(


        getAllPosts = GetAllPosts(postRepo),
        getUserPosts = GetUserPosts(postRepo),
        getSavedPosts = GetSavedPosts(postRepo),
        getPostById = GetPostById(postRepo),
        uploadPost = UploadPost(postRepo),
        updatePostCaption = UpdatePostCaption(postRepo),
        deletePost = DeletePost(postRepo),
        likePost = LikePost(postRepo),
        savePost = SavePost(postRepo),
        addComment = AddComment(postRepo),
        deleteComment = DeleteComment(postRepo)

    )

    @Provides
    @Singleton
    fun provideUserFeedUseCase(userFeedRepo: UserFeedRepo) = UserFeedUseCases(
        follow = Follow(userFeedRepo),
        unfollow = UnFollow(userFeedRepo),
        getFollowes = GetFollowers(userFeedRepo),
        getFeed = GetFeed(userFeedRepo)
    )



}