package com.example.helsanf.footballmatch.view.favorite.prevmatch


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import com.example.helsanf.footballmatch.DetailActivity

import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.adapter.FavoriteAdapterPrev
import com.example.helsanf.footballmatch.database.database
import com.example.helsanf.footballmatch.database.prevmatch.FavoriteModelPrev
import com.example.helsanf.footballmatch.preference.MyPreference
import com.example.helsanf.footballmatch.utils.invisible
import com.mlsdev.animatedrv.AnimatedRecyclerView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class FavoritePrevFragment : Fragment() {

    private lateinit var recyclerView: AnimatedRecyclerView
    private lateinit var proggressbar: ProgressBar
    private lateinit var adapter: FavoriteAdapterPrev
    private lateinit var preference: MyPreference

    //inisialisasi fav
    private var favorites: MutableList<FavoriteModelPrev> = mutableListOf()
    private lateinit var dataSearch : List<FavoriteModelPrev>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val rootview : View = inflater.inflate(R.layout.fragment_favorite_prev, container, false)
        recyclerView = rootview.findViewById(R.id.rv_prev_Fav)
        proggressbar = rootview.findViewById(R.id.progressFavPrev)
        preference = MyPreference(this.activity!!)
        return rootview
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
    private fun searchAnything(keyword : String){
        val filteredList = ArrayList<FavoriteModelPrev>()
        for (s : FavoriteModelPrev in dataSearch) {
            //if the existing elements contains the search input
            if (s.homeTeam?.toLowerCase()?.contains(keyword)!! || s.awayTeam?.toLowerCase()?.contains(keyword)!!) {
                //adding the element to filtered list
                filteredList.add(s)
                Log.e("Test : ",s.homeTeam)
            }
        }
        adapter =
            FavoriteAdapterPrev(filteredList, this.activity!!, { itemTeams: FavoriteModelPrev -> getItemClick(itemTeams) })
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       initAdapter()
    }
    private fun initAdapter(){
        adapter =
            FavoriteAdapterPrev(favorites, this.activity!!, { itemTeams: FavoriteModelPrev -> getItemClick(itemTeams) })
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.activity)
        recyclerView.layoutManager = layoutManager
    }
    fun getItemClick(item: FavoriteModelPrev) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra("id_event", item.idEvent)
        intent.putExtra("id_home", item.idHomeTeam)
        intent.putExtra("id_away", item.idAwayTeam)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            proggressbar.invisible()
            val result = select(FavoriteModelPrev.TABLE_FAVORITE_PREV)
                .whereArgs("(ID_LIGA = {id})","id" to preference.getIdLiga() )
            val favorite = result.parseList(classParser<FavoriteModelPrev>())
            favorites.addAll(favorite)
            dataSearch = favorite
            adapter.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()

        }
    }


}
