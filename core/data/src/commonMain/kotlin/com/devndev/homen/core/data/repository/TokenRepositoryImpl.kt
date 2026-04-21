package com.devndev.homen.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.devndev.homen.core.domain.repository.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class TokenRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : TokenRepository {

    // 앱 종료 시 사라지는 메모리 저장소
    private val tempAccessToken = MutableStateFlow<String?>(null)
    private val tempRefreshToken = MutableStateFlow<String?>(null)

    private companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override suspend fun saveTokens(accessToken: String, refreshToken: String, isPermanent: Boolean) {
        if (isPermanent) {
            // 영구 저장 (DataStore)
            dataStore.edit { preferences ->
                preferences[ACCESS_TOKEN] = accessToken
                preferences[REFRESH_TOKEN] = refreshToken
            }
            // 영구 저장 시 메모리 값은 비움
            tempAccessToken.value = null
            tempRefreshToken.value = null
        } else {
            // 임시 저장 (메모리)
            tempAccessToken.value = accessToken
            tempRefreshToken.value = refreshToken
        }
    }

    // 메모리 값이 있으면 우선 사용, 없으면 DataStore 값 사용
    override fun getAccessToken(): Flow<String?> = combine(
        tempAccessToken,
        dataStore.data.map { it[ACCESS_TOKEN] }
    ) { temp, perm -> temp ?: perm }

    override fun getRefreshToken(): Flow<String?> = combine(
        tempRefreshToken,
        dataStore.data.map { it[REFRESH_TOKEN] }
    ) { temp, perm -> temp ?: perm }

    override suspend fun clearTokens() {
        tempAccessToken.value = null
        tempRefreshToken.value = null
        dataStore.edit { preferences ->
            preferences.remove(ACCESS_TOKEN)
            preferences.remove(REFRESH_TOKEN)
        }
    }
}
