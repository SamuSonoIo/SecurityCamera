package org.samu.securityCamera.commands

import org.bukkit.entity.Player
import org.samu.securityCamera.gui.GUI
import org.samu.securityCamera.manager.config.ConfigManager
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description

class CamerasCommand {
    @Command("cameras")
    @Description("Open a GUI with all available cameras you can access.")
    fun openCameraGUI(sender: Player) {
        GUI(sender, 1, ConfigManager.readInt("gui-length"))
    }
}