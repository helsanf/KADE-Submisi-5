package com.example.helsanf.footballmatch.view.pertandingan.klasemen


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
import com.example.helsanf.footballmatch.adapter.KlasemenAdapter
import com.example.helsanf.footballmatch.model.Table
import com.example.helsanf.footballmatch.preference.MyPreference
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.example.helsanf.footballmatch.utils.invisible
import com.example.helsanf.footballmatch.utils.visible
import com.mlsdev.animatedrv.AnimatedRecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class KlasemenFragment : Fragment(), KlasemenView {


    private lateinit var recycle: AnimatedRecyclerView
    private var adapter: KlasemenAdapter? = null
    private lateinit var presenter: KlasemenPresenter
    private var table: MutableList<Table> = mutableListOf()
    private var progress: ProgressBar? = null
    private lateinit var preference: MyPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_klasemen, container, false)
        recycle = rootView.findViewById(R.id.rvKlasemen)
        progress = rootView.findViewById(R.id.progressKlasemen)
        preference = MyPreference(this.activity!!)
        showKlasemen()
        return rootView
    }

    private fun itemMatchClicked(item: Table) {
//        val intent = Intent(activity, DetailActivity::class.java)
//        intent.putExtra("id_event", item.idEvent)
//        intent.putExtra("id_home", item.idHomeTeam)
//        intent.putExtra("id_away", item.idAwayTeam)
//        startActivity(intent)
        Toast.makeText(context, "DI KLIK SUKSES", Toast.LENGTH_SHORT).show()
    }

    override fun showKlasemen(klasemen: List<Table>) {
        table.clear()
        table.addAll(klasemen)
        adapter = KlasemenAdapter(table, { itemMatch: Table -> itemMatchClicked(itemMatch) })
        recycle!!.adapter = adapter
        recycle!!.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this.activity)

        recycle!!.layoutManager = layoutManager

        adapter!!.notifyDataSetChanged()
        recycle.scheduleLayoutAnimation()
    }

    override fun showLoading() {
        progress?.visible()
    }

    override fun hideLoading() {
        progress?.invisible()
    }

    fun showKlasemen() {
        val apiRepository = ApiRepository()
        presenter = KlasemenPresenter(this, apiRepository)
        presenter.getKlasemenList(preference.getIdLiga())

    }
}
