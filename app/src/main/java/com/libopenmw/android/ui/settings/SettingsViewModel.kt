package com.libopenmw.android.ui.settings

import androidx.lifecycle.ViewModel
import java.io.File

class SettingsViewModel : ViewModel() {

    // Function to update the viewing distance in settings.cfg file
    fun updateViewingDistance(newDistance: Double) {
        val configFile = File("/path/to/settings.cfg") // Specify the path to your settings.cfg file
        val lines = configFile.readLines()
        val updatedLines = lines.map { line ->
            if (line.startsWith("viewing distance")) {
                "viewing distance = $newDistance"
            } else {
                line
            }
        }
        configFile.writeText(updatedLines.joinToString("\n"))
    }
}