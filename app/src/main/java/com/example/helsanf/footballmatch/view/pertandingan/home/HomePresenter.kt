package com.example.helsanf.footballmatch.view.pertandingan.home

import com.example.helsanf.footballmatch.model.League
import com.example.helsanf.footballmatch.model.LeagueResponse
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenter(private var view: HomeView, private var apiRepository: ApiRepository) {

    fun getDetailLiga(id: String) {
        val connect: ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getDetailLeugue(id).enqueue(object : Callback<LeagueResponse> {
            override fun onFailure(call: Call<LeagueResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<LeagueResponse>, response: Response<LeagueResponse>) {
                val get: League = response.body()!!.leagues.get(0)
                view.showLiga(get)
            }

        })
    }
}