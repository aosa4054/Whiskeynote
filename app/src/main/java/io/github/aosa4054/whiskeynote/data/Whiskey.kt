package io.github.aosa4054.whiskeynote.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "whiskey_table")
data class Whiskey(
        @PrimaryKey
        @ColumnInfo(name = "entryId")
        var name: String,
        var type: String, //スコッチ等
        var kind: String?, //ハイランド等
        var fruity: Int,
        var smokey: Int,
        var salty: Int,
        var malty: Int,
        var floral: Int,
        var woody: Int,
        var memo: String?,
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
        var blob: ByteArray
)