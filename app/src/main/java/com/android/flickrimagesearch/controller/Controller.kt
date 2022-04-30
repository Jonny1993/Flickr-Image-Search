package com.android.flickrimagesearch.controller

import android.app.AlertDialog
import android.util.Log
import com.android.flickrimagesearch.MainActivity
import com.android.flickrimagesearch.utils.JSONParser
import com.android.flickrimagesearch.utils.NetworkUtils
import com.android.flickrimagesearch.utils.ThreadUtil
import java.util.concurrent.TimeUnit

class Controller {
	fun searchButtonOnClick(searchTerm: String, mainActivity: MainActivity) {
		if(NetworkUtils.isNetworkAvailable(mainActivity)) {
			val cleanSearch = searchTerm.replace(Regex("[^a-zA-Z0-9 ]"), "")
			Log.d("Clean", cleanSearch)
			if (cleanSearch.isNotBlank() && isNotSpamClick(mainActivity)) {
				ThreadUtil.startThread {
					JSONParser.parsePhotoResults(cleanSearch)
				}
				ThreadUtil.startUIThread(1200) {
					if (JSONParser.photos.isNotEmpty()) {
						Log.d("Messages", JSONParser.photos.first().title)
						mainActivity.displaySearchResults()
					}
					else {
						mainActivity.displayEmptyResultToast()
					}
				}
			}
		}
		else {
			val builder = AlertDialog.Builder(mainActivity)
			builder.setTitle("Warning!")
			builder.setMessage("There is no network, please try again when you are online.")
			builder.setPositiveButton("OK"){_, _ ->
			}
			val dialog = builder.create()
			dialog.show()
		}
	}

	private fun isNotSpamClick(mainActivity: MainActivity): Boolean {
		val timeSinceLastClick = System.currentTimeMillis() - mainActivity.lastClicked
		Log.d("TimeBeforeIf", timeSinceLastClick.toString())
		if (timeSinceLastClick > TimeUnit.MILLISECONDS.convert(3L, TimeUnit.SECONDS)) {
			mainActivity.lastClicked = System.currentTimeMillis()
			return true
		}
		return false
	}
}