package com.example.helsanf.footballmatch.view.favorite.nextmatch


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
import com.example.helsanf.footballmatch.adapter.FavoriteAdapterNext
import com.example.helsanf.footballmatch.database.nextmatch.FavoriteModelNext
import com.example.helsanf.footballmatch.database.database
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
class FavoriteNextFragment : Fragment() {
    private lateinit var recyclerView: AnimatedRecyclerView
    private lateinit var proggressbar: ProgressBar
    private lateinit var adapter: FavoriteAdapterNext
    private lateinit var preference: MyPreference

    //inisialisasi fav
    private var favorites: MutableList<FavoriteModelNext> = mutableListOf()
    private lateinit var dataSearch : List<FavoriteModelNext>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val rootview: View = inflater.inflate(R.layout.fragment_favorite_next, container, false)
        recyclerView = rootview.findViewById(R.id.rv_next_Fav)
        proggressbar = rootview.findViewById(R.id.progressFavNext)
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()

    }
    private fun initAdapter(){
        adapter =
            FavoriteAdapterNext(favorites, this.activity!!, { itemTeams: FavoriteModelNext -> getItemClick(itemTeams) })
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.activity)
        recyclerView.layoutManager = layoutManager
    }

    fun getItemClick(item: FavoriteModelNext) {
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
    private fun searchAnything(keyword : String){
        val filteredList = ArrayList<FavoriteModelNext>()
        for (s : FavoriteModelNext in dataSearch) {
            //if the existing elements contains the search input
            if (s.homeTeam?.toLowerCase()?.contains(keyword)!! || s.awayTeam?.toLowerCase()?.contains(keyword)!!) {
                //adding the element to filtered list
                filteredList.add(s)
            }
        }
        adapter =
            FavoriteAdapterNext(filteredList, this.activity!!, { itemTeams: FavoriteModelNext -> getItemClick(itemTeams) })
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        recyclerView.scheduleLayoutAnimation()

    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            proggressbar.invisible()
            val result = select(FavoriteModelNext.TABLE_FAVORITE_NEXT)
                .whereArgs("(ID_LIGA = {id})" , "id" to preference.getIdLiga())
            val favorite = result.parseList(classParser<FavoriteModelNext>())
            dataSearch = favorite
            favorites.addAll(favorite)
            adapter.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()

        }
    }
}
