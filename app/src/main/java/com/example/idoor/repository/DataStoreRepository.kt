package com.example.idoor.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.idoor.data.models.auth.AuthData
import com.example.idoor.data.models.auth.UserData
import com.example.idoor.navigation.Screen
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_pref")

class DataStoreRepository(context: Context) {

    private object PreferencesKey {
        val START_DESTINATION = stringPreferencesKey(name = "start_destination")
        val AUTH_DATA = stringPreferencesKey(name = "auth_data")
        val DEVICE_ID = stringPreferencesKey(name = "device_id")
        val DEVICE_MODEL = stringPreferencesKey(name = "device_model")
    }

    private val dataStore = context.dataStore
    private val gson = Gson()

    suspend fun saveStartDestination(startDestination: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.START_DESTINATION] = startDestination
        }
    }

    suspend fun saveAuthData(authData: AuthData) {
        val authDataJson = gson.toJson(authData)
        dataStore.edit { preferences ->
            preferences[PreferencesKey.AUTH_DATA] = authDataJson
        }
    }

    suspend fun saveDeviceId(id: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.DEVICE_ID] = id
        }
    }

    suspend fun saveDeviceModel(deviceModel: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.DEVICE_MODEL] = deviceModel
        }
    }

    suspend fun updateUserData(userData: UserData) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.AUTH_DATA]?.let { authDataJson ->
                val authData = gson.fromJson(authDataJson, AuthData::class.java)
                val updatedAuthData = authData?.copy(user = userData)

                if (updatedAuthData != null) {
                    preferences[PreferencesKey.AUTH_DATA] = gson.toJson(updatedAuthData)
                }
            }
        }
    }

    fun readStartDestination(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.START_DESTINATION] ?: Screen.OnBoarding.route
                onBoardingState
            }
    }

    fun readAuthData(): Flow<AuthData?> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val authDataJson = preferences[PreferencesKey.AUTH_DATA]
                if (authDataJson != null) {
                    gson.fromJson(authDataJson, AuthData::class.java)
                } else {
                    null
                }
            }
    }

    fun readDeviceId(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.DEVICE_ID] ?: ""
                onBoardingState
            }
    }

    fun readDeviceModel(): Flow<String> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.DEVICE_MODEL] ?: ""
                onBoardingState
            }
    }

    fun readAuthToken(): Flow<String?> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKey.AUTH_DATA]?.let { authDataJson ->
                    gson.fromJson(authDataJson, AuthData::class.java)?.token
                }
            }
    }

    fun readUserId(): Flow<Int?> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKey.AUTH_DATA]?.let { authDataJson ->
                    gson.fromJson(authDataJson, AuthData::class.java)?.user?.id
                }
            }
    }

    fun readUserName(): Flow<String?> {
        return dataStore.data
            .map { preferences ->
                preferences[PreferencesKey.AUTH_DATA]?.let { authDataJson ->
                    gson.fromJson(authDataJson, AuthData::class.java)?.user?.name
                }
            }
    }

    suspend fun clearAuthData() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKey.AUTH_DATA)
        }
    }
}