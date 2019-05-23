package com.example.helsanf.footballmatch.view.pertandingan.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.adapter.MyPagerAdapter
import com.example.helsanf.footballmatch.adapter.PagerFavoriteAdapter
import com.example.helsanf.footballmatch.model.League
import com.example.helsanf.footballmatch.preference.MyPreference
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() , HomeView {


    private lateinit var imgLiga : ImageView
    private lateinit var txtLiga : TextView
    private lateinit var preference: MyPreference
    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

         val rootView : View = inflater.inflate(R.layout.fragment_home, container, false)
        imgLiga = rootView.findViewById(R.id.ligaImage)
        txtLiga = rootView.findViewById(R.id.textLiga)
        val apiRepository = ApiRepository()
        val presenter = HomePresenter(this,apiRepository)
        preference = MyPreference(this.activity!!)
        presenter.getDetailLiga(preference.getIdLiga())
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(activity as AppCompatActivity){
            view_pager_main.adapter = MyPagerAdapter(childFragmentManager)
            tabLayout.setupWithViewPager(view_pager_main)
        }

    }

    override fun showLiga(liga: League) {
        activity?.setTitle(liga.strLeague)
        Picasso.get()
            .load(liga.strFanart1)
            .into(imgLiga)
        txtLiga.text = liga.strLeague
    }


}
