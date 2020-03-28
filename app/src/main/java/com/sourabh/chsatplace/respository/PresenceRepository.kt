package com.sourabh.chsatplace.respository


class PresenceRepository constructor(val presenceDao: PresenceDao) {
   
    fun getUserPresence(username: String)=presenceDao.getUserPresence(username)
    fun setUserPresence(presenceEntityModel: PresenceEntityModel)=presenceDao.insertPresence(presenceEntityModel)
}