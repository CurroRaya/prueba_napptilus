package com.raya.curro.pruebanapptilus.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raya.curro.pruebanapptilus.R
import com.raya.curro.pruebanapptilus.data.model.Results
import com.raya.curro.pruebanapptilus.ui.adapters.listeners.OnCharacterClickListener
import com.raya.curro.pruebanapptilus.ui.adapters.viewholders.CharacterViewHolder

class CharacterAdapter(private var context: Context, private var resultCharactersList: ArrayList<Results>, private val listener: OnCharacterClickListener): RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.characters_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val data = resultCharactersList[position]
        holder.bind(context, data)
        holder.itemView.setOnClickListener {
            listener.onCharacterClick(data.id)
        }
    }

    override fun getItemCount(): Int {
        return resultCharactersList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setChatacterList(newCharactersList: ArrayList<Results>){
        resultCharactersList.addAll(newCharactersList)
        notifyDataSetChanged()
    }
}