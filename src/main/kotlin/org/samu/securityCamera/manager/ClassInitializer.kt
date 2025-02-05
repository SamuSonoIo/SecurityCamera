package org.samu.securityCamera.manager

import gg.flyte.twilight.Twilight
import org.bukkit.Bukkit
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.SecurityCamera.Companion.cameraManager
import org.samu.securityCamera.commands.CameraCommand
import org.samu.securityCamera.commands.CamerasCommand
import org.samu.securityCamera.commands.CreateCommand
import org.samu.securityCamera.commands.RemoveCommand
import org.samu.securityCamera.dsl.toMini
import org.samu.securityCamera.listener.*
import org.samu.securityCamera.manager.cache.CameraCache
import org.samu.securityCamera.manager.cache.PlayerCache
import org.samu.securityCamera.manager.config.ConfigManager
import revxrsal.commands.bukkit.BukkitLamp

class ClassInitializer {
    init {
        /**
         * Let's load all the needed Classes
         * to avoid fulling the onEnable with
         * useless Stuffs, only the
         * CameraManager and this class
         * is initialized there.
         */
        Twilight(SecurityCamera.INSTANCE)
        ConfigManager.init()
        cameraManager.loadCameras()
        FileManager.createFile()

        val lamp = BukkitLamp.builder(SecurityCamera.INSTANCE)
            .suggestionProviders { providers ->
                providers.addProvider(String::class.java) { _ ->
                    CameraCache.cameras.keys.map { it.name }
                }
            }
            .build()

        lamp.register(CreateCommand())
        lamp.register(CameraCommand())
        lamp.register(RemoveCommand())
        lamp.register(CamerasCommand())

        InteractEvent()
        DamageEvent()
        QuitEvent()
        SneakEvent()
        GuiEvents()
        MoveEvent()

        /**
         * Main Timer, we keep everything
         * here to avoid having multiple Timers
         * around the code
         */

        Bukkit.getScheduler().runTaskTimer(SecurityCamera.INSTANCE, Runnable {
            PlayerCache.watchingCams.keys.forEach { uuid ->
                val player = Bukkit.getPlayer(uuid)
                // Action Bar soon.
            }
        }, 0, 20)
    }
}