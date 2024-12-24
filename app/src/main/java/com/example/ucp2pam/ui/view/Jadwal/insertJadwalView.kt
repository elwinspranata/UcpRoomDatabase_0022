package com.example.ucp2pam.ui.view.Jadwal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.ui.Navigation.AlamatNavigasi
import com.example.ucp2pam.ui.costumwidget.TopAppBar
import com.example.ucp2pam.ui.view.viewmodel.Jadwal.JadwalEvent
import com.example.ucp2pam.ui.view.viewmodel.Jadwal.JadwalUIState
import com.example.ucp2pam.ui.view.viewmodel.Jadwal.JadwalViewModel
import com.example.ucp2pam.ui.view.viewmodel.Jadwal.FormErrorState
import com.example.ucp2pam.ui.view.viewmodel.Jadwal.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertJadwal : AlamatNavigasi {
    override val route: String = "insert_jadwal"
}

@Composable
fun InsertJadwalView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JadwalViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uistate // Ambil UI state dari ViewModel
    val snackBarHostState = remember { SnackbarHostState() } // snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackBarHostState.showSnackbar(message) // Tampilkan Snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Jadwal Konsultasi"
            )
            // Isi body
            InsertBodyJadwal(
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent) // Update state di ViewModel
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData() // Simpan Data
                    }
                    onNavigate()
                },
                uiState = uiState
            )
        }
    }
}

@Composable
fun InsertBodyJadwal(
    modifier: Modifier = Modifier,
    onValueChange: (JadwalEvent) -> Unit,
    uiState: JadwalUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormJadwal(
            jadwalEvent = uiState.jadwalEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun FormJadwal(
    jadwalEvent: JadwalEvent = JadwalEvent(),
    onValueChange: (JadwalEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val statusList = listOf("Pending", "Selesai", "Dibatalkan")

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaDokter,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaDokter = it)) // Update namaDokter
            },
            label = { Text("Nama Dokter") },
            isError = errorState.namaDokter != null,
            placeholder = { Text("Masukkan nama dokter") },
        )
        Text(
            text = errorState.namaDokter ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.namaPasien,
            onValueChange = {
                onValueChange(jadwalEvent.copy(namaPasien = it)) // Update namaPasien
            },
            label = { Text("Nama Pasien") },
            isError = errorState.namaPasien != null,
            placeholder = { Text("Masukkan nama pasien") },
        )
        Text(
            text = errorState.namaPasien ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.noHp,
            onValueChange = {
                onValueChange(jadwalEvent.copy(noHp = it)) // Update noHp
            },
            label = { Text("Nomor Telepon") },
            isError = errorState.noHp != null,
            placeholder = { Text("Masukkan nomor telepon pasien") },
        )
        Text(
            text = errorState.noHp ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = jadwalEvent.tanggalKonsultasi,
            onValueChange = {
                onValueChange(jadwalEvent.copy(tanggalKonsultasi = it)) // Update tanggalKonsultasi
            },
            label = { Text("Tanggal Konsultasi") },
            isError = errorState.tanggalKonsultasi != null,
            placeholder = { Text("Masukkan tanggal konsultasi") },
        )
        Text(
            text = errorState.tanggalKonsultasi ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Status")

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            statusList.forEach { status ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = jadwalEvent.status == status,
                        onClick = {
                            onValueChange(jadwalEvent.copy(status = status)) // Update status
                        }
                    )
                    Text(
                        text = status
                    )
                }
            }
        }
        Text(
            text = errorState.status ?: "",
            color = Color.Red
        )
    }
}
