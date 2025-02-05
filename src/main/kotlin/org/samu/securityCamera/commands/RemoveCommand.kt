package org.samu.securityCamera.commands

import org.bukkit.entity.Player
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.manager.config.ConfigManager
import org.samu.securityCamera.manager.cache.CameraCache
import org.samu.securityCamera.manager.config.Messages
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description

class RemoveCommand {
    @Command("removecamera")
    @Description("Removes the specified camera.")
    fun removeCamera(sender: Player, name: String) {
        if (!sender.hasPermission("securitycamera.remove")) return sender.sendMessage(Messages.NO_PERMISSION.message())
        if (name.isBlank()) return sender.sendMessage(Messages.MISSED_ARGUMENT_REMOVECAMERA.message())
        val camera = CameraCache.cameras.keys.find { it.name == name }
        if (camera == null) return sender.sendMessage(Messages.NOT_EXIST.message())

        SecurityCamera.cameraManager.deleteCamera(name)
        sender.sendMessage(Messages.DELETED.message())
    }
}