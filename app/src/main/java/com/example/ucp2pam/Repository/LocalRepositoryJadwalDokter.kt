package com.example.ucp2pam.Repository

import com.example.ucp2pam.data.dao.Jadwaldao
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

class LocalRepositoryJadwalDokter(
    private val jadwalDao: Jadwaldao // Menggunakan interface yang sesuai
) : RepositoryJadwalDokter { // Implementasi dari interface RepositoryJadwalDokter

    override suspend fun insertJad(jadwal: Jadwal) {
        jadwalDao.insertJadwal(jadwal) // Perbaiki jika ada typo di sini
    }


    override fun getAllJad(): Flow<List<Jadwal>> {
        return jadwalDao.getAllJadwal()
    }


    override fun getJad(idJadwal: Int): Flow<Jadwal> {
        return jadwalDao.getJadwalById(idJadwal)
    }


    override suspend fun deleteJad(jadwal: Jadwal) {
        jadwalDao.deleteJadwal(jadwal)
    }


    override suspend fun updateJad(jadwal: Jadwal) {
        jadwalDao.updateJadwal(jadwal)
    }
}
