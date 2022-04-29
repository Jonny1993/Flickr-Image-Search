package com.android.flickrimagesearch.controller

import org.json.JSONObject

interface APIResult {
	fun success(json: JSONObject)
	fun error(t: Throwable)
}
