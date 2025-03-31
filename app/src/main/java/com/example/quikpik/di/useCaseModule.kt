package com.example.quikpik.di

import com.example.quikpik.domain.repo.AuthRepo
import com.example.quikpik.domain.usecase.auth.AuthForgot
import com.example.quikpik.domain.usecase.auth.AuthLogin
import com.example.quikpik.domain.usecase.auth.AuthLogout
import com.example.quikpik.domain.usecase.auth.AuthSignup
import com.example.quikpik.domain.usecase.AuthUseCases
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
}