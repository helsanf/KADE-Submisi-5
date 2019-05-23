package com.example.helsanf.footballmatch.view.pertandingan.detalTeam.DetailFragment


import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.example.helsanf.footballmatch.R
import com.example.helsanf.footballmatch.model.Teams
import com.example.helsanf.footballmatch.preference.MyPreference
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.example.helsanf.footballmatch.utils.invisible
import com.example.helsanf.footballmatch.utils.visible
import com.squareup.picasso.Picasso
import com.example.helsanf.footballmatch.database.database
import com.example.helsanf.footballmatch.database.teams.FavoriteTeamsModel
import org.jetbrains.anko.db.*




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DetailTeamsFragment : Fragment(), DetailTeamsView {


    private lateinit var imgTeams: ImageView
    private lateinit var txtNameTeam: TextView
    private lateinit var txtStadiumTeam: TextView
    private lateinit var txtDescTeam: TextView
    private lateinit var progress: ProgressBar
    private var teams: Teams? = null
    private lateinit var presenter: DetailTeamsPresenter
    private lateinit var preference: MyPreference

    //var untuk favorites menu
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    //    private var matchGo: Tea? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView: View = inflater.inflate(R.layout.fragment_detail_teams, container, false)
        imgTeams = rootView.findViewById(R.id.imgTeamDetail)
        txtNameTeam = rootView.findViewById(R.id.txtNameTeam)
        txtStadiumTeam = rootView.findViewById(R.id.txtStadiumTeam)
        txtDescTeam = rootView.findViewById(R.id.txtDescTeam)
        progress = rootView.findViewById(R.id.progressDetailTeams)
        val apiRepository = ApiRepository()
        preference = MyPreference(this.activity!!)
        presenter = DetailTeamsPresenter(this, apiRepository)
        presenter.getDetailTeams(preference.getIdTeam())
        setHasOptionsMenu(true)
//        favoriteState()
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        favoriteState()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun showDetailTeams(data: Teams) {
        teams = data
        Picasso.get()
            .load(data.strTeamBadge)
            .into(imgTeams)
        txtNameTeam.text = data.strTeam
        txtStadiumTeam.text = data.strStadium
        txtDescTeam.text = data.strDescriptionEN
    }

    override fun showLoading() {
        progress.visible()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    private fun addToFavorite(){
        try {
            activity?.database?.use{
                insert(
                    FavoriteTeamsModel.TABLE_FAVORITE_TEAM,
                    FavoriteTeamsModel.ID_TEAM to teams!!.idTeam,
                    FavoriteTeamsModel.ID_LIGA to teams!!.idLeague,
                    FavoriteTeamsModel.NAMA_TEAM to teams!!.strTeam,
                    FavoriteTeamsModel.LOGO_TEAM to teams!!.strTeamBadge
                )
                Toast.makeText(activity, "Teams Favorite Berhasil di Tambahkan", Toast.LENGTH_SHORT).show()

            }
        }catch (e : SQLiteConstraintException){

        }


    }

    private fun removeFromFavorite() {

            try {
                activity?.database?.use {
                    delete(FavoriteTeamsModel.TABLE_FAVORITE_TEAM, "(ID_TEAM = {id})", "id" to preference.getIdTeam())
                }
                Toast.makeText(activity, "Berhasil menghapus Data", Toast.LENGTH_SHORT).show()

            } catch (e: SQLiteConstraintException) {
                Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
            }

    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this.activity!!, R.drawable.ic_added_to_favorites)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this.activity!!, R.drawable.ic_add_to_favorites)
        }
    }
    private fun favoriteState() {
        //koodingan pengecekan apakah data atau tidak , jika ada rubah isFavorite = true
        activity?.database?.use {
            val result = select(FavoriteTeamsModel.TABLE_FAVORITE_TEAM)
                .whereArgs("(ID_TEAM = {id})", "id" to preference.getIdTeam())
            val favorite = result.parseList(classParser<FavoriteTeamsModel>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        returnTransition
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.add_to_favorite -> {

//                addToFavorite()
                if (isFavorite) removeFromFavorite()
                else  addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
//                Toast.makeText(this.activity, "FAVORITE TEAMS TESTING", Toast.LENGTH_SHORT).show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
