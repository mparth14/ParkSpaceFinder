package com.parkspace.finder.ui.booking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.parkspace.finder.ui.theme.NewPickersTheme

class EnterDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewPickersTheme {
                val navController = rememberNavController()
                EnterDetailsContent(navController = navController) {
                    // Handle onViewTicketClick action
                }
            }
        }
    }
}
