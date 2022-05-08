package com.example.automatas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class FileUp : AppCompatActivity() {
    var txtText: EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_up)

        txtText=findViewById(R.id.txtText)
        if(existFile(fileList(), "text.txt")){
            var content=""
            val file= InputStreamReader(openFileInput("text.txt"))
            val bf= BufferedReader(file)
            var line = bf.readLine()
            while (line!=null){
                content=content+line+"\n"
                line=bf.readLine()
            }
            txtText?.setText(content)
        }
    }

    fun save(view: View) {
        val file= OutputStreamWriter(openFileOutput("text.txt", MODE_PRIVATE))
        file.write(txtText?.text.toString())
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
}