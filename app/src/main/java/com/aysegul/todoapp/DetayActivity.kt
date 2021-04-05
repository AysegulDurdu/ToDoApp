package com.aysegul.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_detay.*
import kotlinx.android.synthetic.main.activity_kayit.*

class DetayActivity : AppCompatActivity() {

    private lateinit var yapilacak : Yapilacaklar
    private lateinit var vt:VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detay)

        toolbarDetayActivity.title = "Yapılacak İş Detay"
        setSupportActionBar(toolbarDetayActivity)

        vt = VeritabaniYardimcisi(this)

        yapilacak = intent.getSerializableExtra("nesne") as Yapilacaklar
        editTextIsGuncelle.setText(yapilacak.yapilacak_is)

        buttonGuncelle.setOnClickListener {
           val yapilacakIs = editTextIsGuncelle.text.toString()
            guncelle(yapilacak.yapilacak_id,yapilacakIs)
        }

    }
    fun guncelle(yapilacak_id:Int,yapilacak_is:String){
        Log.e("İş Güncelle","$yapilacak_id-$yapilacak_is")
        YapilacaklarDao().yapilacaklarGuncelle(vt,yapilacak_id,yapilacak_is)
        startActivity(Intent(this,MainActivity::class.java))
    }
}