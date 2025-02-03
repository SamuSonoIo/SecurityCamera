package org.samu.securityCamera.manager

import gg.flyte.twilight.Twilight
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.SecurityCamera.Companion.cameraManager
import org.samu.securityCamera.commands.CameraCommand
import org.samu.securityCamera.commands.CreateCommand
import org.samu.securityCamera.commands.RemoveCommand
import org.samu.securityCamera.dsl.toMini
import org.samu.securityCamera.listener.EntityDamage
import org.samu.securityCamera.listener.InteractListener
import org.samu.securityCamera.listener.PlayerMove
import org.samu.securityCamera.listener.SneakEvent
import org.samu.securityCamera.manager.cache.CameraCache
import org.samu.securityCamera.manager.cache.PlayerCache
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

        InteractListener()
        EntityDamage()
        PlayerMove()
        SneakEvent()

        /**
         * Main Timer, we keep everything
         * here to avoid having multiple Timers
         * around the code
         */

        val messageExit:Component = SecurityCamera.INSTANCE.config.getString("messages.shift-to-exit")!!.toMini()

        Bukkit.getScheduler().runTaskTimer(SecurityCamera.INSTANCE, Runnable {
            PlayerCache.watchingCams.keys.forEach { player ->
                Bukkit.getPlayer(player)?.sendActionBar(messageExit)
            }

            CameraCache.cameras.values.forEach { camera ->
                camera.location.chunk.load()
            }
        }, 0, 20)
    }
}