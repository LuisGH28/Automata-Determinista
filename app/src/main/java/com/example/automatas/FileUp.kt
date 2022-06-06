package com.example.automatas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_file_up.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class FileUp : AppCompatActivity() {

    companion object{
        var Text: EditText?=null
        const val PICK_PDF_FILE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_up)
        fileBtn.setOnClickListener{ checkPermission()}

        Text=findViewById(R.id.txtText)
        if(existFile(fileList(), "text.txt")){
            var content=""
            val file= InputStreamReader(openFileInput("text.txt"))
            val bf= BufferedReader(file)
            var line = bf.readLine()
            while (line!=null){
                content=content+line+"\n"
                line=bf.readLine()
            }
            Text?.setText(content)
        }

    }

    private fun checkPermission() =
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //No aceptado
            requestFilePermission()
        }else{
            openFile()
            openFiles(Uri.fromFile(filesDir))
        }



    private fun openFile() {
        Toast.makeText(this, "Abriendo almacenamiento", Toast.LENGTH_LONG).show()
    }

    private fun requestFilePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(this, "Rechazado", Toast.LENGTH_LONG).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 777)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 777){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openFile()
            }else{
                Toast.makeText(this, "Rechazado", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun save(view: View) {
        val file= OutputStreamWriter(openFileOutput("text.txt", MODE_PRIVATE))
        file.write(Text?.text.toString())
        file.flush()
        file.close()
        finish()
    }

    fun existFile(files:Array<String>, file:String): Boolean {
        files.forEach {
            if (file == it)
                return true
        }
        return false
    }

    private fun openFiles(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"

            // Optionally, specify a URI for the file that should appear in the
            // system file picker when it loads.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }

        startActivityForResult(intent, PICK_PDF_FILE)
    }
}
