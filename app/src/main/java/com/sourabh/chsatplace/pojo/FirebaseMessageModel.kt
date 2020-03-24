package com.sourabh.chsatplace.pojo

data class FirebaseMessageModel(
    val from_JID: String,
    val message: String,
    val stanzaId: String
)