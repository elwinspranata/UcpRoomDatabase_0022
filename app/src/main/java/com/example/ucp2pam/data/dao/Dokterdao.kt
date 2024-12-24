package com.example.ucp2pam.data.dao

import androidx.room.*
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal
import kotlinx.coroutines.flow.Flow

@Dao
interface DokterDao {
    @Insert
    suspend fun insertDokter(dokter: Dokter)

    @Query("SELECT * FROM dokter ORDER BY idDokter ASC")
    fun getAllDokter() : Flow<List<Dokter>>


    @Query("SELECT * FROM dokter WHERE idDokter = :idDokter")
    fun getDokter(idDokter: String) : Flow<Dokter>

}