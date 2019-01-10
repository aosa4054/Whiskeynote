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
        var kind: String, //ハイランド等

        //特徴 desc="true -> 1, false -> 0"
        var isDelicate: Int,
        var isLight: Int,
        var isMild: Int,
        var isComplex: Int,
        var isRich: Int,
        var isElegant: Int,
        var isFlesh: Int,

        //アイコン desc="no -> 0, have -> 1, especially -> 2"
        var citrus: Int,
        var berry: Int,
        var fruity: Int,
        var sea: Int,
        var soil: Int,
        var salt: Int,
        var smokey: Int,
        var chemical: Int,
        var vanilla: Int,
        var barrel: Int,
        var honey: Int,
        var chocolate: Int,
        var spices: Int,
        var herbs: Int,

        var memo: String?,
        @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
        var blob: ByteArray?
)