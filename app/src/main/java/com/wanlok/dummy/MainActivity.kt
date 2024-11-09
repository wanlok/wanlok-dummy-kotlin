package com.wanlok.dummy

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.wanlok.dummy.Dummy.Companion.LOGO


class MainActivity : AppCompatActivity() {
    private val tag: String = "MainActivity"
    lateinit var shareButton: Button

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            // Copy button click
        }
    }

    fun convertToBitmap(base64String: String): Bitmap {
        val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }

    fun getBitmapUri(bitmap: Bitmap): Uri {
        return Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, bitmap, "", ""))
    }

    fun share(text: String) {
        val intent = Intent(Intent.ACTION_SEND)

        intent.putExtra(Intent.EXTRA_TITLE, "Your Title")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Subtitle")

        intent.putExtra(Intent.EXTRA_STREAM, getBitmapUri(convertToBitmap(LOGO)))

        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT, text)

        launcher.launch(Intent.createChooser(intent, null))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val text = "Hello World"
        shareButton = findViewById(R.id.share_button)
        shareButton.setOnClickListener { share(text) }
    }
}