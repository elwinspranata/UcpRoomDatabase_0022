package com.example.ucp2pam.ui.view.viewmodel.Jadwal


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.KrsApp
import com.example.ucp2pam.ui.view.viewmodel.Dokter.DokterViewModel
import com.example.ucp2pam.ui.view.viewmodel.Dokter.HomeDokterViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DokterViewModel(
                KrsApp().containerApp.repositoryDokter
            )
        }
        initializer {
            HomeDokterViewModel(
                KrsApp().containerApp.repositoryDokter
            )
        }
        initializer {
            JadwalViewModel(
                KrsApp().containerApp.repositoryJadwalDokter
            )
        }
        initializer {
            HomeJadwalViewModel(
                KrsApp().containerApp.repositoryJadwalDokter
            )
        }
        initializer {
            DetailJadwalViewModel(
                createSavedStateHandle(),
                KrsApp().containerApp.repositoryJadwalDokter
            )
        }
        initializer {
            UpdateJadwalViewModel(
                createSavedStateHandle(),
                KrsApp().containerApp.repositoryJadwalDokter
            )
        }
    }
}

fun CreationExtras.KrsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)
