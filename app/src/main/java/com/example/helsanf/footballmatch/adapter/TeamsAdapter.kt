package com.example.helsanf.footballmatch.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.model.Teams
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class TeamsAdapter(private val teams: List<Teams>, private val context: Context , private val listener: (Teams) -> Unit) :
    RecyclerView.Adapter<TeamsViewHolder>() {
    override fun onCreateViewHolder(viewgroup: ViewGroup, p1: Int): TeamsViewHolder {
        return TeamsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_league_row, viewgroup, false))

    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(teams[position],listener)
    }
}

class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val teamBadge: ImageView = itemView.find(R.id.imgLiga)
    val teamName: TextView = itemView.find(R.id.nameLeague)
    fun bindItem(teams: Teams , listener: (Teams) -> Unit) {
        Picasso.get().load(teams.strTeamBadge).into(teamBadge)
        teamName.text = teams.strTeam
        itemView.setOnClickListener { listener(teams) }
    }

}
