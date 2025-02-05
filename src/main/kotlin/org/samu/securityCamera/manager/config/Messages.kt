package org.samu.securityCamera.manager.config

import net.kyori.adventure.text.Component

enum class Messages(private val path: String) {
    NO_PERMISSION("messages.no-permission"),
    NOT_EXIST("messages.not-exist"),
    CAMERA_EXISTS("messages.camera-exists"),
    HOW_TO("messages.how-to"),
    ALREADY_CREATING("messages.already-creating"),
    EXITED("emessages.exited"),
    CREATED("messages.created"),
    DELETED("messages.deleted"),
    MISSED_ARGUMENT_CAMERA("messages.missed-argument-camera"),
    MISSED_ARGUMENT_CREATECAMERA("messages.missed-argument-createcamera"),
    MISSED_ARGUMENT_REMOVECAMERA("messages.missed-argument-removecamera");

    fun message(): Component = ConfigManager.readComponent(path)
}