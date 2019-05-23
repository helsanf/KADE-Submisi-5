package com.example.helsanf.footballmatch.database.nextmatch

data class FavoriteModelNext (
    val id : Long?,
    val idEvent : String? ,
    val homeTeam : String?,
    val awayTeam : String?,
    val tanggal : String?,
    val scoreHome : String?,
    val scoreAway : String?,
    val idHomeTeam : String?,
    val idAwayTeam : String?,
    val idLiga : String?
) {
    companion object{
        const val TABLE_FAVORITE_NEXT : String = "TABLE_FAVORITE_NEXT"
        const val ID : String = "ID"
        const val   ID_EVENT : String = "ID_EVENT"
        const val HOME_TEAM : String = "HOME_TEAM"
        const val AWAY_TEAM : String = "AWAY_TEAM"
        const val TANGGAL : String = "TANGGAL"
        const val SCORE_HOME : String = "SCORE_HOME"
        const val SCORE_AWAY : String = "SCORE_AWAY"
        const val ID_HOME_TEAM : String = "ID_HOME_TEAM"
        const val ID_AWAY_TEAM : String = "ID_AWAY_TEAM"
        const val ID_LIGA : String = "ID_LIGA"

    }
}