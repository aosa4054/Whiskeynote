package io.github.aosa4054.whiskeynote.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Whiskey::class], version = 1)
abstract class WhiskeyDatabase: RoomDatabase(){
    abstract fun whiskeyDao(): WhiskeyDao

    companion object {

        @Volatile private var INSTANCE: WhiskeyDatabase? = null

        fun getInstance(context: Context): WhiskeyDatabase =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it}
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, WhiskeyDatabase::class.java, "Whiskey.db")
                        .build()
    }
}