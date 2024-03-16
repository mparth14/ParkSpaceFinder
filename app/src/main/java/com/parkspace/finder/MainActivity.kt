package com.parkspace.finder

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.navigation.AppNavHost
import com.parkspace.finder.ui.theme.ParkSpaceFinderTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ParkSpaceFinderTheme {
                // A surface container using the 'background' color from the theme
                AppNavHost(viewModel)
            }
        }
    }
}