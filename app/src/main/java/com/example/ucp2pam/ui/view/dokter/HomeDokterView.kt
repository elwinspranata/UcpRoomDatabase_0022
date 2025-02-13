package com.example.ucp2pam.ui.view.dokter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.UCP2PAM.R
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.ui.view.viewmodel.Dokter.HomeDokterViewModel
import com.example.ucp2pam.ui.view.viewmodel.Jadwal.PenyediaViewModel

@Composable
fun HomeDokterView(
    viewModel: HomeDokterViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddDokter: () -> Unit,
    onJadwalView: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        HeaderSection()
        BodySection(
            viewModel = viewModel,
            onAddDokter = onAddDokter,
            onJadwalView = onJadwalView
        )
    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF0E91F3))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.padding(start = 15.dp)) {
                    Text(
                        text = "Welcome, ",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Klinik Elwin",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.klinikprawatan),
                    contentDescription = "klinikprawatan",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .padding(end = 15.dp)
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
        }
    }
}

@Composable
fun BodySection(
    viewModel: HomeDokterViewModel,
    onAddDokter: () -> Unit,
    onJadwalView: (String) -> Unit
) {
    val homeUiState by viewModel.homeUiState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.keperawatan),
                    contentDescription = "keperawatan",
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(6.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Chat Dokter di Klinik Elwin",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        text = "siap kasih yang terbaik untuk kamu hidup lebih sehat",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(text = "Cari dokter", fontSize = 14.sp, color = Color.Gray)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onAddDokter,
                    modifier = Modifier.weight(0.5f).padding(end = 8.dp)
                ) {
                    Text("Tambah Dokter")
                }
                Button(
                    onClick = { onJadwalView("homejadwal") },
                    modifier = Modifier.weight(0.5f).padding(start = 8.dp)
                ) {
                    Text("Lihat Jadwal")
                }
            }
        }
    }

    if (homeUiState.listDokter.isEmpty()) {
        EmptyState()
    } else {
        ListDokter(
            listDokter = homeUiState.listDokter,
            onClick = onJadwalView
        )
    }
}

@Composable
fun ListDokter(
    listDokter: List<Dokter>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(listDokter) { dokter ->
            DokterCard(dokter = dokter, onClick = onClick)
        }
    }
}

@Composable
fun DokterCard(
    dokter: Dokter,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logoklinik),
                contentDescription = "atena",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = dokter.nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = dokter.spesialis,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = dokter.klinik,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tidak ada data dokter.",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}
