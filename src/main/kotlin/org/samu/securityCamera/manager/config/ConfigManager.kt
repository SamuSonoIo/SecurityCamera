package org.samu.securityCamera.manager.config

import gg.flyte.twilight.extension.asString
import net.kyori.adventure.text.Component
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.dsl.toMini

object ConfigManager {
    fun init() {
        SecurityCamera.INSTANCE.saveDefaultConfig()
        SecurityCamera.INSTANCE.config.options().copyDefaults(true)
    }

    val prefix = SecurityCamera.INSTANCE.config.getString("prefix")

    fun readComponent(path: String): Component {
        val prefix = SecurityCamera.INSTANCE.config.getString("prefix")
        val message = if (SecurityCamera.INSTANCE.config.getString(path)!!.contains("%NOPREFIX%")) {
            return SecurityCamera.INSTANCE.config.getString(path)!!.toMini()
        } else {
            prefix + SecurityCamera.INSTANCE.config.getString(path)!!.toMini()
        }
        return Component.text("Message not found: $message")
    }

    fun readString(path: String): String {
        val prefix = SecurityCamera.INSTANCE.config.getString("prefix")
        val message = if (SecurityCamera.INSTANCE.config.getString(path)!!.contains("%NOPREFIX%")) {
            return SecurityCamera.INSTANCE.config.getString(path)!!.toMini().asString()
        } else {
            prefix + SecurityCamera.INSTANCE.config.getString(path)!!.toMini().asString()
        }

        return ""
    }

    fun readInt(path: String): Int = SecurityCamera.INSTANCE.config.getInt(path)
}