package com.example.ucp2pam.ui.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2pam.ui.view.Jadwal.DestinasiInsertJadwal
import com.example.ucp2pam.ui.view.Jadwal.DetailJadwalView
import com.example.ucp2pam.ui.view.Jadwal.UpdateJadwalView
import com.example.ucp2pam.ui.view.dokter.InsertDokterView
import com.example.ucp2pam.ui.view.dokter.HomeDokterView


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeDokterView.route // Starting screen is HomeView
    ) {
        // Home Screen
        composable(route = DestinasiHomeDokterView.route) {
            HomeDokterView(
                onAddDokter = {
                    navController.navigate(DestinasiInsertDokter.route)
                },
                onJadwalView = {
                    navController.navigate(DestinasiInsertJadwal.route) // Corrected navigation to Jadwal view
                },
                modifier = modifier
            )
        }

        // Insert Dokter Screen
        composable(route = DestinasiInsertDokter.route) {
            InsertDokterView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Detail Dokter Screen
        composable(
            route = "${DestinasiInsertJadwal.route}",
            arguments = listOf(
                navArgument(DestinasiInsertDokter.id) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(DestinasiInsertDokter.id)
            id?.let { dokterId ->
                DetailJadwalView(
                    onBack = { navController.popBackStack() },
                    onEditClick = {
                        navController.navigate("${DestinasiInsertDokter.route}/edit/$dokterId")
                    },
                    onDeleteClick = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Update Dokter Screen
        composable(
            route = "${DestinasiInsertDokter.route}/edit/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            id?.let {
                HomeDokterView(
                    onJadwalView = { navController.popBackStack() },
                    onAddDokter = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Insert Jadwal Screen
        composable(route = DestinasiInsertJadwal.route) { backStackEntry ->
            val idDokter = backStackEntry.arguments?.getString("idDokter") // Retrieve idDokter
            InsertDokterView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Update Jadwal Screen
        composable(
            route = "${DestinasiInsertJadwal.route}/edit/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            id?.let {
                UpdateJadwalView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Detail Jadwal Screen
        composable(
            route = "${DestinasiInsertJadwal.route}/detail/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            id?.let {
                DetailJadwalView(
                    onBack = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }
    }
}

// Define Destinations
object DestinasiHomeDokterView {
    const val route = "home"
}

object DestinasiInsertDokter {
    const val route = "insert_dokter"
    const val routesWithArg = "insert_dokter/{id}"
    const val id = "id"
}

object DestinasiInsertJadwal {
    const val route = "insert_jadwal"
    const val routesWithArg = "insert_jadwal/{idDokter}"
    const val idDokter = "idDokter"
}
