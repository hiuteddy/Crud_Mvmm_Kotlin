package com.duyle.tutorkot104.data.dao


import androidx.room.*
import com.duyle.tutorkot104.data.entity.SanPham
import kotlinx.coroutines.flow.Flow

@Dao
interface SanPhamDao {
    @Query("SELECT * FROM sanpham_table")
    fun getAllSanPhams(): Flow<List<SanPham>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sanPham: SanPham)

    @Update
    suspend fun update(sanPham: SanPham)

    @Delete
    suspend fun delete(sanPham: SanPham)
}
