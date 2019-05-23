package com.example.helsanf.footballmatch.view.pertandingan.prevmatch

import android.util.Log
import com.example.helsanf.footballmatch.model.Event
import com.example.helsanf.footballmatch.model.MatchRespone
import com.example.helsanf.footballmatch.prevmatch.PrevPresenter
import com.example.helsanf.footballmatch.prevmatch.PrevView
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrevPresenterTest {

    @Mock
    private lateinit var view: PrevView
    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var presenter: PrevPresenter



    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        apiRepository = ApiRepository()
        presenter = PrevPresenter(view, apiRepository)
    }

    @Test
    fun getLastMatch() {
        val id = "4328"
        presenter.getLastMatch(id)
        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        argumentCaptor<PrevView>().apply {
            connect.getLastMatch(id).enqueue(object :  Callback<MatchRespone> {
                override fun onFailure(call: Call<MatchRespone>, t: Throwable) {

                }

                override fun onResponse(call: Call<MatchRespone>, response: Response<MatchRespone>) {
                    view.hideLoading()
                    val get = response.body()!!.events
                    firstValue.ShowMatchList(get)
                    Mockito.verify(view.ShowMatchList(get))
                }

            })
        }



    }

    @Test
    fun getTeamSearch() {
        val query = "arsenal"
        val connect: ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        presenter.getTeamSearch(query)
        argumentCaptor<PrevView>().apply {
            connect.getSearchMatch(query).enqueue(object : Callback<MatchRespone> {
                override fun onFailure(call: Call<MatchRespone>, t: Throwable) {
                    Log.d("tag", "responsennya ${t.message}")

                }

                override fun onResponse(call: Call<MatchRespone>, response: Response<MatchRespone>) {
                    view.hideLoading()
                    val get: List<Event>? = response.body()!!.event
                    firstValue.ShowMatchList(get!!)
                    Mockito.verify(view.ShowMatchList(get!!))

                }

            })
        }

    }
}