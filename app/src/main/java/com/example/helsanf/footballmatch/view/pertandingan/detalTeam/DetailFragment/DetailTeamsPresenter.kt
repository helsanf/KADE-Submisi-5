package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.DetailFragment

import com.example.helsanf.footballmatch.model.MatchRespone
import com.example.helsanf.footballmatch.model.Teams
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTeamsPresenter(private val view : DetailTeamsView , private val apiRepository: ApiRepository) {

    fun getDetailTeams(id_teams : String){
        view.showLoading()
        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getDetailTeam(id_teams).enqueue(object : Callback<MatchRespone>{
            override fun onFailure(call: Call<MatchRespone>, t: Throwable) {

            }

            override fun onResponse(call: Call<MatchRespone>, response: Response<MatchRespone>) {
                view.hideLoading()
                val get : Teams = response.body()?.teams!!.get(0)
                view.showDetailTeams(get)
            }

        })
    }
}