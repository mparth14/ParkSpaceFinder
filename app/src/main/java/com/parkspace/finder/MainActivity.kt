package com.parkspace.finder

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.navigation.AppNavHost
import com.parkspace.finder.ui.locationPermission.LocationPermissionScreen
import com.parkspace.finder.ui.onboarding.OnboardingScreen
import com.parkspace.finder.ui.theme.ParkSpaceFinderTheme
import com.parkspace.finder.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<AuthViewModel>()
    private val parkingSpaceViewModel by viewModels<ParkingSpaceViewModel>()

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply{
            setKeepOnScreenCondition {
                !splashViewModel.isLoading .value || !viewModel.isReady.value
            }

        }
        setContent {
            ParkSpaceFinderTheme {
                val screen = splashViewModel.startDestination
                AppNavHost(viewModel,parkingSpaceViewModel, startDestination = screen.value, context = this@MainActivity)
            }
        }
    }
}