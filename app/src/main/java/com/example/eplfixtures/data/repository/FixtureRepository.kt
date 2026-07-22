package com.example.eplfixtures.data.repository

import com.example.eplfixtures.data.model.Fixture
import com.example.eplfixtures.data.remote.ApiService
import com.example.eplfixtures.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Здесь  реализовано простое in-memory кеширование (бонусное требование),
 * чтобы при повторном открытии экрана в рамках одной сессии
 * не делать лишний сетевой запрос
 */
class FixtureRepository(
    private val api: ApiService = RetrofitClient.api
) {
    private var cache: List<Fixture>? = null

    suspend fun getFixtures(forceRefresh: Boolean = false): List<Fixture> {
        val cached = cache
        if (!forceRefresh && cached != null) {
            return cached
        }
        return withContext(Dispatchers.IO) {
            val result = api.getFixtures()
            cache = result
            result
        }
    }

    fun getCachedFixture(matchNumber: Int): Fixture? =
        cache?.firstOrNull { it.matchNumber == matchNumber }
}
