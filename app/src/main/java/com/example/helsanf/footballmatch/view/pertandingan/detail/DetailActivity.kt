package com.example.helsanf.footballmatch

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.helsanf.footballmatch.database.nextmatch.FavoriteModelNext
import com.example.helsanf.footballmatch.database.database
import com.example.helsanf.footballmatch.database.prevmatch.FavoriteModelPrev
import com.example.helsanf.footballmatch.model.Event
import com.example.helsanf.footballmatch.model.Teams
import com.example.helsanf.footballmatch.rest.ApiRepository
import com.example.helsanf.footballmatch.utils.invisible
import com.example.helsanf.footballmatch.utils.visible
import com.squareup.picasso.Picasso
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailActivity : AppCompatActivity(), DetailView {



    private lateinit var id_event: String
    var id_home: String? = null
    var id_away: String? = null
    private lateinit var presenter: DetailPresenter
    var txtScoreHome: TextView? = null
    var txtScoreAway: TextView? = null
    var txtNamaTeams: TextView? = null
    var txtGoalHome: TextView? = null
    var txtGoalAway: TextView? = null
    var txtKiperHome: TextView? = null
    var txtKiperAway: TextView? = null
    var txtBekHome: TextView? = null
    var txtBekAway: TextView? = null
    var txtGelandangHome: TextView? = null
    var txtGelandangAway: TextView? = null
    var txtPenyerangHome: TextView? = null
    var txtPenyerangAway: TextView? = null
    var imgHome: ImageView? = null
    var imgAway: ImageView? = null
    var progress: ProgressBar? = null
    var linear: LinearLayout? = null

    //var untuk favorites menu
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private var matchGo: Event? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        txtScoreAway = findViewById(R.id.scoreAway)
        txtScoreHome = findViewById(R.id.scoreHome)
        imgHome = findViewById(R.id.imgViewHome)
        imgAway = findViewById(R.id.imgAway)
        txtNamaTeams = findViewById(R.id.namaTeamsBertanding)
        txtGoalHome = findViewById(R.id.goalHome)
        txtGoalAway = findViewById(R.id.goalAway)
        txtKiperHome = findViewById(R.id.kiperHome)
        txtKiperAway = findViewById(R.id.kiperAway)
        txtBekHome = findViewById(R.id.bekHome)
        txtBekAway = findViewById(R.id.bekAway)
        txtGelandangHome = findViewById(R.id.gelandangHome)
        txtGelandangAway = findViewById(R.id.gelandangAway)
        txtPenyerangHome = findViewById(R.id.penyerangHome)
        txtPenyerangAway = findViewById(R.id.penyerangAway)
        linear = findViewById(R.id.linearLayout)
        progress = findViewById(R.id.progressDetail)

        val apiRepository = ApiRepository()
        presenter = DetailPresenter(this, apiRepository)
        id_event = intent.getStringExtra("id_event")
        id_home = intent.getStringExtra("id_home")
        id_away = intent.getStringExtra("id_away")

        Log.d("Id Event : ", id_home)
        presenter.getDetailEvent(id_event!!)
        presenter.getDetailHome(id_home!!)
        presenter.getDetailAway(id_away!!)

        favoriteState()
        favoriteStatePref()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite()
                else  addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun addToFavorite() {

        if (matchGo?.intHomeScore == null) {
            try {
                database.use {
                    insert(
                        FavoriteModelNext.TABLE_FAVORITE_NEXT,
                        FavoriteModelNext.ID_EVENT to matchGo?.idEvent,
                        FavoriteModelNext.HOME_TEAM to matchGo?.strHomeTeam,
                        FavoriteModelNext.AWAY_TEAM to matchGo?.strAwayTeam,
                        FavoriteModelNext.TANGGAL to matchGo?.dateEvent,
                        FavoriteModelNext.SCORE_HOME to matchGo?.intHomeScore,
                        FavoriteModelNext.SCORE_AWAY to matchGo?.intAwayScore,
                        FavoriteModelNext.ID_HOME_TEAM to matchGo?.idHomeTeam,
                        FavoriteModelNext.ID_AWAY_TEAM to matchGo?.idAwayTeam,
                        FavoriteModelNext.ID_LIGA to matchGo?.idLeague
                    )
                }
                Toast.makeText(this, "Data Next Match Berhasil di Tambahkan", Toast.LENGTH_SHORT).show()
            } catch (e: SQLiteConstraintException) {

            }
        } else {
            try {
                database.use {
                    insert(
                        FavoriteModelPrev.TABLE_FAVORITE_PREV,
                        FavoriteModelPrev.ID_EVENT to matchGo?.idEvent,
                        FavoriteModelPrev.HOME_TEAM to matchGo?.strHomeTeam,
                        FavoriteModelPrev.AWAY_TEAM to matchGo?.strAwayTeam,
                        FavoriteModelPrev.TANGGAL to matchGo?.dateEvent,
                        FavoriteModelPrev.SCORE_HOME to matchGo?.intHomeScore,
                        FavoriteModelPrev.SCORE_AWAY to matchGo?.intAwayScore,
                        FavoriteModelPrev.ID_HOME_TEAM to matchGo?.idHomeTeam,
                        FavoriteModelPrev.ID_AWAY_TEAM to matchGo?.idAwayTeam,
                        FavoriteModelPrev.ID_LIGA to matchGo?.idLeague
                    )
                }
                Toast.makeText(this, "Data Prev Match Berhasil di Tambahkan", Toast.LENGTH_SHORT).show()

            } catch (e: SQLiteConstraintException) {

            }
        }


    }

    private fun removeFromFavorite() {


        if (matchGo?.intHomeScore == null) {
            try {
                database.use {
                    delete(FavoriteModelNext.TABLE_FAVORITE_NEXT, "(ID_EVENT = {id})", "id" to id_event)
                }
                Toast.makeText(this, "Berhasil menghapus Data", Toast.LENGTH_SHORT).show()

            } catch (e: SQLiteConstraintException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            try {
                database.use {
                    delete(FavoriteModelPrev.TABLE_FAVORITE_PREV, "(ID_EVENT = {id})", "id" to id_event)
                }
                Toast.makeText(this, "Berhasil menghapus Data", Toast.LENGTH_SHORT).show()

            } catch (e: SQLiteConstraintException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setFavorite() {
        if (isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
        }
    }

    private fun favoriteState() {
        //koodingan pengecekan apakah data atau tidak , jika ada rubah isFavorite = true
        database.use {
            val result = select(FavoriteModelNext.TABLE_FAVORITE_NEXT)
                .whereArgs("(ID_EVENT = {id})", "id" to id_event)
            val favorite = result.parseList(classParser<FavoriteModelNext>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun favoriteStatePref() {
        database.use {
            val result = select(FavoriteModelPrev.TABLE_FAVORITE_PREV)
                .whereArgs("(ID_EVENT = {id})", "id" to id_event)
            val favorite = result.parseList(classParser<FavoriteModelPrev>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showHomeTeam(teams: Teams) {
        Picasso.get()
            .load(teams.strTeamBadge)
            .into(imgHome)

    }

    override fun showAwayTeam(teams: Teams) {
        Picasso.get()
            .load(teams.strTeamBadge)
            .into(imgAway)

    }


    override fun showloading() {
        progress?.visible()
    }

    override fun hideloading() {
        progress?.invisible()
    }
    override fun showView() {
        linear?.visible()
    }
    override fun showDetailEvent(event: Event) {
        matchGo = event
        txtScoreHome!!.text = event.intHomeScore
        txtScoreAway!!.text = event.intAwayScore
        txtNamaTeams!!.text = event.strEvent
        txtGoalHome!!.text = event.strHomeGoalDetails
        txtGoalAway!!.text = event.strAwayGoalDetails
        txtKiperHome!!.text = event.strHomeLineupGoalkeeper
        txtKiperAway!!.text = event.strAwayLineupGoalkeeper
        txtBekHome!!.text = event.strHomeLineupDefense
        txtBekAway!!.text = event.strAwayLineupDefense
        txtPenyerangHome!!.text = event.strHomeLineupForward
        txtPenyerangAway!!.text = event.strAwayLineupForward
        txtGelandangHome!!.text = event.strHomeLineupMidfield
        txtGelandangAway!!.text = event.strAwayLineupMidfield
    }

}
