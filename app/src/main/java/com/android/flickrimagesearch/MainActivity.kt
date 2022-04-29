package com.android.flickrimagesearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.android.flickrimagesearch.controller.Controller
import com.android.flickrimagesearch.ui.theme.FlickrImageSearchTheme
import com.android.flickrimagesearch.utils.JSONParser
import java.sql.Timestamp
import java.time.Instant
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
	val controller = Controller()
	var lastClicked = 0L
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			FlickrImageSearchTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
				) {
					Column() {
						FieldAndButton(controller, this@MainActivity)
					}
				}
			}
		}
	}

	fun displaySearchResults() {
		this.setContent() {
			FlickrImageSearchTheme() {
				Surface(
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
				) {
					Column() {
						FieldAndButton(controller, this@MainActivity)
						SearchResultList(flickrPhotos = JSONParser.photos)
					}
				}
			}
		}
	}
}

@Composable
fun SearchResultList(flickrPhotos: List<FlickrPhoto>) {
	LazyColumn(modifier = Modifier.clickable { JSONParser.photos }) {
		items(flickrPhotos) { photo ->
			RowInfo(flickrPhoto = photo)
		}
	}
}
@Composable
fun RowInfo(flickrPhoto: FlickrPhoto) {
	Log.d("URL", JSONParser.getPhotoUrl(flickrPhoto))
	val context = LocalContext.current
	val myIntent = Intent(context, ImageActivity::class.java)
	Row() {
		Image(
			painter = rememberAsyncImagePainter(JSONParser.getPhotoUrl(flickrPhoto)),
			contentDescription = "Search Result Image",
			modifier = Modifier
				.padding(all = 4.dp)
				.size(128.dp)
				.clip(RectangleShape)
				.border(1.dp, MaterialTheme.colors.primaryVariant, RectangleShape)
				.clickable {
					myIntent.putExtra("theImage", flickrPhoto)
					context.startActivity(myIntent)
				}
		)

		Spacer(modifier = Modifier.width(8.dp))

		Column() {
			Spacer(modifier = Modifier.height(8.dp))
			Text(
				text = flickrPhoto.title,
				maxLines = 1,
				style = MaterialTheme.typography.body1,
			)
		}
	}
	Divider(color = MaterialTheme.colors.onSurface, thickness = 1.dp)
}

@Composable
fun FieldAndButton(controller: Controller, mainActivity: MainActivity) {
	var text by remember { mutableStateOf("")}
	Row() {
		OutlinedTextField(
			value = text,
			onValueChange = { text = it },
			label = { Text(text = "Image Search") },
			modifier = Modifier.fillMaxWidth(0.69f)
		)
		
		Spacer(modifier = Modifier.padding(all = 4.dp))
		
		Button(
			onClick = { controller.searchButtonOnClick(text, mainActivity)},
			contentPadding = PaddingValues(
				start = 20.dp,
				top = 12.dp,
				end = 20.dp,
				bottom = 12.dp
			),
			modifier = Modifier.padding(top = 12.dp).fillMaxWidth()
		) {
			Icon(
				Icons.Filled.Search,
				contentDescription = "Search",
				modifier = Modifier.size(ButtonDefaults.IconSize)
			)
			Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
			Text(text = "Search")
		}
	}
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
	FlickrImageSearchTheme {
		Column() {
			FieldAndButton(Controller(), MainActivity())
			RowInfo(flickrPhoto = FlickrPhoto("","","","","","Testing"))
		}
	}
}