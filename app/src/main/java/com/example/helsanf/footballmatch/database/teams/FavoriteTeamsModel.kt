package com.example.helsanf.footballmatch.database.teams

data class FavoriteTeamsModel(val id : Long?,
                              val idTeam : String?,
                              val idLiga : String?,
                              val namaTeam : String?,
                              val logoTeam : String?) {

    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID"
        const val ID_TEAM: String = "ID_TEAM"
        const val ID_LIGA: String = "ID_LIGA"
        const val NAMA_TEAM: String = "NAMA_TEAM"
        const val LOGO_TEAM : String = "LOGO_TEAM"

    }
}