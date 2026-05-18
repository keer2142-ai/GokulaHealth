package com.gokula.health.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cattle")
data class CattleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val earTagId: String,
    val name: String,
    val breed: String,
    val dateOfBirth: Long,
    val gender: String,
    val ownerName: String,
    val photoUri: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "milk_entries")
data class MilkEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cattleId: Long,
    val entryDate: Long,
    val session: String,
    val litres: Double,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "vaccinations")
data class VaccinationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cattleId: Long,
    val vaccineName: String,
    val administeredDate: Long,
    val nextDueDate: Long,
    val notes: String
)

@Entity(tableName = "heat_cycles")
data class HeatCycleEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cattleId: Long,
    val observedDate: Long,
    val projectedNextDate: Long,
    val reminderDate: Long
)

data class DailyYield(
    val dayStart: Long,
    val totalLitres: Double
)

data class HerdSummary(
    val cattleCount: Int,
    val pendingVaccinations: Int,
    val nearHeat: Int
)
