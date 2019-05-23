package com.example.helsanf.footballmatch.view.pertandingan.home

import com.example.helsanf.footballmatch.model.League
import com.example.helsanf.footballmatch.model.LeagueResponse
import com.example.helsanf.footballmatch.rest.ApiInterface
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.example.helsanf.footballmatch.rest.RepositoryUnitTest
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePresenterTest {

    @Mock
    private lateinit var view: HomeView
    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var presenter: HomePresenter
    @Mock
    private lateinit var leagueResponse: League

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        apiRepository = ApiRepository()
        presenter = HomePresenter(view, apiRepository)
    }


    @Test
    fun getDetailLiga() {
        val id = "4328"
        presenter.getDetailLiga(id)
        val connect : ApiInterface = apiRepository.getUrl().create(ApiInterface::class.java)
        argumentCaptor<HomeView>().apply {
            connect.getDetailLeugue(id).enqueue(object : Callback<LeagueResponse> {
                override fun onFailure(call: Call<LeagueResponse>, t: Throwable) {
                    Mockito.verify(t.message)
                }

                override fun onResponse(call: Call<LeagueResponse>, response: Response<LeagueResponse>) {
                    val get: League = response.body()!!.leagues.get(0)
                    firstValue.showLiga(get)
                    Mockito.verify(view.showLiga(get))
                }

            })
        }

    }
}