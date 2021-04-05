package com.aysegul.todoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class YapilacaklarAdapter(var mContext:Context,var yapilacakListe:ArrayList<Yapilacaklar>,var vt:VeritabaniYardimcisi)
    :RecyclerView.Adapter<YapilacaklarAdapter.cardTasarimTutucu>() {

    inner class cardTasarimTutucu(view: View):RecyclerView.ViewHolder(view){
        var tasarim_card:CardView
        var yapilacak_resim:ImageView
        var yapilacak_yazi:TextView
        var delete_resim:ImageView

        init {
            tasarim_card = view.findViewById(R.id.tasarim_card)
            yapilacak_resim = view.findViewById(R.id.yapilacak_resim)
            yapilacak_yazi = view.findViewById(R.id.yapilacak_yazi)
            delete_resim = view.findViewById(R.id.delete_resim)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardTasarimTutucu {
        val tasarim = LayoutInflater.from(mContext).inflate(R.layout.yapilacak_liste_tasarim,parent,false)
        return cardTasarimTutucu(tasarim)
    }

    override fun onBindViewHolder(holder: cardTasarimTutucu, position: Int) {
        val yapilacakIs = yapilacakListe.get(position)
        holder.yapilacak_yazi.text = "${yapilacakIs.yapilacak_is}"
        holder.delete_resim.setOnClickListener {
            Toast.makeText(mContext,"${yapilacakIs.yapilacak_is} silindi", Toast.LENGTH_LONG).show()
            YapilacaklarDao().yapilacaklarSil(vt,yapilacakIs.yapilacak_id)
            yapilacakListe = YapilacaklarDao().tumYapilacaklar(vt)
            notifyDataSetChanged()
        }
        holder.tasarim_card.setOnClickListener {
            //Sayfa geçişi
            val intent = Intent(mContext,DetayActivity::class.java)
            intent.putExtra("nesne",yapilacakIs)
            mContext.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return yapilacakListe.size
    }
}