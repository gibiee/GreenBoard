package com.gibisoft.kwton_environment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container, Login()).commit()
    }

    fun onFragmentChange(str: String) {
        when(str) {
            "로그인" -> supportFragmentManager.beginTransaction().replace(R.id.container, Login()).commit()
            "회원가입" -> supportFragmentManager.beginTransaction().replace(R.id.container, Register()).commit()
            "타임라인" -> supportFragmentManager.beginTransaction().replace(R.id.container, Timeline()).commit()
            "글작성" -> supportFragmentManager.beginTransaction().replace(R.id.container, Write()).commit()
            "인포" -> supportFragmentManager.beginTransaction().replace(R.id.container, Info()).commit()
            "랭크" -> supportFragmentManager.beginTransaction().replace(R.id.container, Rank()).commit()
        }
    }
}
