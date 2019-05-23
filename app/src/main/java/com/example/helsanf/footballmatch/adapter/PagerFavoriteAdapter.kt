package com.example.helsanf.footballmatch.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.helsanf.footballmatch.view.favorite.nextmatch.FavoriteNextFragment
import com.example.helsanf.footballmatch.view.favorite.prevmatch.FavoritePrevFragment
import com.example.helsanf.footballmatch.view.favorite.teams.TeamsFavoriteFragment

class PagerFavoriteAdapter (fragment : FragmentManager) : FragmentPagerAdapter(fragment) {
    private val pages = listOf(
        FavoriteNextFragment(),
        FavoritePrevFragment(),
        TeamsFavoriteFragment()
    )
    override fun getItem(position: Int): Fragment {
        return pages[position]

    }

    override fun getCount(): Int {
        return pages.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0 -> "Next Match"
            1 -> "Previous Match"
            else -> "Teams"
        }
    }
}