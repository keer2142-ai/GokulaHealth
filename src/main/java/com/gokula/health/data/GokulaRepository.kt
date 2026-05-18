package com.gokula.health.data

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class GokulaRepository(private val dao: GokulaDao) {
    private val zone: ZoneId = ZoneId.systemDefault()

    suspend fun addCattle(earTag: String, name: String, breed: String, dob: Long, gender: String, owner: String, photoUri: String?): Long =
        dao.insertCattle(CattleEntity(earTagId = earTag, name = name, breed = breed, dateOfBirth = dob, gender = gender, ownerName = owner, photoUri = photoUri))

    suspend fun addMilkEntry(cattleId: Long, date: Long, session: String, litres: Double): Long =
        dao.insertMilkEntry(MilkEntryEntity(cattleId = cattleId, entryDate = startOfDay(date), session = session, litres = litres))

    suspend fun addVaccination(cattleId: Long, vaccine: String, administered: Long, due: Long, notes: String): Long =
        dao.insertVaccination(VaccinationEntity(cattleId = cattleId, vaccineName = vaccine, administeredDate = startOfDay(administered), nextDueDate = startOfDay(due), notes = notes))

    suspend fun addHeatCycle(cattleId: Long, observed: Long): HeatCycleEntity {
        val observedDay = startOfDay(observed)
        val next = addDays(observedDay, 21)
        val heatCycle = HeatCycleEntity(cattleId = cattleId, observedDate = observedDay, projectedNextDate = next, reminderDate = addDays(next, -2))
        dao.insertHeatCycle(heatCycle)
        return heatCycle
    }

    suspend fun herdSummary(): HerdSummary {
        val cattle = dao.getCattle()
        val today = startOfDay(System.currentTimeMillis())
        return HerdSummary(
            cattleCount = cattle.size,
            pendingVaccinations = dao.countPendingVaccinations(addDays(today, 7)),
            nearHeat = dao.countNearHeat(today, addDays(today, 3))
        )
    }

    suspend fun cattle(): List<CattleEntity> = dao.getCattle()
    suspend fun cattle(id: Long): CattleEntity? = dao.getCattleById(id)
    suspend fun vaccinations(cattleId: Long): List<VaccinationEntity> = dao.getVaccinations(cattleId)
    suspend fun heatCycles(cattleId: Long): List<HeatCycleEntity> = dao.getHeatCycles(cattleId)
    suspend fun allMilk(cattleId: Long): List<MilkEntryEntity> = dao.getAllMilkEntries(cattleId)

    suspend fun dailyYield(cattleId: Long, days: Long = 30): List<DailyYield> {
        val from = addDays(startOfDay(System.currentTimeMillis()), -days + 1)
        return dao.getMilkEntriesSince(cattleId, from)
            .groupBy { it.entryDate }
            .map { (day, entries) -> DailyYield(day, entries.sumOf { it.litres }) }
            .sortedBy { it.dayStart }
    }

    suspend fun monthlyAverage(cattleId: Long): Double {
        val now = LocalDate.now(zone)
        val monthStart = now.withDayOfMonth(1).atStartOfDay(zone).toInstant().toEpochMilli()
        val entries = dao.getMilkEntriesSince(cattleId, monthStart)
        val dailyTotals = entries.groupBy { it.entryDate }.values.map { day -> day.sumOf { it.litres } }
        return if (dailyTotals.isEmpty()) 0.0 else dailyTotals.average()
    }

    suspend fun lastSevenDayTotals(cattleId: Long): List<Double> =
        dailyYield(cattleId, 7).map { it.totalLitres }

    fun startOfDay(millis: Long): Long =
        Instant.ofEpochMilli(millis).atZone(zone).toLocalDate().atStartOfDay(zone).toInstant().toEpochMilli()

    fun addDays(millis: Long, days: Long): Long =
        Instant.ofEpochMilli(millis).atZone(zone).toLocalDate().plusDays(days).atStartOfDay(zone).toInstant().toEpochMilli()
}
