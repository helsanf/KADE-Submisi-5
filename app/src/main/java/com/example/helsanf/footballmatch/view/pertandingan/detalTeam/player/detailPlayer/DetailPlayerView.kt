package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.player.detailPlayer

import com.example.helsanf.footballmatch.model.Player

interface DetailPlayerView {
    fun showDetailPlayer(data : Player)
    fun showLoading()
    fun hideLoading()
}