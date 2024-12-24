package com.example.ucp2pam.ui.Navigation

interface AlamatNavigasi {
    val route: String
}


object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}


object DestinasiDetail : AlamatNavigasi {
    override val route = "detail"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}


object DestinasiUpdate : AlamatNavigasi {
    override val route = "update"
    const val ID = "id"
    val routesWithArg = "$route/{$ID}"
}


object DestinasiInsert : AlamatNavigasi{
    override val route: String = "insert_Dokter"
}