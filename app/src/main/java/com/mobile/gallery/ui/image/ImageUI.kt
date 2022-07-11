package com.mobile.gallery.ui.image

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun Image(imageUrl: String, text: String) {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        AsyncImage(
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth(),
            model = imageUrl,
            contentDescription = ""
        )
        Text(
            fontSize = 24.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp),
            text = text
        )
    }
}
