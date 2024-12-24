package com.example.ucp2pam.Repository

import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.entity.Dokter
import kotlinx.coroutines.flow.Flow


class LocalRepositoryDokter(
    private val dokterDao: DokterDao // Menggunakan PascalCase sesuai konvensi Kotlin
) : RepositoryDokter {

    override suspend fun insertDok(dokter: Dokter) {
        dokterDao.insertDokter(dokter) // Perbaiki jika ada typo di sini
    }

    override fun getAllDok(): Flow<List<Dokter>> {
        return dokterDao.getAllDokter()
    }

    override fun getDok(idDokter: String): Flow<Dokter> {
        return dokterDao.getDokter(idDokter = idDokter)
    }
}


