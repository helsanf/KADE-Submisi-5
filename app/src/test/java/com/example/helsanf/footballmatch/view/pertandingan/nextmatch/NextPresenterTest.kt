package com.example.helsanf.footballmatch.view.pertandingan.nextmatch

import android.util.Log
import android.widget.Toast
import com.example.helsanf.footballmatch.model.Event
import com.example.helsanf.footballmatch.model.MatchRespone
import com.example.helsanf.footballmatch.nextmatch.NextPresenter
import com.example.helsanf.footballmatch.nextmatch.NextView
import com.example.helsanf.footballmatch.prevmatch.PrevView
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NextPresenterTest {

    @Mock
    private lateinit var view: NextView
    @Mock
    private lateinit var apiRepository: ApiRepository
    private lateinit var presenter: NextPresenter
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        apiRepository = ApiRepository()
        presenter = NextPresenter(view, apiRepository)
    }

    @Test
    fun getTeamList() {
        val id = "4328"
        presenter.getTeamList(id)
        val connect: ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        argumentCaptor<NextView>().apply {
            connect.getScheduleNext(id).enqueue(object : Callback<MatchRespone> {
                override fun onFailure(call: Call<MatchRespone>, t: Throwable) {

                }

                override fun onResponse(call: Call<MatchRespone>, response: Response<MatchRespone>) {

                    val get: List<Event>? = response.body()!!.events
                    firstValue.showScheduleList(get!!)
                    Mockito.verify(view.showScheduleList(get!!))
                }

            })
        }

    }

    @Test
    fun getTeamSearch() {
        val club = "chelsea"
        presenter.getTeamSearch(club)
        val connect: ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        argumentCaptor<PrevView>().apply {
            connect.getSearchMatch(club).enqueue(object : Callback<MatchRespone> {
                override fun onFailure(call: Call<MatchRespone>, t: Throwable) {
                    Log.d("tag", "responsennya ${t.message}")

                }

                override fun onResponse(call: Call<MatchRespone>, response: Response<MatchRespone>) {
                    view.hideLoading()
                    val get: List<Event>? = response.body()!!.event
                    firstValue.ShowMatchList(get!!)
                    Mockito.verify(view.showScheduleList(get!!))


                }

            })
        }

    }
}