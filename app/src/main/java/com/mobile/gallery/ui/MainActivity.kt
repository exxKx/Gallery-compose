package com.mobile.gallery.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobile.gallery.ui.home.HomeGallery
import com.mobile.gallery.ui.home.HomeMutation.*
import com.mobile.gallery.ui.home.HomeViewModel
import com.mobile.gallery.ui.image.Image
import com.mobile.gallery.ui.theme.GalleryTheme
import java.net.URLDecoder

class MainActivity : ComponentActivity() {

    val homeViewModel: HomeViewModel by lazy { HomeViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GalleryTheme {
                val navController = rememberNavController()

                navController.addOnDestinationChangedListener { nc, dest, b ->
                    when (dest.route) {
                        "gallery" -> homeViewModel.mutation(Resume)
                    }

                }

                NavHost(navController = navController, startDestination = "gallery") {
                    composable("gallery") { HomeGallery(navController) }
                    composable("image/{image_url}/{title}",
                        arguments = listOf(
                            navArgument("image_url") { type = NavType.StringType },
                            navArgument("title") { type = NavType.StringType }
                        )
                    ) { backStackEntry ->
                        Image(
                            imageUrl = backStackEntry.arguments?.getString("image_url") ?: "",
                            text = (backStackEntry.arguments?.getString("title") ?: "").let {
                                URLDecoder.decode(it, "UTF-8")
                            }
                        )
                    }
                }
            }
        }
    }
}