package org.samu.securityCamera

import gg.flyte.twilight.Twilight
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin
import org.samu.securityCamera.commands.CameraCommand
import org.samu.securityCamera.commands.CreateCommand
import org.samu.securityCamera.commands.RemoveCommand
import org.samu.securityCamera.dsl.toMini
import org.samu.securityCamera.listener.DismountEvent
import org.samu.securityCamera.listener.EntityDamage
import org.samu.securityCamera.listener.InteractListener
import org.samu.securityCamera.listener.PlayerMove
import org.samu.securityCamera.manager.CameraManager
import org.samu.securityCamera.manager.ConfigManager
import org.samu.securityCamera.manager.FileManager
import org.samu.securityCamera.manager.cache.CameraCache.cameras
import revxrsal.commands.bukkit.BukkitLamp

class SecurityCamera : JavaPlugin() {

    override fun onEnable() {

        server.consoleSender.sendMessage("<green>Plugin Enabled Correctly | Thanks for downloading!</green> <red><3</red>".toMini())
        INSTANCE = this

        Twilight(this)
        ConfigManager.init()

        cameraManager = CameraManager()
        cameraManager.loadCameras()

        FileManager.createFile()

        val lamp = BukkitLamp.builder(this).build()
        lamp.register(CreateCommand())
        lamp.register(CameraCommand())
        lamp.register(RemoveCommand())

        InteractListener()
        EntityDamage()
        PlayerMove()
        DismountEvent()
    }

    override fun onDisable() = cameraManager.clearEntities()

    companion object {
        lateinit var INSTANCE : SecurityCamera
        lateinit var cameraManager: CameraManager
    }
}
