package org.samu.securityCamera.dsl

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder

val miniMessage = MiniMessage.builder().build()

fun String.toComponent(): TextComponent = Component.text(this)

fun String.toMini(): Component = miniMessage.deserialize(this)

fun String.toMini(vararg placeholders: Component): Component {
    val components = placeholders.mapIndexed { i, it -> Placeholder.component(i.toString(), it) }.toTypedArray()
    return miniMessage.deserialize(this, *components)
}