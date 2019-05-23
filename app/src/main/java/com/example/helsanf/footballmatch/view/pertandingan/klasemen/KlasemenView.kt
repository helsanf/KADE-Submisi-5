package com.example.helsanf.footballmatch.view.pertandingan.klasemen

import com.example.helsanf.footballmatch.model.Table

interface KlasemenView {
    fun showKlasemen(klasemen : List<Table>)
    fun showLoading()
    fun hideLoading()
}