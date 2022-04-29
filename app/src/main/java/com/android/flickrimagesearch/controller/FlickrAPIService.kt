package com.android.flickrimagesearch.controller

import com.android.flickrimagesearch.BuildConfig
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FlickrAPIService {
	@GET("/services/rest/")
	suspend fun getSearchResults(
		@Query("api_key") apiKey: String = BuildConfig.API_KEY,
		@QueryMap params: Map<String, String>,
		@Query("format") format: String = "json",
		@Query("nojsoncallback") noJsonCallback: Boolean = true,
	): Response<ResponseBody>

	@GET("/{server-id}/{id}_{secret}_{size-suffix}.jpg")
	suspend fun getPhoto(
		@Path("server-id") serverId: String,
		@Path("id") photoId: String,
		@Path("secret") secret: String,
		@Path("size-suffix") sizeSuffix: String = "b",
	): Response<ResponseBody>
}