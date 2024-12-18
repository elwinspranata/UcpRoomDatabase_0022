package com.example.ucp2pam.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Jadwal")
data class Jadwal(
    @PrimaryKey
    val Id: String,
    val Namadokter: String,
    val Namapasien: String,
    val Nohp: String,
    val Tanggalkonsultasi: String,
    val Status: String
)