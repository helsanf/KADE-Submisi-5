package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.player.daftarPlayer

import com.example.helsanf.footballmatch.model.Player

interface PlayerView {
    fun showListPlayer(data : List<Player>)
    fun hideLoading()
    fun showLoading()

}