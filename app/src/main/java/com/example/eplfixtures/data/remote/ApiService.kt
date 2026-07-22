package com.example.eplfixtures.data.remote

import com.example.eplfixtures.data.model.Fixture
import retrofit2.http.GET

interface ApiService {

    @GET("feed/json/epl-2023")
    suspend fun getFixtures(): List<Fixture>
}
