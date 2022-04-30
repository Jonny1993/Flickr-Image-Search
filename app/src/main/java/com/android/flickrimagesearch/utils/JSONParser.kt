package com.android.flickrimagesearch.utils

import android.util.Log
import com.android.flickrimagesearch.controller.APIResult
import com.android.flickrimagesearch.FlickrPhoto
import com.android.flickrimagesearch.FlickrPhotoSizes
import org.json.JSONObject

class JSONParser {
	companion object {
		val photos = mutableListOf<FlickrPhoto>()
		fun parsePhotoResults(searchText: String) {
			photos.clear()
			val params = mapOf(Pair("method", "flickr.photos.search"), Pair("text", searchText))
			Connection.getRequest(params, object : APIResult {
				override fun success(json: JSONObject) {
					Log.d("Message0", json.toString())
					if (json.has("photos")) {
						val photosArray = json.getJSONObject("photos").getJSONArray("photo")
						Log.d("Message1", "Length of array: ${photosArray.length()}")
						for (i in 0 until photosArray.length()) {
							val photo = photosArray.getJSONObject(i)
							photos.add(
								FlickrPhoto(
									photo.getString("id"),
									photo.getString("owner"),
									photo.getString("secret"),
									photo.getString("server"),
									photo.getString("farm"),
									photo.getString("title")
								)
							)
						}
						if (photos.isNotEmpty()) Log.d("Message", photos.last().title)
					}
				}

				override fun error(t: Throwable) {
					Log.getStackTraceString(t)
				}
			})
		}

		fun getPhotoUrl(flickrPhoto: FlickrPhoto, customSize: String = FlickrPhotoSizes.Large1024px.suffix): String {
			return "${Connection.FLICKR_JPG_URL}/${flickrPhoto.server}/${flickrPhoto.id}_${flickrPhoto.secret}_$customSize.jpg"
		}
	}
}