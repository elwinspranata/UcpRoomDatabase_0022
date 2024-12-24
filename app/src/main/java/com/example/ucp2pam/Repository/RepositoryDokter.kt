package com.example.ucp2pam.Repository

import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow

interface RepositoryDokter {
    suspend fun insertDok(dokter: Dokter)

    fun getAllDok(): Flow<List<Dokter>>

    fun getDok(idDokter: String): Flow<Dokter>
}