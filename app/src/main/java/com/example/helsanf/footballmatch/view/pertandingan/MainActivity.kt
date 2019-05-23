package com.example.helsanf.footballmatch.view.pertandingan

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.preference.MyPreference
import com.example.helsanf.footballmatch.view.favorite.FavoriteFragment
import com.example.helsanf.footballmatch.view.pertandingan.home.HomeFragment
import com.example.helsanf.footballmatch.view.pertandingan.teams.TeamsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var id_liga : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        view_pager_main.adapter = MyPagerAdapter(supportFragmentManager)
//        tabLayout.setupWithViewPager(view_pager_main)
        val preference = MyPreference(this)
        id_liga = intent.getStringExtra("id_liga")
        preference.setIdLiga(id_liga)
        bottomFavorites.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.match -> {
                    loadMatchFragment(savedInstanceState)
                }
                R.id.favorites -> {
                    loadFavoriteFragment(savedInstanceState)
                }
                R.id.teams ->{
                    loadTeamsFragment(savedInstanceState)
                }
            }
            true
        }
        bottomFavorites.selectedItemId = R.id.match
    }


    private fun loadMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.mainContainerFavorites,
                    HomeFragment(), HomeFragment::class.java.simpleName
                )
                .commit()
        }
    }

    private fun loadFavoriteFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.mainContainerFavorites,
                    FavoriteFragment(), FavoriteFragment::class.java.simpleName
                )
                .commit()
        }
    }
    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.mainContainerFavorites,
                    TeamsFragment(), TeamsFragment::class.java.simpleName
                )
                .commit()
        }
    }
}
