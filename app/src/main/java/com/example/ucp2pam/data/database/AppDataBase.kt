package com.example.ucp2pam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2pam.data.dao.DokterDao
import com.example.ucp2pam.data.dao.Jadwaldao
import com.example.ucp2pam.data.entity.Dokter
import com.example.ucp2pam.data.entity.Jadwal

@Database(entities = [Dokter::class, Jadwal::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {


    abstract fun dokterDao(): DokterDao
    abstract fun jadwalDao(): Jadwaldao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null


        fun getInstance(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "KrsApp"
                ).fallbackToDestructiveMigration() // Untuk reset database jika ada perubahan skema
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
