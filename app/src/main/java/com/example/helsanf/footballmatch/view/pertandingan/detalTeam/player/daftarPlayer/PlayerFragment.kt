package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.player.daftarPlayer


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast

import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.adapter.PlayersAdapter
import com.example.helsanf.footballmatch.model.Player
import com.example.helsanf.footballmatch.preference.MyPreference
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.example.helsanf.footballmatch.utils.invisible
import com.example.helsanf.footballmatch.utils.visible
import com.example.helsanf.footballmatch.view.pertandingan.detalTeam.player.detailPlayer.DetailPlayer
import com.mlsdev.animatedrv.AnimatedRecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PlayerFragment : Fragment() , PlayerView {


    private lateinit var recycler: AnimatedRecyclerView
    private lateinit var presenter: PlayerPresenter
    private lateinit var progress: ProgressBar
    private var player: MutableList<Player> = mutableListOf()
    private var adapter: PlayersAdapter? = null
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView : View = inflater.inflate(R.layout.fragment_player, container, false)
        recycler = rootView.findViewById(R.id.rv_player)
        progress = rootView.findViewById(R.id.progressPlayer)
        preference = MyPreference(this.activity!!)
        showListPlayer()
        return rootView
    }
    fun getItemClick(item: Player) {
        val intent = Intent(activity,DetailPlayer::class.java)
        intent.putExtra("id_player",item.idPlayer)
        startActivity(intent)
//        Toast.makeText(context, "Testing Klik Player", Toast.LENGTH_SHORT).show()

    }
    override fun showListPlayer(data: List<Player>) {
        player.clear()
        player.addAll(data)
        adapter = PlayersAdapter(player,this.activity!!,{ itemTeams: Player -> getItemClick(itemTeams) })
        recycler!!.adapter = adapter
        recycler!!.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.activity)
        recycler!!.layoutManager = layoutManager
        adapter!!.notifyDataSetChanged()
        recycler.scheduleLayoutAnimation()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    override fun showLoading() {
        progress.visible()
    }
    fun showListPlayer(){
        val apiRepository = ApiRepository()
        presenter = PlayerPresenter(this,apiRepository)
        presenter.getListPlayer(preference.getIdTeam())
    }
}
