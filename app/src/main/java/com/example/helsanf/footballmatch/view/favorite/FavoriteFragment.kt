package com.example.helsanf.footballmatch.view.favorite


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.adapter.PagerFavoriteAdapter


import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val rootview : View= inflater.inflate(R.layout.fragment_favorite, container, false)
        activity?.setTitle("Favorites")
        return rootview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(activity as AppCompatActivity){
            pagerFavorite.adapter = PagerFavoriteAdapter(childFragmentManager)
            tabFavorite.setupWithViewPager(pagerFavorite)
        }

    }
}
