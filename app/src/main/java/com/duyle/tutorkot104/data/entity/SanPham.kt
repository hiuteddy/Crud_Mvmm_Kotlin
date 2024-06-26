package com.duyle.tutorkot104.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sanpham_table")
data class SanPham(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val price: Float,
    val name: String,
    val description: String?,
    val status: Boolean?,
    val image:String
)
