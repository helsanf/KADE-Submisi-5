package com.example.helsanf.footballmatch.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.database.teams.FavoriteTeamsModel
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class FavoriteTeamsAdapter(private val teams : List<FavoriteTeamsModel>, private val context: Context,
                           private val listener: (FavoriteTeamsModel) -> Unit
) : RecyclerView.Adapter<FavoriteTeamsHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): FavoriteTeamsHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_league_row, viewGroup, false)
        return FavoriteTeamsHolder(view)
    }

    override fun getItemCount(): Int {
       return teams.size
    }

    override fun onBindViewHolder(holder: FavoriteTeamsHolder, position: Int) {
        holder.bindItem(teams[position],listener)

    }
}

class FavoriteTeamsHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    val teamBadge: ImageView = itemView.find(R.id.imgLiga)
    val teamName: TextView = itemView.find(R.id.nameLeague)
    fun bindItem(player: FavoriteTeamsModel, listener: (FavoriteTeamsModel) -> Unit) {
        Picasso.get().load(player.logoTeam).into(teamBadge)
        teamName.text = player.namaTeam
        itemView.setOnClickListener { listener(player) }
    }
}
