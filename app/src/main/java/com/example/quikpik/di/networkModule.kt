package com.example.quikpik.di

import android.content.Context
import android.content.SharedPreferences
import com.example.quikpik.common.Constants.BASE_URL
import com.example.quikpik.data.remort.AuthApi
import com.example.quikpik.data.remort.PostApi
import com.example.quikpik.data.remort.ProfileApi
import com.example.quikpik.data.remort.UserFeedApi
import com.example.quikpik.data.remort.others.AuthInterceptor
import com.example.quikpik.data.remort.others.TokenManager
import com.example.quikpik.data.repo.AuthRepoImpl
import com.example.quikpik.data.repo.PostRepoImpl
import com.example.quikpik.data.repo.ProfileRepoImpl
import com.example.quikpik.data.repo.Userfeedimpl
import com.example.quikpik.domain.repo.AuthRepo
import com.example.quikpik.domain.repo.PostRepo
import com.example.quikpik.domain.repo.ProfileRepo
import com.example.quikpik.domain.repo.UserFeedRepo
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))

    }

    @Singleton
    @Provides
    fun provideAuthapi(retrofitBuilder :Retrofit.Builder):AuthApi{
        return retrofitBuilder.build().create(AuthApi::class.java)
    }
    @Singleton
    @Provides
    fun provideAuthRepo(authApi: AuthApi , tokenManager: TokenManager): AuthRepo {
        return AuthRepoImpl(authApi,tokenManager)
    }

    @Singleton
    @Provides
    fun proviceOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(authInterceptor)
        return client.build()
    }

    @Singleton
    @Provides
    fun provideTokenPostApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient): PostApi {
        return retrofitBuilder.client(okHttpClient).build().create(PostApi::class.java)
    }
    @Singleton
    @Provides
    fun providePostRepo(@ApplicationContext appContext: Context,authApi: PostApi,tokenManager: TokenManager ): PostRepo {
        return PostRepoImpl(authApi,appContext, tokenManager )
    }

    @Singleton
    @Provides
    fun provideTokenUserFeedApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient): UserFeedApi {
        return retrofitBuilder.client(okHttpClient).build().create(UserFeedApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserFeedRepo(authApi: UserFeedApi ): UserFeedRepo {
        return Userfeedimpl(authApi )
    }

    @Singleton
    @Provides
    fun provideTokenProfileApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient): ProfileApi {
        return retrofitBuilder.client(okHttpClient).build().create(ProfileApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileRepo(@ApplicationContext appContext: Context,authApi: ProfileApi,tokenManager: TokenManager ): ProfileRepo {
        return ProfileRepoImpl(authApi,appContext, tokenManager )
    }




    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("quikpik_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        return TokenManager(sharedPreferences)
    }
}