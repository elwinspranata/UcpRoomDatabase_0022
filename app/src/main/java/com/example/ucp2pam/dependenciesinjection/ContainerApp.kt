package com.example.ucp2pam.dependenciesinjection

import android.content.Context
import com.example.ucp2pam.Repository.LocalRepositoryDokter
import com.example.ucp2pam.Repository.LocalRepositoryJadwalDokter
import com.example.ucp2pam.Repository.RepositoryDokter
import com.example.ucp2pam.Repository.RepositoryJadwalDokter
import com.example.ucp2pam.data.database.AppDataBase

// Interface untuk menyediakan dependensi
interface InterfaceContainerApp {
    val repositoryDokter: RepositoryDokter
    val repositoryJadwalDokter: RepositoryJadwalDokter
}

// Implementasi dependensi
class ContainerApp(private val context: Context) : InterfaceContainerApp {

    // Lazy initialization untuk RepositoryDokter
    override val repositoryDokter: RepositoryDokter by lazy {
        LocalRepositoryDokter(AppDataBase.getInstance(context).dokterDao())
    }

    // Lazy initialization untuk RepositoryJadwalDokter
    override val repositoryJadwalDokter: RepositoryJadwalDokter by lazy {
        LocalRepositoryJadwalDokter(AppDataBase.getInstance(context).jadwalDao())
    }
}
