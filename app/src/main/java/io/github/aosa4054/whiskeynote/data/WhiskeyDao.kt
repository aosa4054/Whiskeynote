package io.github.aosa4054.whiskeynote.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WhiskeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(whiskey: Whiskey)

    @Query("SELECT * from whiskey_table")
    fun getAllWhiskeys(): List<Whiskey>

    @Query("SELECT * from whiskey_table WHERE entryId = :whiskeyId")
    fun getWhiskeyById(whiskeyId: String): Whiskey

    @Query("DELETE FROM whiskey_table WHERE entryId = :whiskeyId")
    fun deleteWhiskeyById(whiskeyId: String)

    @Query("DELETE FROM whiskey_table")
    fun deleteAll()
}
