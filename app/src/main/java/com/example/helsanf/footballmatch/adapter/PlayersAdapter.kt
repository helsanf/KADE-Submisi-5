package com.example.helsanf.footballmatch.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class PlayersAdapter(private val player: List<Player>, private val context: Context, private val listener: (Player) -> Unit) :
    RecyclerView.Adapter<PlayersViewHolder>() {
    override fun onCreateViewHolder(viewgroup: ViewGroup, p1: Int): PlayersViewHolder {
        return PlayersViewHolder(LayoutInflater.from(context).inflate(R.layout.item_league_row, viewgroup, false))

    }

    override fun getItemCount(): Int {
        return player.size
    }

    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        holder.bindItem(player[position],listener)
    }
}

class PlayersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val teamBadge: ImageView = itemView.find(R.id.imgLiga)
    val teamName: TextView = itemView.find(R.id.nameLeague)
    fun bindItem(player: Player, listener: (Player) -> Unit) {
        Picasso.get().load(player.strCutout).into(teamBadge)
        teamName.text = player.strPlayer
        itemView.setOnClickListener { listener(player) }
    }

}