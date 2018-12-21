package io.github.aosa4054.whiskeynote.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Whiskey::class], version = 1)
abstract class WhiskeyDatabase: RoomDatabase(){
    abstract fun whiskeyDao(): WhiskeyDao
}