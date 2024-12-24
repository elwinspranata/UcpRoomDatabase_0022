package com.example.ucp2pam.ui.view.dokter

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
import com.example.ucp2pam.ui.view.viewmodel.Dokter.DokterEvent
import com.example.ucp2pam.ui.view.viewmodel.Dokter.DokterUIState
import com.example.ucp2pam.ui.view.viewmodel.Dokter.DokterViewModel
import com.example.ucp2pam.ui.view.viewmodel.Dokter.FormErrorState
import com.example.ucp2pam.ui.view.viewmodel.Jadwal.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertDokter : AlamatNavigasi {
    override val route: String = "insert_dokter"
}

@Composable
fun InsertDokterView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DokterViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                judul = "Tambah Dokter"
            )
            // Isi body
            InsertBodyDokter(
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
fun InsertBodyDokter(
    modifier: Modifier = Modifier,
    onValueChange: (DokterEvent) -> Unit,
    uiState: DokterUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormDokter(
            dokterEvent = uiState.dokterEvent,
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
fun FormDokter(
    dokterEvent: DokterEvent = DokterEvent(),
    onValueChange: (DokterEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val DokterViewModel = listOf("Laki-laki", "Perempuan")
    val spesialisasi = listOf("Umum", "Bedah", "Kandungan", "Anak", "Gigi")

    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.idDokter,
            onValueChange = {
                onValueChange(dokterEvent.copy(idDokter = it)) // Update idDokter
            },
            label = { Text("ID Dokter") },
            isError = errorState.idDokter != null,
            placeholder = { Text("Masukkan ID Dokter") },
        )
        Text(
            text = errorState.idDokter ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.nama,
            onValueChange = {
                onValueChange(dokterEvent.copy(nama = it)) // Update nama
            },
            label = { Text("Nama Dokter") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama dokter") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.noHp,
            onValueChange = {
                onValueChange(dokterEvent.copy(noHp = it)) // Update noHp
            },
            label = { Text("Nomor Telepon") },
            isError = errorState.noHp != null,
            placeholder = { Text("Masukkan nomor telepon dokter") },
        )
        Text(
            text = errorState.noHp ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.klinik,
            onValueChange = {
                onValueChange(dokterEvent.copy(klinik = it)) // Update klinik
            },
            label = { Text("Klinik") },
            isError = errorState.klinik != null,
            placeholder = { Text("Masukkan nama klinik") },
        )
        Text(
            text = errorState.klinik ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = errorState.spesialis ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dokterEvent.jamKerja,
            onValueChange = {
                onValueChange(dokterEvent.copy(jamKerja = it)) // Update jamKerja
            },
            label = { Text("jam Kerja") },
            isError = errorState.jamKerja != null,
            placeholder = { Text("Masukkan jam kerja dokter") },
        )
        Text(
            text = errorState.jamKerja ?: "",
            color = Color.Red
        )
    }
}

