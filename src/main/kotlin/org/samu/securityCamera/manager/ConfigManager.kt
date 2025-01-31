package org.samu.securityCamera.manager

import net.kyori.adventure.text.Component
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.dsl.toComponent
import org.samu.securityCamera.dsl.toMini

object ConfigManager {
    fun init() {
        SecurityCamera.INSTANCE.saveDefaultConfig()
        SecurityCamera.INSTANCE.config.options().copyDefaults(true)
    }

    fun readString(path: String): Component {
        val prefix = SecurityCamera.INSTANCE.config.getString("prefix")
        val message = prefix + SecurityCamera.INSTANCE.config.getString(path)
        return message.toMini()
    }
    fun reloadConfig() = SecurityCamera.INSTANCE.reloadConfig()
}