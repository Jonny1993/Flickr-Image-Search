package com.android.flickrimagesearch

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.flickrimagesearch.ui.theme.FlickrImageSearchTheme
import com.android.flickrimagesearch.utils.JSONParser

class ImageActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			FlickrImageSearchTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
				) {
					ImageDisplay(intent)
				}
			}
		}
	}
}

@Composable
fun ImageDisplay(image: Intent?) {
	Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
		Text(text = "Image", style = MaterialTheme.typography.h2, fontWeight = FontWeight.Bold)
		if (image != null) {
			val clickedPhoto = image.getSerializableExtra("theImage") as FlickrPhoto
			Image(
				painter = rememberAsyncImagePainter(JSONParser.getPhotoUrl(clickedPhoto)),
				contentDescription = "Clicked Image",
				modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
			)
			Text(text = clickedPhoto.title, style = MaterialTheme.typography.body2)
		}
		else {
			Text(text = "Image could not be loaded", style = MaterialTheme.typography.h2)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
	FlickrImageSearchTheme {
	}
}