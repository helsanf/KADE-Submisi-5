package com.example.helsanf.footballmatch.view.favorite.teams


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.ProgressBar

import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.adapter.FavoriteTeamsAdapter
import com.example.helsanf.footballmatch.database.database
import com.example.helsanf.footballmatch.database.teams.FavoriteTeamsModel
import com.example.helsanf.footballmatch.preference.MyPreference
import com.example.helsanf.footballmatch.utils.invisible
import com.example.helsanf.footballmatch.view.pertandingan.detalTeam.DetailTeamActivity
import com.mlsdev.animatedrv.AnimatedRecyclerView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TeamsFavoriteFragment : Fragment() {
    private lateinit var recyclerView: AnimatedRecyclerView
    private lateinit var proggressbar: ProgressBar
    private lateinit var adapter: FavoriteTeamsAdapter
    private lateinit var preference: MyPreference

    //inisialisasi fav
    private var favorites: MutableList<FavoriteTeamsModel> = mutableListOf()
    private lateinit var dataSearch : List<FavoriteTeamsModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val rootView : View = inflater.inflate(R.layout.fragment_teams_favorite, container, false)
        recyclerView = rootView.findViewById(R.id.rv_teamsFav)
        proggressbar = rootView.findViewById(R.id.progressFavTeam)
        preference = MyPreference(this.activity!!)
        return rootView
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)
        val searchview = menu!!.findItem(R.id.searchView)?.actionView as SearchView
        searchview.isSubmitButtonEnabled = true
        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(querys: String?): Boolean {
                if (querys != null) {
                    searchAnything(querys)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()

    }
    private fun initAdapter(){
        adapter =
            FavoriteTeamsAdapter(favorites, this.activity!!, { itemTeams: FavoriteTeamsModel -> getItemClick(itemTeams) })
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.activity)
        recyclerView.layoutManager = layoutManager
    }

    fun getItemClick(item: FavoriteTeamsModel) {
        val intent = Intent(activity, DetailTeamActivity::class.java)
        intent.putExtra("id_team", item.idTeam)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }
    private fun searchAnything(keyword : String){
        val filteredList = ArrayList<FavoriteTeamsModel>()
        for (s : FavoriteTeamsModel in dataSearch) {
            //if the existing elements contains the search input
            if (s.namaTeam?.toLowerCase()?.contains(keyword)!!) {
                //adding the element to filtered list
                filteredList.add(s)
            }
        }
        adapter =
            FavoriteTeamsAdapter(filteredList, this.activity!!, { itemTeams: FavoriteTeamsModel -> getItemClick(itemTeams) })
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()

    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            proggressbar.invisible()
            val result = select(FavoriteTeamsModel.TABLE_FAVORITE_TEAM)
                .whereArgs("(ID_LIGA = {id})" , "id" to preference.getIdLiga())
            val favorite = result.parseList(classParser<FavoriteTeamsModel>())
            dataSearch = favorite
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()

        }
    }



}
