package com.example.helsanf.footballmatch.view.pertandingan.teams


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ProgressBar

import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.adapter.TeamsAdapter
import com.example.helsanf.footballmatch.model.Teams
import com.example.helsanf.footballmatch.preference.MyPreference
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.example.helsanf.footballmatch.utils.invisible
import com.example.helsanf.footballmatch.utils.visible
import com.example.helsanf.footballmatch.view.pertandingan.detalTeam.DetailTeamActivity
import com.mlsdev.animatedrv.AnimatedRecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamsFragment : Fragment(), TeamsView {


    private lateinit var recycler: AnimatedRecyclerView
    private lateinit var presenter: TeamsPresenter
    private lateinit var progress: ProgressBar
    private var teams: MutableList<Teams> = mutableListOf()
    private var adapter: TeamsAdapter? = null
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_teams, container, false)
        setHasOptionsMenu(true)
        recycler = rootView.findViewById(R.id.rv_teams)
        progress = rootView.findViewById(R.id.progressTeams)
        preference = MyPreference(this.activity!!)
        showTeams()
        return rootView
    }

    override fun showLoading() {
        progress.visible()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    fun getItemClick(item: Teams) {
        val intent = Intent(activity,DetailTeamActivity::class.java)
        intent.putExtra("id_team",item.idTeam)
        startActivity(intent)
    }

    override fun showTeamList(data: List<Teams>) {
        teams.clear()
        teams.addAll(data)
        adapter = TeamsAdapter(teams, this.activity!!, { itemTeams: Teams -> getItemClick(itemTeams) })
        recycler!!.adapter = adapter
        recycler!!.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.activity)
        recycler!!.layoutManager = layoutManager
        adapter!!.notifyDataSetChanged()
        recycler.scheduleLayoutAnimation()
    }

    fun showTeams(){
        val apiRepository = ApiRepository()
        presenter = TeamsPresenter(this,apiRepository)
        presenter.TeamsPresenter(this.activity!!)
        presenter.getTeamsList(preference.getIdLiga())
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)
        val searchview = menu!!.findItem(R.id.searchView)?.actionView as SearchView
        searchview.isSubmitButtonEnabled = true
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(querys: String?): Boolean {
                if (querys != null) {
                    recycler!!.adapter = null
                    searchMatch(querys)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })

    }

    fun searchMatch(query: String) {
        val request = ApiRepository()
        presenter = TeamsPresenter(this, request)
        presenter.TeamsPresenter(this.activity!!)
        presenter.getTeamSearch(query)
    }

}
