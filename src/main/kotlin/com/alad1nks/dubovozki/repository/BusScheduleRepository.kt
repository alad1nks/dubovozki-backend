package com.alad1nks.dubovozki.repository

import com.alad1nks.dubovozki.model.Bus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BusScheduleRepository : JpaRepository<Bus, Long> {
    fun findByDirection(direction: String): List<Bus>

    @Modifying
    @Transactional
    @Query("DELETE FROM Bus")
    fun deleteAllBuses()
}
