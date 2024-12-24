package com.example.ucp2pam.Repository

import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

interface RepositoryJadwalDokter {
    suspend fun insertJad(jadwal: Jadwal)
    fun getAllJad(): Flow<List<Jadwal>>
    fun getJad(idJadwal: Int): Flow<Jadwal>
    suspend fun deleteJad(jadwal: Jadwal)
    suspend fun updateJad(jadwal: Jadwal)
}
