package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.player.detailPlayer

import com.example.helsanf.footballmatch.model.Player
import com.example.helsanf.footballmatch.model.PlayerResponse
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPlayerPresenter(private val view : DetailPlayerView , private val apiRepository: ApiRepository) {

    fun getDetailPlayer(id_player : String){
        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getDetailPlayer(id_player).enqueue(object : Callback<PlayerResponse>{
            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
               val get : Player = response.body()!!.players.get(0)
//                if(get == null){
//
//                }
                view.showDetailPlayer(get)
                view.hideLoading()
            }

        })
    }
}