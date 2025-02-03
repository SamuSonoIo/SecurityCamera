package org.samu.securityCamera

import org.bukkit.plugin.java.JavaPlugin
import org.samu.securityCamera.dsl.toMini
import org.samu.securityCamera.manager.CameraManager
import org.samu.securityCamera.manager.ClassInitializer

class SecurityCamera : JavaPlugin() {

    override fun onEnable() {
        server.consoleSender.sendMessage("<green>Plugin Enabled Correctly | Thanks for downloading!</green> <red><3</red>".toMini())
        INSTANCE = this
        cameraManager = CameraManager()
        ClassInitializer()
    }

    override fun onDisable() = cameraManager.clearEntities()

    companion object {
        lateinit var INSTANCE : SecurityCamera
        lateinit var cameraManager: CameraManager
    }
}
