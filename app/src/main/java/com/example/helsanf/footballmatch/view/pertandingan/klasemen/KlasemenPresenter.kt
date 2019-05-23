package com.example.helsanf.footballmatch.view.pertandingan.klasemen

import com.example.helsanf.footballmatch.model.KlasemenRespone
import com.example.helsanf.footballmatch.model.Table
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KlasemenPresenter(private val view: KlasemenView , private val apiRepository: ApiRepository) {

    fun getKlasemenList(id_liga : String){
        view.showLoading()

        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        connect.getKlasemen(id_liga).enqueue(object : Callback<KlasemenRespone>{
            override fun onFailure(call: Call<KlasemenRespone>, t: Throwable) {

            }

            override fun onResponse(call: Call<KlasemenRespone>, response: Response<KlasemenRespone>) {
               view.hideLoading()
                val get : List<Table> = response.body()!!.table
                view.showKlasemen(get)
            }

        })
    }
}