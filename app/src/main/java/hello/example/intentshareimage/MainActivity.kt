package hello.example.intentshareimage

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Color.parseColor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var pathString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //var boomButtonMenu = findViewById<View>(R.id.bmb) as BoomButton
        for (i in 0 until bmb.buttonPlaceEnum.buttonNumber()){
            bmb.addBuilder(getSimpleCircleButtonBuilder())
        }
    }
    companion object{
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }
    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode==IMAGE_PICK_CODE){
            gambar.setImageURI(data?.data)
            pathString = gambar.setImageURI(data?.data).toString()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun ambilGambar(view: View) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else
                pickImageFromGallery()
        }
        else
            pickImageFromGallery()
    }

    fun bagikanGambar(view: View) {
        val mimeType = "image/jpeg+png"
        var imageUri = Uri.parse(pathString)

        ShareCompat.IntentBuilder
            .from(this)
            .setType(mimeType)
            .setStream(imageUri)
            .setChooserTitle("Bagikan gambar ke")
            .startChooser()
    }

    private val imageResources = intArrayOf(
        R.drawable.instagram,
        R.drawable.whatsapp,
        R.drawable.twitter,
        R.drawable.facebook,
        R.drawable.path
    )
    private val colorResources = intArrayOf(
        parseColor("#FFFFFF"),
        parseColor("#25D366"),
        parseColor("#1CB7EB"),
        parseColor("#3C5A99"),
        parseColor("#D42028")
    )
    private var imageResourceIndex = 0
    private var colorResourceIndex = 0

    fun getImageResource(): Int {
        if (imageResourceIndex >= imageResources.size) imageResourceIndex = 0
        return imageResources[imageResourceIndex++]
    }

    fun getColorResource(): Int {
        if (colorResourceIndex >= colorResources.size) colorResourceIndex = 0
        return colorResources[colorResourceIndex++]
    }

    fun getSimpleCircleButtonBuilder(): SimpleCircleButton.Builder? {
        return SimpleCircleButton.Builder()
            .normalImageRes(getImageResource())
            .normalColor(getColorResource())
            .listener(){ index ->
                if(index == 0){
                    Toast.makeText(
                        this,
                        "Dibagikan ke Instagram",
                        Toast.LENGTH_SHORT).
                    show();
                }
                else if(index == 1){
                    Toast.makeText(
                        this,
                        "Dibagikan ke Whatsapp",
                        Toast.LENGTH_SHORT).
                    show();
                }
                else if(index == 2){
                    Toast.makeText(
                        this,
                        "Dibagikan ke Twitter",
                        Toast.LENGTH_SHORT).
                    show();
                }
                else if(index == 3){
                    Toast.makeText(
                        this,
                        "Dibagikan ke Facebook",
                        Toast.LENGTH_SHORT).
                    show();
                }
                else if(index == 4){
                    Toast.makeText(
                        this,
                        "Dibagikan ke Path",
                        Toast.LENGTH_SHORT).
                    show();
                }
            }
    }
}
