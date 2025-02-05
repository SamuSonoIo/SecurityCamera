package org.samu.securityCamera.commands

import org.bukkit.entity.Player
import org.samu.securityCamera.manager.config.ConfigManager
import org.samu.securityCamera.manager.cache.CameraCache
import org.samu.securityCamera.manager.cache.PlayerCache
import org.samu.securityCamera.manager.config.Messages
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Default
import revxrsal.commands.annotation.Description

class CreateCommand {

    @Command("createcamera")
    @Description("Creates a new camera, Staff only!")
    fun createCameraCommand(sender: Player, name: String, @Default(" ") permission: String) {

        if (!sender.hasPermission("securitycamera.create")) return sender.sendMessage(Messages.NO_PERMISSION.message())

        if (name.isBlank()) return sender.sendMessage(Messages.MISSED_ARGUMENT_CREATECAMERA.message())

        if (PlayerCache.isInCreatingMode(sender.uniqueId) && name.isEmpty()) {
            sender.sendMessage(Messages.EXITED.message())
            PlayerCache.removeCreatingMode(sender.uniqueId)
            return
        }

        if (PlayerCache.isInCreatingMode(sender.uniqueId)) return sender.sendMessage(Messages.ALREADY_CREATING.message())

        if (CameraCache.cameras.keys.any { it.name.equals(name, true) }) return sender.sendMessage(Messages.CAMERA_EXISTS.message())

        PlayerCache.addCreatingMode(sender.uniqueId, name, permission)
        sender.sendMessage(Messages.HOW_TO.message())
    }
}
