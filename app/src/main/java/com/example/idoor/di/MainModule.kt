package com.example.idoor.di

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.example.idoor.MainActivity
import com.example.idoor.MyApplication
import com.example.idoor.data.remote.AuthApiInterface
import com.example.idoor.data.remote.GuestApiInterface
import com.example.idoor.data.remote.TokenInterceptor
import com.example.idoor.data.repository.DataStoreRepository
import com.example.idoor.nfc.NfcHelper
import com.example.idoor.repository.AccessHistoryRepository
import com.example.idoor.repository.AccessRightRepository
import com.example.idoor.repository.ProfileRepository
import com.example.idoor.repository.ToolRepository
import com.example.idoor.repository.UserRepository
import com.example.idoor.util.Constants.BASE_URL
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideNfcHelper(
        context: Context,
        accessHistoryRepository: AccessHistoryRepository,
        dataStoreRepository: DataStoreRepository
    ): NfcHelper {
        return NfcHelper(context, accessHistoryRepository, dataStoreRepository)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

    @Provides
    @Singleton
    fun provideUserRepository(
        api: AuthApiInterface
    ) = UserRepository(api)

    @Provides
    @Singleton
    fun provideToolRepository(
        api: AuthApiInterface
    ) = ToolRepository(api)

    @Provides
    @Singleton
    fun provideAccessRightRepository(
        api: AuthApiInterface
    ) = AccessRightRepository(api)

    @Provides
    @Singleton
    fun provideAccessHistoryRepository(
        api: AuthApiInterface
    ) = AccessHistoryRepository(api)

    @Provides
    @Singleton
    fun provideProfileRepository(
        api: AuthApiInterface
    ) = ProfileRepository(api)

    @Provides
    @Singleton
    fun provideGuestApiInterface(): GuestApiInterface {
        val gson = GsonBuilder().serializeNulls().create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
            .create(GuestApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApiInterface(okHttpClient: OkHttpClient): AuthApiInterface {
        val gson = GsonBuilder().serializeNulls().create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
            .create(AuthApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(dataStoreRepository: DataStoreRepository): TokenInterceptor {
        return runBlocking {
            TokenInterceptor(dataStoreRepository.readAuthToken().first() ?: "")
        }
    }
}