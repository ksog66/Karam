package com.kklabs.karam.data.ds

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kklabs.karam.domain.model.User
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_config")

class ConfigPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi
) {

    private val AUTH_TOKEN_PREFERENCE_KEY = stringPreferencesKey("auth_token")
    private val USER_DATA_PREFERENCE_KEY = stringPreferencesKey("user_data")

    val existingUser = MutableStateFlow(getUserData())
    fun getApplicationPackage(): String {
        return context.packageName
    }

    fun getAuthToken(): String = runBlocking {
        val authToken = context.dataStore.data
            .map { pref->
                pref[AUTH_TOKEN_PREFERENCE_KEY] ?: ""
            }
        return@runBlocking authToken.first()
    }

    suspend fun setAuthToken(token: String) {
        context.dataStore.edit { configPref ->
            configPref[AUTH_TOKEN_PREFERENCE_KEY] = token
        }
    }

    suspend fun saveUserData(user: User) {
        context.dataStore.edit { configPref ->
            val userJson = moshi.adapter(User::class.java).toJson(user)
            configPref[USER_DATA_PREFERENCE_KEY] = userJson
        }
    }

    fun getUserData(): User? = runBlocking {
        return@runBlocking try {
            val userString = context.dataStore.data
                .map { pref ->
                    pref[USER_DATA_PREFERENCE_KEY] ?: ""
                }.first()
            moshi.adapter(User::class.java).fromJson(userString)
        } catch (e: Exception) {
            null
        }
    }

}