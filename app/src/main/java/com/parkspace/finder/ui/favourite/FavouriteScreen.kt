package com.parkspace.finder.ui.favourite

/*
 * This file contains UI components related to the favourite screen.
 */
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.parkspace.finder.R
import com.parkspace.finder.ui.theme.spacing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.parkspace.finder.data.ParkingSpaceViewModel
import com.parkspace.finder.data.Resource
import com.parkspace.finder.navigation.ROUTE_PARKING_DETAIL

/*
 * Composable function to display a product card.
 *
 * @param price: The price of the product.
 * @param productName: The name of the product.
 * @param productImage: The URL of the product image.
 * @param productDescription: The description of the product.
 */
@Composable
fun ProductCard(
    price: String,
    productName: String,
    productImage: String,
    productDescription: String,
    onCardClick: () -> Unit = {}
) {
    val painter: Painter = rememberImagePainter(
        data = productImage,
        builder = {
            crossfade(true) // Optional - Enables crossfade animation between images
        }
    )
    var isVisible = true
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val cardwidth = (screenWidth/2) - 20.dp
    var visible by remember { mutableStateOf(isVisible) }
    val scale by animateFloatAsState(
        targetValue = if (visible) 1.1f else 1f,
        animationSpec = spring()
    )
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 500)
    )
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 4.dp)
            .background(Color.White)
            .clickable {
                onCardClick()
            }
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            // Image with heart icon and price
            Box(
                modifier = Modifier
                    .height(170.dp)
                    .width(cardwidth)
                    .background(Color.White)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f) // Maintain aspect ratio
                        .clip(shape = RoundedCornerShape(16.dp))
                )

                // Heart icon at top right corner
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(5.dp)

                ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_favourite_filled),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .padding(4.dp)
                                .scale(scale)
                                .alpha(alpha)
                                .clickable {
                                    visible = false
                                }
                        )
                }

                // Price at bottom right corner
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(9.dp)
                        .background(Color.Black.copy(alpha = 0.65f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "$ $price/hr",
                        color = Color.White,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 4.dp)
                    .widthIn(max = cardwidth - 10.dp)
            ){
                Text(
                    text = productName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 21.sp
                    ),
                    maxLines = 1,
                )
            }

            Row(
                modifier = Modifier.padding( horizontal = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_location_grey),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp) // Adjust size as needed
                )
                Text(
                    text = productDescription,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xffC8C7CC)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // Product details

            Spacer(modifier = Modifier.height(1.dp))

            // Stars for rating
            Row(
                modifier = Modifier
                    .padding(horizontal = 3.dp, vertical = 4.dp ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(5) { index ->
                    Image(
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = null,
                        //tint = if (index < rating.toInt()) Color.Yellow else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

/*
 * Composable function to display the favourite screen.
 *
 * @param context: The context of the screen.
 * @param favouritescreenViewModel: The view model for favourite screen.
 * @param navController: Nav controller for navigation.
 */
@Composable
fun FavouriteScreen(context: Context, favouritescreenViewModel: ParkingSpaceViewModel = hiltViewModel(), navController: NavHostController) {
    val spacing = MaterialTheme.spacing
    val scrollState = rememberScrollState()
    val parkingSpaces = favouritescreenViewModel.parkingSpaces
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 80.dp)
            .padding(horizontal = 6.dp)
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val resource by parkingSpaces.collectAsState()
        Text(
            text = "Favourites",
            style = MaterialTheme.typography.displayMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Bold)
        )

        LazyColumn {
            when (val result = resource) {
                is Resource.Success -> {
                    val spaces = result.result
                    items(spaces.chunked(2)) { pairOfSpaces ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            pairOfSpaces.forEach { space ->
                                ProductCard(
                                    price = space.hourlyPrice.toString(),
                                    productName = space.name,
                                    productImage = space.imageURL,
                                    productDescription = "${String.format("%.2f", space.distanceFromCurrentLocation)} km away · 27 left"
                                ){
                                    navController.navigate(ROUTE_PARKING_DETAIL.replace("{parkingId}", space.id))
                                }
                                Spacer(modifier = Modifier.padding(8.dp))
                            }
                        }
                    }
                }
                is Resource.Failure -> item {
                    Text(text = "Error")
                }
                Resource.Loading -> item {
                    Text(text = "Loading...")
                }
                null -> item {
                    Text(text = "Error")
                }
            }
        }

    }
}