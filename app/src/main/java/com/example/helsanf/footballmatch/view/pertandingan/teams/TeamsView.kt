package com.example.helsanf.footballmatch.view.pertandingan.teams

import com.example.helsanf.footballmatch.model.Teams

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data : List<Teams>)
}