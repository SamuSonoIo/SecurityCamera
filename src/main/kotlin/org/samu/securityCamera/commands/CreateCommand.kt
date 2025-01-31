package org.samu.securityCamera.commands

import org.bukkit.entity.Player
import org.samu.securityCamera.manager.ConfigManager
import org.samu.securityCamera.manager.cache.CameraCache
import org.samu.securityCamera.manager.cache.PlayerCache
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional

class CreateCommand {

    @Command("createcamera")
    @Description("Creates a new camera, Staff only!")
    fun createCameraCommand(sender: Player,@Optional name: String, @Optional permission: String) {

        if (!sender.hasPermission("securitycamera.create")) return sender.sendMessage(ConfigManager.readString("messages.no-permission"))

        if (PlayerCache.isInCreatingMode(sender.uniqueId) && name.isEmpty()) {
            sender.sendMessage(ConfigManager.readString("messages.exited"))
            PlayerCache.removeCreatingMode(sender.uniqueId)
            return
        }

        if (PlayerCache.isInCreatingMode(sender.uniqueId)) return sender.sendMessage(ConfigManager.readString("messages.already-creating"))

        if (CameraCache.cameras.keys.any { it.name.equals(name, true) }) return sender.sendMessage(ConfigManager.readString("messages.camera-exists"))

        PlayerCache.addCreatingMode(sender.uniqueId, name, permission)
        sender.sendMessage(ConfigManager.readString("messages.how-to"))
    }
}
