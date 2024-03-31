package com.parkspace.finder.ui.payment

/*
 * This file contains composable functions related to the payment UI.
 */
import androidx.compose.runtime.Composable
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

/**
 * Composable function to create and return a list of konfetti parties for the payment screen.
 * @return List of konfetti parties.
 */
@Composable
fun KonfettiViewModel(): List<Party> {
    return listOf(
        Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 360,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3)
        )
    )
}
