package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.player.detailPlayer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.model.Player
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.example.helsanf.footballmatch.utils.invisible
import com.example.helsanf.footballmatch.utils.visible
import com.squareup.picasso.Picasso

class DetailPlayer : AppCompatActivity() , DetailPlayerView {

    private lateinit var imgPlayer : ImageView
    private lateinit var txtName : TextView
    private lateinit var txtClub : TextView
    private lateinit var txtDesc : TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: DetailPlayerPresenter
    private var player : Player? = null

    var id_player : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)
        imgPlayer = findViewById(R.id.imgPlayer)
        txtName = findViewById(R.id.txtNamePlayer)
        txtClub = findViewById(R.id.txtNameClub)
        txtDesc = findViewById(R.id.txtDescPlayer)
        progressBar = findViewById(R.id.progressDetailPlayer)
        val apiRepository = ApiRepository()
        presenter = DetailPlayerPresenter(this,apiRepository)
        id_player = intent.getStringExtra("id_player")
        presenter.getDetailPlayer(id_player!!)

    }
    override fun showDetailPlayer(data: Player) {
        player = data
        txtDesc.text = player?.strDescriptionEN
        txtClub.text = player?.strTeam
        txtName.text = player?.strPlayer

        Picasso.get()
            .load(player?.strCutout)
            .into(imgPlayer)


    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

}
