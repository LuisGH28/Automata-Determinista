package com.example.automatas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.io.BufferedReader
import java.io.InputStreamReader
import com.example.automatas.FileUp.Companion.Text
import java.io.OutputStreamWriter


class ReadFile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_file)

        Text=findViewById(R.id.txtTexts)
        if(FileUp().existFile(fileList(), "text.txt")){
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
}