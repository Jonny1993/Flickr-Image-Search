package com.android.flickrimagesearch.controller

import android.app.AlertDialog
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.android.flickrimagesearch.FieldAndButton
import com.android.flickrimagesearch.MainActivity
import com.android.flickrimagesearch.SearchResultList
import com.android.flickrimagesearch.ui.theme.FlickrImageSearchTheme
import com.android.flickrimagesearch.utils.JSONParser
import com.android.flickrimagesearch.utils.NetworkUtils
import com.android.flickrimagesearch.utils.ThreadUtil
import java.util.concurrent.TimeUnit

class Controller {
	fun searchButtonOnClick(searchTerm: String, mainActivity: MainActivity) {
		if(NetworkUtils.isNetworkAvailable(mainActivity)) {
			if (searchTerm.isNotBlank() && isNotSpamClick(mainActivity)) {
				ThreadUtil.startThread {
					JSONParser.parsePhotoResults(searchTerm)
				}
				ThreadUtil.startUIThread(1200) {
					Log.d("Messages", JSONParser.photos.first().title)
					mainActivity.displaySearchResults()
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