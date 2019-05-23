package com.example.helsanf.footballmatch.adapter


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.database.nextmatch.FavoriteModelNext
import java.text.ParseException
import java.text.SimpleDateFormat

class FavoriteAdapterNext(
    private val match: List<FavoriteModelNext>,
    private val context: Context,
    private val listener: (FavoriteModelNext) -> Unit
) : RecyclerView.Adapter<FavoriteHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): FavoriteHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_match, viewGroup, false)
        return FavoriteHolder(view)
    }

    override fun getItemCount(): Int {
        return match.size
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
       holder.bindItem(match[position],listener)
    }
}

class FavoriteHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
    val homeTeam: TextView = itemview.findViewById(R.id.homeTeams)
    val awayTeam: TextView = itemview.findViewById(R.id.awayTeams)
    val tanggal: TextView = itemview.findViewById(R.id.tanggalMatch)
    val scoreHome: TextView = itemview.findViewById(R.id.scoreHome)
    val scoreAway: TextView = itemview.findViewById(R.id.scoreAway)
    fun bindItem(teams: FavoriteModelNext, listener: (FavoriteModelNext) -> Unit) {
        val getDate: String = teams.tanggal.toString()

        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val date = simpleDateFormat.parse(getDate)
            val newFormat = SimpleDateFormat("EEEE, MMM dd, yyyy")
            val dateFix = newFormat.format(date)
            tanggal.text = dateFix
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        scoreAway.text = teams.scoreAway
        scoreHome.text = teams.scoreHome


        awayTeam.text = teams.awayTeam
        homeTeam.text = teams.homeTeam

        itemView.setOnClickListener { listener(teams) }
    }
}
