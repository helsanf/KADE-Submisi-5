package com.example.helsanf.footballmatch.rest

import com.example.helsanf.footballmatch.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api/v1/json/1/eventsnextleague.php")
    fun getScheduleNext(@Query("id")id_liga : String) : Call<MatchRespone>

    @GET("api/v1/json/1/eventspastleague.php")
    fun getLastMatch(@Query("id") id_liga : String) : Call<MatchRespone>

    @GET("api/v1/json/1/searchevents.php")
    fun getSearchMatch(@Query("e") query : String) : Call<MatchRespone>

    @GET("api/v1/json/1/lookupevent.php")
    fun getDetailEvent(@Query("id") id_event : String) : Call<MatchRespone>

    @GET("api/v1/json/1/lookupteam.php")
    fun getDetailTeam(@Query("id") id_team : String) : Call<MatchRespone>

    @GET("api/v1/json/1/lookupleague.php")
    fun getDetailLeugue(@Query("id") id_team : String) : Call<LeagueResponse>

    @GET("api/v1/json/1/lookuptable.php")
    fun getKlasemen(@Query("l") id_liga: String) : Call<KlasemenRespone>

    @GET("api/v1/json/1/lookup_all_teams.php")
    fun getListTeams(@Query("id") id_liga: String) : Call<TeamsRespone>

    @GET("api/v1/json/1/lookup_all_players.php")
    fun  getListPlayer(@Query("id") id_team: String) : Call<PlayerResponse>

    @GET("api/v1/json/1/lookupplayer.php")
    fun  getDetailPlayer(@Query("id") id_player: String) : Call<PlayerResponse>

    @GET("api/v1/json/1/searchteams.php")
    fun getSearchTeams(@Query("t") query: String) : Call<TeamsRespone>


}