package com.example.helsanf.footballmatch.view.pertandingan.teams

import android.content.Context
import android.widget.Toast
import com.example.helsanf.footballmatch.model.Teams
import com.example.helsanf.footballmatch.model.TeamsRespone
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamsPresenter(
    private val view: TeamsView,
    private val apiRepository: ApiRepository
) {
    private var context: Context? = null
    fun TeamsPresenter(context: Context) {
        this.context = context
    }


    fun getTeamsList(id_liga: String) {
        view.showLoading()
        val connect: ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getListTeams(id_liga).enqueue(object : Callback<TeamsRespone> {
            override fun onFailure(call: Call<TeamsRespone>, t: Throwable) {

            }

            override fun onResponse(call: Call<TeamsRespone>, response: Response<TeamsRespone>) {
                val get: List<Teams>? = response.body()!!.teams
                view.hideLoading()

                if (get == null) {
                    Toast.makeText(context, "Tidak ada List Teams Pada Liga Ini ", Toast.LENGTH_SHORT).show()

                } else {
                    view.showTeamList(get!!)

                }

            }

        })
    }

    fun getTeamSearch(query : String){
            view.showLoading()
        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getSearchTeams(query).enqueue(object : Callback<TeamsRespone>{
            override fun onFailure(call: Call<TeamsRespone>, t: Throwable) {

            }

            override fun onResponse(call: Call<TeamsRespone>, response: Response<TeamsRespone>) {
               view.hideLoading()
                val get: List<Teams>? = response.body()!!.teams
                if (get == null) {
                    Toast.makeText(context, "Tidak Ada Teams Yang di Cari", Toast.LENGTH_SHORT).show()

                } else {
                    view.showTeamList(get!!)

                }
            }

        })
    }
}