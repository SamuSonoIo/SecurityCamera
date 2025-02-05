package org.samu.securityCamera.listener

import gg.flyte.twilight.event.event
import org.bukkit.NamespacedKey
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.persistence.PersistentDataType
import org.samu.securityCamera.SecurityCamera

class DamageEvent {
    /**
     * Let's check if the Entity that the player hit
     * is an ArmorStand, if so we cancel the event so the Armor Stand
     * doesn't move, and it doesn't destroy.
     */
    val damage = event<EntityDamageByEntityEvent> {
        if (damager !is Player) return@event
        if (entity !is ArmorStand) return@event

        val armorStand = entity as ArmorStand
        val customModelDataKey = NamespacedKey(SecurityCamera.INSTANCE, "security_camera")

        if (armorStand.persistentDataContainer.has(customModelDataKey, PersistentDataType.INTEGER)) isCancelled = true
    }
}
