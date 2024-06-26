package com.duyle.tutorkot104.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duyle.tutorkot104.data.entity.SanPham
import com.duyle.tutorkot104.data.dao.SanPhamDao

@Database(entities = [SanPham::class], version = 2, exportSchema = false)
abstract class SanPhamDatabase : RoomDatabase() {
    abstract fun sanPhamDao(): SanPhamDao

    companion object {
        @Volatile
        private var INSTANCE: SanPhamDatabase? = null

        fun getDatabase(context: Context): SanPhamDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SanPhamDatabase::class.java,
                    "sanpham_database"
                )
                    .fallbackToDestructiveMigration()  // Thêm dòng này để phá hủy và tạo lại cơ sở dữ liệu khi cần
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
