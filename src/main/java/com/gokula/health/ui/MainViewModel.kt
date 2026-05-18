package com.gokula.health.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.gokula.health.data.CattleEntity
import com.gokula.health.data.DailyYield
import com.gokula.health.data.GokulaRepository
import com.gokula.health.data.HerdSummary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val summary: HerdSummary = HerdSummary(0, 0, 0),
    val cattle: List<CattleEntity> = emptyList(),
    val selected: CattleEntity? = null,
    val chart: List<DailyYield> = emptyList(),
    val monthlyAverage: Double = 0.0
)

class MainViewModel(private val repository: GokulaRepository) : ViewModel() {
    private val _state = MutableStateFlow(MainUiState())
    val state: StateFlow<MainUiState> = _state

    fun load(selectedId: Long? = _state.value.selected?.id) {
        viewModelScope.launch {
            val cattle = repository.cattle()
            val selected = selectedId?.let { id -> cattle.firstOrNull { it.id == id } } ?: cattle.firstOrNull()
            _state.value = MainUiState(
                summary = repository.herdSummary(),
                cattle = cattle,
                selected = selected,
                chart = selected?.let { repository.dailyYield(it.id) } ?: emptyList(),
                monthlyAverage = selected?.let { repository.monthlyAverage(it.id) } ?: 0.0
            )
        }
    }

    fun select(cattleId: Long) = load(cattleId)

    fun addCattle(earTag: String, name: String, breed: String, dob: Long, gender: String, owner: String, photoUri: String?) {
        viewModelScope.launch {
            val id = repository.addCattle(earTag, name, breed, dob, gender, owner, photoUri)
            load(id)
        }
    }

    fun addMilk(cattleId: Long, date: Long, session: String, litres: Double) {
        viewModelScope.launch {
            repository.addMilkEntry(cattleId, date, session, litres)
            load(cattleId)
        }
    }

    fun addVaccination(cattleId: Long, vaccine: String, administered: Long, due: Long, notes: String, onSaved: (Long) -> Unit) {
        viewModelScope.launch {
            repository.addVaccination(cattleId, vaccine, administered, due, notes)
            onSaved(due)
            load(cattleId)
        }
    }

    fun addHeat(cattleId: Long, observed: Long, onSaved: (Long) -> Unit) {
        viewModelScope.launch {
            val heatCycle = repository.addHeatCycle(cattleId, observed)
            onSaved(heatCycle.reminderDate)
            load(cattleId)
        }
    }

    class Factory(private val repository: GokulaRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T
    }
}
