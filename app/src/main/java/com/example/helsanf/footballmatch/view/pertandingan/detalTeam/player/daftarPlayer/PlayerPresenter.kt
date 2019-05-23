package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.player.daftarPlayer

import com.example.helsanf.footballmatch.model.Player
import com.example.helsanf.footballmatch.model.PlayerResponse
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerPresenter(private val view: PlayerView,private val apiRepository: ApiRepository) {

    fun getListPlayer(id_teams : String){
        view.showLoading()
        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getListPlayer(id_teams).enqueue(object : Callback<PlayerResponse>{
            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                val get : List<Player> = response.body()!!.player
                view.showListPlayer(get)
                view.hideLoading()
            }

        })
    }
}