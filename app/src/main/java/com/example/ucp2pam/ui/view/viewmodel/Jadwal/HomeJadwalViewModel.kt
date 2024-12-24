package com.example.ucp2pam.ui.view.viewmodel.Jadwal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.Repository.RepositoryJadwalDokter
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

data class HomeUiState(
    val listJadwal: List<Jadwal> = emptyList()
)

class HomeJadwalViewModel(
    private val repositoryJadwalDokter: RepositoryJadwalDokter
) : ViewModel(){

    val homeUiState: StateFlow<HomeUiState2> = repositoryJadwalDokter.getAllJad()
        .filterNotNull()
        .map {
            HomeUiState2(
                listJadwal = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUiState2(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUiState2(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState2
                (isLoading = true)
        )
}

data class HomeUiState2(
    val listJadwal: List<Jadwal> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)