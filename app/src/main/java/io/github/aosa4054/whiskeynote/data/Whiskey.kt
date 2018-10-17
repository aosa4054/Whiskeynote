package io.github.aosa4054.whiskeynote.data

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "whiskey_table")
data class Whiskey(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "entryId")
        var id: Long,
        var name: String?,
        var type: String?, //スコッチ等
        var kind: String?, //ハイランド等
        var price: Int?,
        var ml: Int?,
        var fragrance: Array<String>?,
        var taste: Array<String>?,
        var aftertaste: Array<String>?,
        var memo: String?,
        var image: Bitmap?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Whiskey

        if (!Arrays.equals(fragrance, other.fragrance)) return false
        if (!Arrays.equals(taste, other.taste)) return false
        if (!Arrays.equals(aftertaste, other.aftertaste)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = Arrays.hashCode(fragrance)
        result = 31 * result + Arrays.hashCode(taste)
        result = 31 * result + Arrays.hashCode(aftertaste)
        return result
    }
}