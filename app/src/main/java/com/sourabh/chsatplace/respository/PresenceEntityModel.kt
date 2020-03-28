package com.sourabh.chsatplace.respository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PresenceEntityTable")
data class PresenceEntityModel (
    @PrimaryKey @ColumnInfo(name = "username")val username:String,
    @ColumnInfo(name = "isOnline") val isOnline:Boolean,
    @ColumnInfo(name = "lastOnline") val lastOnline:String
)