package com.parkspace.finder.ui.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import com.parkspace.finder.data.AuthViewModel
import com.parkspace.finder.ui.theme.spacing
import androidx.compose.foundation.rememberScrollState


@Composable
fun ProductCard(
    price: String,
    productName: String,
    productDescription: String,
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 5.dp, vertical = 4.dp)
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            // Image with heart icon and price
            Box(
                modifier = Modifier
                    .height(170.dp)
                    .width(180.dp)
                    .background(Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.parkingphoto),
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Adjust content scale as needed
                    modifier = Modifier
                        .fillMaxWidth()
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
                        modifier = Modifier.size(32.dp).padding(4.dp)
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
                        text = "$price/hr",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 4.dp)
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

@Composable
fun FavouriteScreen(viewModel: AuthViewModel?, navController: NavHostController) {
    val spacing = MaterialTheme.spacing
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 80.dp)
            .padding(horizontal = 6.dp)
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(

        ){
            Text(
                text = "Favourites",
                style = MaterialTheme.typography.displayMedium.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                ))
        }

        LazyColumn {
            items(10) { index ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    ProductCard(
                        price = "Price ",
                        productName = "Product ",
                        productDescription = "Description "
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    ProductCard(
                        price = "Price ",
                        productName = "Product ",
                        productDescription = "Description ",

                    )
                }
            }
        }

    }
}