package com.example.ucp2pam.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Dokter")
data class Dokter(
    @PrimaryKey
    val Id: String,
    val nama: String,
    val Spesialis: String,
    val Klinik: String,
    val Nohp: String,
    val Jamkerja: String
)