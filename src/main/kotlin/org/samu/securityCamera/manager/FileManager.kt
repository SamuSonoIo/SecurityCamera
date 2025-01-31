package org.samu.securityCamera.manager

import java.io.File
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.samu.securityCamera.SecurityCamera
import java.io.FileReader
import java.io.FileWriter

object FileManager {

    private val gson = Gson()
    private val file = File(SecurityCamera.INSTANCE.dataFolder, "cameras.json")

    fun createFile() {
        if (!file.exists()) {
            file.createNewFile()
        }
    }

    fun writeData(camera: Camera) {
        val cameras = readData().toMutableList()
        cameras.add(camera)

        val writer = FileWriter(file, false)
        gson.toJson(cameras, writer)
        writer.flush()
        writer.close()
    }

    fun removeData(cameraName: String) {
        val cameras = readData().toMutableList()
        cameras.removeIf { it.name == cameraName }

        val writer = FileWriter(file, false)
        gson.toJson(cameras, writer)
        writer.flush()
        writer.close()
    }

    fun readData(): List<Camera> {
        if (!file.exists() || file.length() == 0L) return emptyList()
        val reader = FileReader(file)
        val type = object : TypeToken<List<Camera>>() {}.type
        val readData: List<Camera> = gson.fromJson(reader, type) ?: emptyList()
        reader.close()
        return readData
    }
}