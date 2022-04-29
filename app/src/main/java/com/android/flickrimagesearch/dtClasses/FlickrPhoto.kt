package com.android.flickrimagesearch

import java.io.Serializable

class FlickrPhoto(
	val id: String,
	val owner: String,
	val secret: String,
	val server: String,
	val farm: String,
	val title: String
	): Serializable {
}

enum class FlickrPhotoSizes(val suffix: String) {
	Thumbnail75px("s"), Thumbnail150px("q"), Thumbnail100px("t"), Small240px("m"), Small320("n"), Small400px("w"), Medium640px("z"), Medium800px("c"), Large1024px("b")
}