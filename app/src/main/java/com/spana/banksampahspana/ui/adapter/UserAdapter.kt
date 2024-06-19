package com.spana.banksampahspana.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spana.banksampahspana.data.remote.response.Admin
import com.spana.banksampahspana.data.remote.response.User
import com.spana.banksampahspana.databinding.UserItemBinding

class UserAdapter(private val users: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]

        holder.binding.txtNameVal.text = user.name
        holder.binding.txtNisVal.text = user.userDetail.nis
        holder.binding.txtGenderVal.text = user.userDetail.gender
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)
}