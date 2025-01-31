package org.samu.securityCamera.commands

import org.bukkit.entity.Player
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.manager.ConfigManager
import org.samu.securityCamera.manager.cache.CameraCache
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description

class RemoveCommand {
    @Command("removecamera")
    @Description("Removes the specified camera.")
    fun removeCamera(sender: Player, name: String) {
        if (!sender.hasPermission("securitycamera.remove")) return sender.sendMessage(ConfigManager.readString("messages.no-permission"))

        val camera = CameraCache.cameras.keys.find { it.name == name }
        if (camera == null) return sender.sendMessage(ConfigManager.readString("messages.not-exist"))

        SecurityCamera.cameraManager.deleteCamera(name)
        sender.sendMessage(ConfigManager.readString("messages.deleted"))
    }
}