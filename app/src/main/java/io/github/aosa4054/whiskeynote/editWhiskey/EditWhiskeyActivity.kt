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
import android.view.KeyEvent
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import io.github.aosa4054.whiskeynote.R
import kotlinx.android.synthetic.main.activity_edit_whiskey.*
import kotlinx.android.synthetic.main.fragment_edit_whiskey.*
import java.io.IOException
import java.util.*
import permissions.dispatcher.*

@RuntimePermissions
class EditWhiskeyActivity : AppCompatActivity(),
        EditwhiskeyNavigator, EditWhiskeyFragment.EditWhiskeyFragmentListener {

    private lateinit var uri: Uri
    lateinit var imageUri: Uri
    private val REQUEST_CHOOSER = 100
    private val RESULT_CROP = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_whiskey)
        setSupportActionBar(toolbar_edit_whiskey)
        toolbar_edit_whiskey.setNavigationOnClickListener { v ->
            showCancelDialog()
        }
    }

    override fun getImage(){
        getImgWithPermissionCheck()
    }

    //<editor-fold desc ="to get and crop image">
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun getImg(){
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

    private fun crop(photoUri: Uri){
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(photoUri, "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("outputX", 700)
        intent.putExtra("outputY", 700)
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("scale", true)
        intent.putExtra("return-data", true)
        intent.putExtra(MediaStore.EXTRA_MEDIA_TITLE, "Whiskeynote Media/${Random()}.jpg")
        startActivityForResult(intent, RESULT_CROP)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fun getRealPath(uri: Uri): Uri{
            return if (uri.toString().startsWith("content://com.android.providers.media.documents")) {
                Uri.parse("content://media/external/images/media/${uri.toString().takeLast(6)}")
            }else{
                uri
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHOOSER) {
            if (resultCode != Activity.RESULT_OK) return
            val resultUri = (if (data != null) data.data else uri) ?: return
            crop(getRealPath(resultUri))
        }

        if (requestCode == RESULT_CROP){
            if (resultCode != Activity.RESULT_OK) return

            imageUri = (if (data != null) data.data else uri) ?: return
            MediaScannerConnection.scanFile(this,
                    arrayOf(imageUri.path),
                    arrayOf("image/jpeg"),
                    null)

            try {
                val sourceBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                val bitmap = Bitmap.createBitmap(sourceBitmap, 0, 0, 700, 700, null, true)
                val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                roundedBitmapDrawable.cornerRadius = 350f
                editing_image.setImageDrawable(roundedBitmapDrawable)
            }catch (t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this, "画像の変換でエラーが発生しました", Toast.LENGTH_SHORT).show()
            }
        }
    }
    //</editor-fold>

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onPermissionDenied(){
        Toast.makeText(this, "画像を挿入するには本体の設定からストレージの権限を付与してください", Toast.LENGTH_LONG).show()
        //TODO: 本体設定に飛ばす
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun onNeverAskAgain(){
        Toast.makeText(this, "画像を挿入するには本体の設定からストレージの権限を付与してください", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            showCancelDialog()
            false
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    private fun showCancelDialog() {
        AlertDialog.Builder(this)
                .setTitle("編集内容を保存しますか？")
                .setMessage("保存した内容は後から編集、削除することができます。")
                .setPositiveButton("保存", null) //save()
                .setNegativeButton("破棄") { _, _ -> finish() }
                .show()
    }
}
