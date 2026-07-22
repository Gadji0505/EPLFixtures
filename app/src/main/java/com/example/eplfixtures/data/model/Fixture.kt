package com.example.eplfixtures.data.model

import com.google.gson.annotations.SerializedName

data class Fixture(
    @SerializedName("MatchNumber") val matchNumber: Int,
    @SerializedName("RoundNumber") val roundNumber: Int,
    @SerializedName("DateUtc") val dateUtc: String,
    @SerializedName("Location") val location: String?,
    @SerializedName("HomeTeam") val homeTeam: String,
    @SerializedName("AwayTeam") val awayTeam: String,
    @SerializedName("Group") val group: String?,
    @SerializedName("HomeTeamScore") val homeTeamScore: Int?,
    @SerializedName("AwayTeamScore") val awayTeamScore: Int?
) {
    /** Счёт в виде строки, например "2 - 1" или "-- : --" если матч ещё не сыгран. */
    val scoreText: String
        get() = if (homeTeamScore != null && awayTeamScore != null) {
            "$homeTeamScore : $awayTeamScore"
        } else {
            "-- : --"
        }

    val isPlayed: Boolean
        get() = homeTeamScore != null && awayTeamScore != null
}
