package com.example.helsanf.footballmatch.view.pertandingan.detalTeam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.adapter.PagerTeamsAdapter
import com.example.helsanf.footballmatch.preference.MyPreference
import kotlinx.android.synthetic.main.activity_detail_team.*

class DetailTeamActivity : AppCompatActivity() {
    var id : String? = null
    private lateinit var preference: MyPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
                view_pager_teams.adapter = PagerTeamsAdapter(supportFragmentManager)
        tabLayoutTeams.setupWithViewPager(view_pager_teams)
        preference = MyPreference(this)
        id = intent.getStringExtra("id_team")
        preference.setIdTeam(id!!)
    }
}
