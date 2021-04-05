package com.aysegul.todoapp

import android.content.ContentValues
import android.view.View
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_main.*

class YapilacaklarDao {

    fun tumYapilacaklar(vt:VeritabaniYardimcisi):ArrayList<Yapilacaklar>{

        val db = vt.writableDatabase
        val yapilacakListe = ArrayList<Yapilacaklar>()
        val cursor = db.rawQuery("SELECT * FROM yapilacaklar",null)

        while (cursor.moveToNext()){
            val yapilacak = Yapilacaklar(cursor.getInt(cursor.getColumnIndex("yapilacak_id"))
            ,cursor.getString(cursor.getColumnIndex("yapilacak_is")))
            yapilacakListe.add(yapilacak)
        }
        return yapilacakListe
    }

    fun yapilacaklarAra(vt:VeritabaniYardimcisi,arananIs:String):ArrayList<Yapilacaklar>{

        val db = vt.writableDatabase
        val yapilacakListe = ArrayList<Yapilacaklar>()
        val cursor = db.rawQuery("SELECT * FROM yapilacaklar WHERE yapilacak_is like '%$arananIs%'",null)

        while (cursor.moveToNext()){
            val yapilacak = Yapilacaklar(cursor.getInt(cursor.getColumnIndex("yapilacak_id"))
                    ,cursor.getString(cursor.getColumnIndex("yapilacak_is")))
            yapilacakListe.add(yapilacak)
        }
        return yapilacakListe
    }

    fun yapilacaklarSil(vt:VeritabaniYardimcisi,yapilacak_id:Int){
        val db = vt.writableDatabase
        db.delete("yapilacaklar","yapilacak_id=?", arrayOf(yapilacak_id.toString()))
        db.close()
    }

    fun yapilacaklarEkle(vt:VeritabaniYardimcisi,yapilacak_is:String){
        val db = vt.writableDatabase
        val values = ContentValues()
        values.put("yapilacak_is",yapilacak_is)
        db.insertOrThrow("yapilacaklar",null,values)
        db.close()
    }
    
    fun yapilacaklarGuncelle(vt:VeritabaniYardimcisi,yapilacak_id:Int,yapilacak_is:String){
        val db = vt.writableDatabase
        val values = ContentValues()
        values.put("yapilacak_is",yapilacak_is)
        db.update("yapilacaklar",values,"yapilacak_id=?", arrayOf(yapilacak_id.toString()))
        db.close()
    }

}