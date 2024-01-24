package com.kklabs.karam.data.ds

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_config")

class ConfigPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val AUTH_TOKEN_PREFERENCE_KEY = stringPreferencesKey("auth_token")

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

}