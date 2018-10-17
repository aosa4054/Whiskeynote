package io.github.aosa4054.whiskeynote.editWhiskey

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.activity_edit_whiskey.*
import kotlinx.android.synthetic.main.fragment_edit_whiskey.*
import permissions.dispatcher.NeedsPermission
import java.io.IOException


class EditWhiskeyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_whiskey)
        setSupportActionBar(toolbar_edit_whiskey)
        toolbar_edit_whiskey.setNavigationOnClickListener { v ->
            //確認ダイアログ
            finish()
        }
    }

    lateinit var uri: Uri
    private val REQUEST_CHOOSER = 1000
    private val RESULT_CROP = 782

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun getImage(){
        val photoName = System.currentTimeMillis().toString() + ".jpg"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, photoName)
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        uri = this.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues) ?: return
        val camIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        val gallIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        gallIntent.addCategory(Intent.CATEGORY_OPENABLE)
        gallIntent.type = "image/jpeg"
        val intent = Intent.createChooser(camIntent, "ギャラリーから選択")
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(gallIntent))
        startActivityForResult(intent, REQUEST_CHOOSER)
    }

    fun crop(photoUri: Uri){
        val intent = Intent("com.android.camera.action.CROP")
        intent.data = photoUri
        intent.putExtra("outputX", 400)
        intent.putExtra("outputY", 400)
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("scale", true)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, RESULT_CROP)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CHOOSER) {
            if (resultCode != Activity.RESULT_OK) return
            val resultUri = (if (data != null) data.data else uri) ?: return
            crop(resultUri)
        }

        if (requestCode == RESULT_CROP){
            if (resultCode != Activity.RESULT_OK) {
                return
            }
            val mUri = (if (data != null) data.data else uri) ?: return
            MediaScannerConnection.scanFile(this,
                    arrayOf(mUri.path),
                    arrayOf("image/jpeg"),
                    null)

            lateinit var bitmap: Bitmap
            try {
                val sourceBitmap = MediaStore.Images.Media.getBitmap(contentResolver, mUri)
                bitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, 400, 400, null, true)
            }catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "画像の変換でエラーが発生しました", Toast.LENGTH_SHORT).show()
            }
            editing_image.setImageBitmap(bitmap)
        }
    }
}
