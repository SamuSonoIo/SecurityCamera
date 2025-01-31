package org.samu.securityCamera.commands

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.dsl.toMini
import org.samu.securityCamera.manager.ConfigManager
import org.samu.securityCamera.manager.cache.CameraCache
import org.samu.securityCamera.manager.cache.PlayerCache
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Description
import revxrsal.commands.annotation.Optional

class CameraCommand {
    /**
     * Command available to Anyone,
     * when executing this there are 5 scenarios:
     *
     * A:
     *  - Player doesn't provide arguments, so we send him a list
     *    of all available cameras.
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
    fun camera(sender: Player, @Optional name: String? = null) {

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
         * a list of available cameras.
         * Scenario A.
         */
        if (name.isNullOrBlank() && !PlayerCache.isWatching(sender)) {
            sender.sendMessage(ConfigManager.readString("messages.available-cams"))
            CameraCache.cameras.keys.forEach { camera ->
                if (sender.hasPermission(camera.permission)) sender.sendMessage("<white>- ${camera.name} </white>".toMini())
            }
            return
        }

        val camera = CameraCache.cameras.keys.find { it.name == name }

        /** Scenario B */
        if (camera == null) return sender.sendMessage(ConfigManager.readString("messages.not-exist"))

        /** Scenario D */
        if (!sender.hasPermission(camera.permission)) sender.sendMessage(ConfigManager.readString("messages.no-permission"))

        /**
         * Location of the camera, just
         * Creating a new one, since we
         * directly don't save the Location
         * but only:
         * - X
         * - Y
         * - Z
         * - WORLD
         */
        val location = Location(
            Bukkit.getWorld(camera.world)!!,
            camera.x,
            camera.y,
            camera.z,
            0f,
            0f
        )

        /**
         * After all the checks above,
         * we can safely put him in watching mode!
         * Scenario C.
         */
        SecurityCamera.cameraManager.watchCamera(sender, camera.id)
    }
}