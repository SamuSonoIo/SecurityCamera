package org.samu.securityCamera.gui

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.samu.securityCamera.SecurityCamera
import org.samu.securityCamera.dsl.toMini
import org.samu.securityCamera.manager.cache.CameraCache

class GUI(player: Player, page: Int, guiLength: Int) {
    private val gui = Bukkit.createInventory(null, guiLength, SecurityCamera.INSTANCE.config.getString("gui-title")!!.toMini())

    init {
        val accessibleCameras = CameraCache.cameras.keys.filter { player.hasPermission(it.permission) }

        val items: MutableList<ItemStack> = mutableListOf()

        accessibleCameras.forEach { camera ->
            items.add(PageUtil.createItem(Material.PLAYER_HEAD, camera.name.toMini(), listOf(), "camera-${camera.id}", camera.id))
        }

        val left = if (PageUtil.isPageValid(items, page - 1, guiLength)) {
            PageUtil.createItem(Material.LIME_STAINED_GLASS_PANE, "<green>Page Left</green>".toMini(), listOf(), "left-page",  page - 1)
        } else {
            PageUtil.createItem(Material.RED_STAINED_GLASS_PANE, "<green>Page Left</green>".toMini(), listOf())
        }

        val right = if (PageUtil.isPageValid(items, page + 1, guiLength)) {
            PageUtil.createItem(Material.LIME_STAINED_GLASS_PANE, "<green>Page Right</green>".toMini(), listOf(), "right-page", page + 1)
        } else {
            PageUtil.createItem(Material.RED_STAINED_GLASS_PANE, "<green>Page Right</green>".toMini(), listOf())
        }

        gui.setItem(0, left)
        gui.setItem(8, right)

        for (itemstacks in PageUtil.pageItems(items, page, guiLength)) {
            val emptySlot = gui.firstEmpty()
            if (emptySlot != -1) {
                gui.setItem(emptySlot, itemstacks)
            }

        }

        player.openInventory(gui)
    }
}