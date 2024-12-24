package com.example.ucp2pam.ui.view.viewmodel.Jadwal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.Repository.RepositoryJadwalDokter
import com.example.ucp2pam.data.entity.Jadwal
import com.example.ucp2pam.ui.Navigation.AlamatNavigasi
import com.example.ucp2pam.ui.Navigation.DestinasiInsertDokter.id
import com.example.ucp2pam.ui.Navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateJadwalViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryJadwalDokter: RepositoryJadwalDokter
) : ViewModel() {

    var updateUIState by mutableStateOf(JadwalUIState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdate.ID])


    init {
        viewModelScope.launch {
            try {
                updateUIState = repositoryJadwalDokter.getJad(_id)
                    .filterNotNull()
                    .first()
                    .toUiStateJdwl()
            } catch (e: Exception) {
                // Handle the error if necessary
                updateUIState = updateUIState.copy(
                    snackBarMessage = "Failed to fetch Jadwal details"
                )
            }
        }
    }

    fun updateState(jadwalEvent: JadwalEvent) {
        updateUIState = updateUIState.copy(jadwalEvent = jadwalEvent)
    }

    fun validateFields(): Boolean {
        val event = updateUIState.jadwalEvent
        val errorState = FormErrorState(
            namaDokter = if (event.namaDokter.isNotEmpty()) null else "Nama Dokter tidak boleh kosong",
            namaPasien = if (event.namaPasien.isNotEmpty()) null else "Nama Pasien tidak boleh kosong",
            noHp = if (event.noHp.isNotEmpty()) null else "NoHp tidak boleh kosong",
            tanggalKonsultasi = if (event.tanggalKonsultasi.isNotEmpty()) null else "Tanggal Konsultasi tidak boleh kosong",
            status = if (event.status.isNotEmpty()) null else "Status tidak boleh kosong"
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.jadwalEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryJadwalDokter.updateJad(currentEvent.toJadwalEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        jadwalEvent = JadwalEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate: ${e.message}"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }

    fun Jadwal.toUiStateJdwl(): JadwalUIState = JadwalUIState(
        jadwalEvent = this.toDetailUiEvent(),
    )
}
