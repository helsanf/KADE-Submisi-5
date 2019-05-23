package com.example.helsanf.footballmatch.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.helsanf.footballmatch.database.nextmatch.FavoriteModelNext
import com.example.helsanf.footballmatch.database.prevmatch.FavoriteModelPrev
import com.example.helsanf.footballmatch.database.teams.FavoriteTeamsModel
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(context: Context) : ManagedSQLiteOpenHelper(
    context, "FavoriteNext.db",
    null, 1
) {
    companion object {
        private var instances: MyDatabaseOpenHelper? = null

        fun getInstance(context: Context): MyDatabaseOpenHelper {
            if (instances == null) {
                instances =
                    MyDatabaseOpenHelper(context.applicationContext)
            }
            return instances as MyDatabaseOpenHelper
        }
    }


    override fun onCreate(db: SQLiteDatabase?) {
        //buat table disini
        db?.createTable(
            FavoriteModelNext.TABLE_FAVORITE_NEXT, true,
            FavoriteModelNext.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteModelNext.ID_EVENT to TEXT + UNIQUE,
            FavoriteModelNext.HOME_TEAM to TEXT,
            FavoriteModelNext.AWAY_TEAM to TEXT,
            FavoriteModelNext.TANGGAL to TEXT,
            FavoriteModelNext.SCORE_HOME to TEXT,
            FavoriteModelNext.SCORE_AWAY to TEXT,
            FavoriteModelNext.ID_HOME_TEAM to TEXT,
            FavoriteModelNext.ID_AWAY_TEAM to TEXT,
            FavoriteModelNext.ID_LIGA to TEXT
        )

        //table prev
        db?.createTable(
            FavoriteModelPrev.TABLE_FAVORITE_PREV, true,
            FavoriteModelPrev.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteModelPrev.ID_EVENT to TEXT + UNIQUE,
            FavoriteModelPrev.HOME_TEAM to TEXT,
            FavoriteModelPrev.AWAY_TEAM to TEXT,
            FavoriteModelPrev.TANGGAL to TEXT,
            FavoriteModelPrev.SCORE_HOME to TEXT,
            FavoriteModelPrev.SCORE_AWAY to TEXT,
            FavoriteModelPrev.ID_HOME_TEAM to TEXT,
            FavoriteModelPrev.ID_AWAY_TEAM to TEXT,
            FavoriteModelPrev.ID_LIGA to TEXT
        )

        //table teams
        db?.createTable(
            FavoriteTeamsModel.TABLE_FAVORITE_TEAM,true,
            FavoriteTeamsModel.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeamsModel.ID_TEAM to TEXT + UNIQUE,
            FavoriteTeamsModel.ID_LIGA to TEXT,
            FavoriteTeamsModel.NAMA_TEAM to TEXT,
            FavoriteTeamsModel.LOGO_TEAM to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(FavoriteModelNext.TABLE_FAVORITE_NEXT, true)
        db?.dropTable(FavoriteModelPrev.TABLE_FAVORITE_PREV,true)
        db?.dropTable(FavoriteTeamsModel.TABLE_FAVORITE_TEAM,true)

    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)