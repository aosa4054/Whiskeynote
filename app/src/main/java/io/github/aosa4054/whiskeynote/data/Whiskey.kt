package io.github.aosa4054.whiskeynote.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "whiskey_table")
data class Whiskey(
        var name: String?,
        var type: String?, //スコッチ等
        var kind: String?, //ハイランド等
        var price: Int?,
        var ml: Int?,
        var fragrance: String?,
        var taste: String?,
        var aftertaste: String?,
        var memo: String?,
        var image: String?
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "entryId")
    var id = 0
}