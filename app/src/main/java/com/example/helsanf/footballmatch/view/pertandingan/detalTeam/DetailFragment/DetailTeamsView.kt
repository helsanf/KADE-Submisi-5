package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.DetailFragment

import com.example.helsanf.footballmatch.model.Teams

interface DetailTeamsView {
    fun showDetailTeams(data : Teams)
    fun showLoading()
    fun hideLoading()
}