package com.example.ucp2pam.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2pam.data.entity.Dokter

@Dao
interface Dokterdao {
    @Insert
    suspend fun insertDokterdao(Dokter: Dokter)

    @Update
    suspend fun updateDokterdao(dokter: Dokter)

    @Delete
    suspend fun deleteDokterdao(dokter: Dokter)

    @Query("SELECT * FROM Dokter")
    suspend fun getAllDokter(): List<Dokter>

    @Query("SELECT * FROM Dokter WHERE id = :id")
    suspend fun getDokterById(id: Int): Dokter?

    @Query("DELETE FROM Dokter")
    suspend fun deleteAllDokter()

}
