package com.example.helsanf.footballmatch.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.helsanf.footballmatch.view.pertandingan.detalTeam.DetailFragment.DetailTeamsFragment
import com.example.helsanf.footballmatch.view.pertandingan.detalTeam.player.daftarPlayer.PlayerFragment

class PagerTeamsAdapter(fragment : FragmentManager) : FragmentPagerAdapter(fragment) {

    private val pages = listOf(
        DetailTeamsFragment(),
        PlayerFragment()
    )
    override fun getItem(position: Int): Fragment {
        return pages[position]

    }

    override fun getCount(): Int {
        return pages.size

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Detail Teams"
            else -> "Daftar Pemain"
        }
    }
}