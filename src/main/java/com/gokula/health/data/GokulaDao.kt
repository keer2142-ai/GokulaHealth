package com.gokula.health.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GokulaDao {
    @Insert
    suspend fun insertCattle(cattle: CattleEntity): Long

    @Insert
    suspend fun insertMilkEntry(entry: MilkEntryEntity): Long

    @Insert
    suspend fun insertVaccination(vaccination: VaccinationEntity): Long

    @Insert
    suspend fun insertHeatCycle(heatCycle: HeatCycleEntity): Long

    @Query("SELECT * FROM cattle ORDER BY name")
    suspend fun getCattle(): List<CattleEntity>

    @Query("SELECT * FROM cattle WHERE id = :id")
    suspend fun getCattleById(id: Long): CattleEntity?

    @Query("SELECT * FROM milk_entries WHERE cattleId = :cattleId AND entryDate >= :from ORDER BY entryDate")
    suspend fun getMilkEntriesSince(cattleId: Long, from: Long): List<MilkEntryEntity>

    @Query("SELECT * FROM milk_entries WHERE cattleId = :cattleId ORDER BY entryDate DESC")
    suspend fun getAllMilkEntries(cattleId: Long): List<MilkEntryEntity>

    @Query("SELECT * FROM vaccinations WHERE cattleId = :cattleId ORDER BY nextDueDate")
    suspend fun getVaccinations(cattleId: Long): List<VaccinationEntity>

    @Query("SELECT COUNT(*) FROM vaccinations WHERE nextDueDate <= :until")
    suspend fun countPendingVaccinations(until: Long): Int

    @Query("SELECT * FROM heat_cycles WHERE cattleId = :cattleId ORDER BY observedDate DESC")
    suspend fun getHeatCycles(cattleId: Long): List<HeatCycleEntity>

    @Query("SELECT COUNT(*) FROM heat_cycles WHERE projectedNextDate BETWEEN :from AND :until")
    suspend fun countNearHeat(from: Long, until: Long): Int
}
