package org.samu.securityCamera.commands

import org.bukkit.entity.Player
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.manager.config.ConfigManager
import org.samu.securityCamera.manager.cache.CameraCache
import org.samu.securityCamera.manager.cache.PlayerCache
import org.samu.securityCamera.manager.config.Messages
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description

class CameraCommand {
    /**
     * Command available to Anyone,
     * when executing this there are 5 scenarios:
     *
     * A:
     *  - Player doesn't provide arguments, so we throw an error.
     * B:
     *  - Player provides a camera name, but it's invalid,
     *    so we send him an Error Message.
     * C:
     *  - Player provides a correct camera name, and he has
     *    permission to use that, so we teleport him.
     * D:
     *  - Player provides a correct camera name, But he hasn't
     *    permission to use that, so we give him an error message.
     * E:
     *  - Player is already watching a camera, so what do we do
     *    is simply remove him from the camera and teleport him
     *    back to his old location.
     */
    @Command("camera")
    @Description("Watch a camera or see the list of all available ones")
    fun camera(sender: Player, name: String) {

        /**
         * If the player is watching,
         * let's remove him from the camera.
         * Scenario E.
         */
        if (PlayerCache.isWatching(sender)) SecurityCamera.cameraManager.stopWatching(sender)

        /**
         * If the first argument provided
         * is null or blank, and the player
         * is NOT watching, we send the player
         * an error.
         * Scenario A.
         */
        if (name.isBlank() && !PlayerCache.isWatching(sender)) return sender.sendMessage(Messages.MISSED_ARGUMENT_CAMERA.message())

        val camera = CameraCache.cameras.keys.find { it.name == name }

        /** Scenario B */
        if (camera == null) return sender.sendMessage(Messages.NOT_EXIST.message())

        /** Scenario D */
        if (!sender.hasPermission(camera.permission)) sender.sendMessage(Messages.NO_PERMISSION.message())

        /**
         * After all the checks above,
         * we can safely put him in watching mode!
         * Scenario C.
         */
        SecurityCamera.cameraManager.watchCamera(sender, camera.id)
    }
}