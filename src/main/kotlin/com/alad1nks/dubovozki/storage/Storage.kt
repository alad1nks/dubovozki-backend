package com.alad1nks.dubovozki.storage

import java.util.prefs.Preferences

class Storage(
    private val preferences: Preferences
) {
    fun getBusScheduleRevision() = preferences.getInt("bus_schedule_revision", 0)

    fun updateBusScheduleRevision() {
        preferences.putInt("bus_schedule_revision", preferences.getInt("bus_schedule_revision", 0) + 1)
    }
}
