package com.android.flickrimagesearch.utils

import com.android.flickrimagesearch.controller.APIResult
import com.android.flickrimagesearch.controller.FlickrAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit

class Connection {
	companion object {
		const val BASE_URL = "https://www.flickr.com"
		const val FLICKR_JPG_URL = "https://live.staticflickr.com"
		fun getRequest(params: Map<String, String>, result: APIResult){
			val retrofit = Retrofit.Builder()
				.baseUrl(BASE_URL)
				.build()

			val service = retrofit.create(FlickrAPIService::class.java)
			CoroutineScope(Dispatchers.IO).launch {

				val response = service.getSearchResults(params = params)

				withContext(Dispatchers.Main) {
					if (response.isSuccessful) {
						result.success(JSONObject(response.body()!!.string()))
					} else {
						result.error(Throwable("Response error: ${response.errorBody()}"))
					}
				}
			}
		}
	}
}