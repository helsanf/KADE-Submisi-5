package com.example.helsanf.footballmatch.prevmatch

import android.util.Log
import com.example.helsanf.footballmatch.model.Event
import com.example.helsanf.footballmatch.model.MatchRespone
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrevPresenter(private val view : PrevView , private val apiRepository: ApiRepository) {

    fun getLastMatch(liga : String){
        view.showLoading()
        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getLastMatch(liga).enqueue(object :  Callback<MatchRespone> {
            override fun onFailure(call: Call<MatchRespone>, t: Throwable) {

            }

            override fun onResponse(call: Call<MatchRespone>, response: Response<MatchRespone>) {
                view.hideLoading()
                val get = response.body()!!.events
                view.ShowMatchList(get)
            }

        })
    }

    fun getTeamSearch(query : String){
        view.showLoading()
        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getSearchMatch(query).enqueue(object : Callback<MatchRespone>{
            override fun onFailure(call: Call<MatchRespone>, t: Throwable) {
                Log.d("tag", "responsennya ${t.message}")

            }

            override fun onResponse(call: Call<MatchRespone>, response: Response<MatchRespone>) {
                view.hideLoading()
                val get : List<Event>? = response.body()!!.event
                view.ShowMatchList(get!!)

            }

        })
    }
}