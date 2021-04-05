package com.aysegul.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_kayit.*

class KayitActivity : AppCompatActivity() {

    private lateinit var vt:VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit)

        toolbarKayitActivity.title = "Yapılacak İş Kayıt"
        setSupportActionBar(toolbarKayitActivity)

        vt = VeritabaniYardimcisi(this)

        buttonKaydet.setOnClickListener {
            val yapilacakIs = editTextYapilacakIs.text.toString()
            if(TextUtils.isEmpty(yapilacakIs)){
                Toast.makeText(applicationContext,"Yapılacak İş Boş",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            kayit(yapilacakIs)
        }
    }

    fun kayit(yapilacak_is:String){
        Log.e("Yapılacak iş","$yapilacak_is")
        YapilacaklarDao().yapilacaklarEkle(vt,yapilacak_is)
        startActivity(Intent(this,MainActivity::class.java))
    }
}