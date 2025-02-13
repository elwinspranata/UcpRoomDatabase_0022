package com.example.ucp2pam.ui.view.viewmodel.Jadwal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.Repository.RepositoryJadwalDokter
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.ui.Navigation.DestinasiUpdate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwalDokter: RepositoryJadwalDokter,
) : ViewModel() {
    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID])


    val detailUiState: StateFlow<DetailUiState> = repositoryJadwalDokter.getJad(_id)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch { e ->
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true
            ),
        )


    fun deleteJdwl() {
        val currentEvent = detailUiState.value.detailUiEvent
        if (currentEvent != JadwalEvent()) {
            viewModelScope.launch {
                try {
                    repositoryJadwalDokter.deleteJad(currentEvent.toJadwalEntity())
                } catch (e: Exception) {
                    // Handle the error if necessary
                }
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: JadwalEvent = JadwalEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == JadwalEvent()
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != JadwalEvent()
}

fun Jadwal.toDetailUiEvent(): JadwalEvent {
    return JadwalEvent(
        id = idJadwal.toInt(),
        namaDokter = namaDokter,
        namaPasien = namaPasien,
        noHp = noHp,
        tanggalKonsultasi = tanggalKonsultasi,
        status = status
    )
}
