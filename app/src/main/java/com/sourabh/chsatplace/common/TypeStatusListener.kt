package com.sourabh.chsatplace.common

import com.sourabh.chsatplace.utilities.Logger
import org.jivesoftware.smack.StanzaListener
import org.jivesoftware.smack.packet.Stanza

class TypeStatusListener : StanzaListener {
    override fun processStanza(packet: Stanza?) {
       val element= packet!!.extensions[0]
        if (element != null) {
            when (element.elementName) {
                "composing" -> Logger.verbose("typing...")
                "paused" ->  Logger.verbose("online")
                "active" ->  Logger.verbose("online")
                "inactive" ->  Logger.verbose("online")
                "gone" ->  Logger.verbose("online")
            }

        }

    }

}
