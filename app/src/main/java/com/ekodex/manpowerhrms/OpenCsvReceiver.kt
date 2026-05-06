package com.ekodex.manpowerhrms

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.File

class OpenCsvReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val filePath = intent.getStringExtra("FILE_URI")

        if (filePath.isNullOrEmpty()) {
            Toast.makeText(context, "File not found", Toast.LENGTH_SHORT).show()
            return
        }

        val file = File(filePath)

        val uri: Uri = FileProvider.getUriForFile(
            context,
            "com.ekodex.manpowerhrms.fileprovider",
            file
        )

        openCsvFile(context, uri)
    }

    private fun openCsvFile(context: Context, fileUri: Uri) {

        val openIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(fileUri, "text/csv")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        try {
            context.startActivity(openIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No app available to open CSV files", Toast.LENGTH_SHORT).show()
        }
    }
}






















//package com.ekodex.manpowerhrms
//
//import android.content.ActivityNotFoundException
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.widget.Toast
//import androidx.core.content.FileProvider
//import java.io.File
//
//class OpenCsvReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context, intent: Intent) {
//        // Retrieve the file URI from the Intent
//        val fileUriString = intent.getStringExtra("FILE_URI")
//        //val fileUri = Uri.parse(fileUriString) // Convert the string back to a Uri
//        val file_Uri: Uri = FileProvider.getUriForFile(context, "com.ekodex.manpowerhrms.fileprovider",File(fileUriString))
//
//        openCsvFile(context, file_Uri,fileUriString)
//    }
//
//    private fun openCsvFile(context: Context, fileUri: Uri, fileUriString: String?) {
//        // Create the intent to view the CSV file
//        val intent = Intent(Intent.ACTION_VIEW).apply {
//            setDataAndType(fileUri, "text/csv") // Use the Uri instead of File
//            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // Grant permission to access the file
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Start in a new task
////            Toast.makeText(context, fileUriString.toString(), Toast.LENGTH_SHORT).show()
////            Log.i("ccccc",fileUriString.toString())
//        }
//
//        try {
//            context.startActivity(intent)
//        } catch (e: ActivityNotFoundException) {
//            Toast.makeText(context, "No app available to open CSV files", Toast.LENGTH_SHORT).show()
//        }
//    }
//}
//
//
//
//
