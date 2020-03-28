package com.sourabh.chsatplace.respository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PresenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPresence(presenceEntityModel: PresenceEntityModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPresenceList(presenceEntityModel: List<PresenceEntityModel>)

    @Query("SELECT * FROM PresenceEntityTable where username =:username LIMIT 1")
    fun getUserPresence(username:String):LiveData<PresenceEntityModel>

}