package com.aysegul.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.info.sqlitekullanimihazirveritabani.DatabaseCopyHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.yapilacak_liste_tasarim.*
import java.io.IOException

class MainActivity : AppCompatActivity(),SearchView.OnQueryTextListener {

    private lateinit var yapilacaklarListe : ArrayList<Yapilacaklar>
    private lateinit var adapter: YapilacaklarAdapter
    private lateinit var vt:VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        veritabaniKopyala()

        toolbarMainActivity.title = "Yapılacaklar"
       // toolbarMainActivity.setLogo(R.drawable.todoicon)
        setSupportActionBar(toolbarMainActivity)

        //Adapter İşlemleri
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        vt = VeritabaniYardimcisi(this)  //vt nesnesini kullanabilmek için
        tumYapilacaklariAl()

        //Fab
        fab.setOnClickListener {
            startActivity(Intent(this,KayitActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.arama_menu,menu)
        val item = menu.findItem(R.id.action_ara)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {   //Bulunduğu sayfanın back tuşu  Yeni sayfa oluşturuyor->Anasayfada kendini kaldırıyor
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        Log.e("Gönderilen arama sonucu",query)
        aramaYap(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        Log.e("Harf girildikce arama",newText)
        aramaYap(newText)
        return true
    }

    //Veritabani Kopyalama İşlemi
    fun veritabaniKopyala(){
        val copyHelper = DatabaseCopyHelper(this)
        try {
            copyHelper.createDataBase()
            copyHelper.openDataBase()
        }catch (e: IOException){  //Hata olursa görebilmek için
            e.printStackTrace()
        }
    }

    fun tumYapilacaklariAl(){
        yapilacaklarListe = YapilacaklarDao().tumYapilacaklar(vt)
        adapter = YapilacaklarAdapter(this,yapilacaklarListe,vt)
        recyclerView.adapter = adapter
        if(yapilacaklarListe.size != 0){
            textBasla.visibility = View.VISIBLE
            endText.visibility = View.GONE
        }else{
            textBasla.visibility = View.GONE
            endText.visibility = View.VISIBLE
        }
    }

    fun aramaYap(aramaKelimesi:String){
        yapilacaklarListe = YapilacaklarDao().yapilacaklarAra(vt,aramaKelimesi)
        adapter = YapilacaklarAdapter(this,yapilacaklarListe,vt)
        recyclerView.adapter = adapter
    }

}