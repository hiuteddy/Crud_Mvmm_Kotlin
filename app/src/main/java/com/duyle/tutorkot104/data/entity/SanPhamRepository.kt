
package com.duyle.tutorkot104.data.entity

import androidx.annotation.WorkerThread
import com.duyle.tutorkot104.data.entity.SanPham
import com.duyle.tutorkot104.data.dao.SanPhamDao
import kotlinx.coroutines.flow.Flow

class SanPhamRepository(private val sanPhamDao: SanPhamDao) {
    val allSanPhams: Flow<List<SanPham>> = sanPhamDao.getAllSanPhams()

    @WorkerThread
    suspend fun insert(sanPham: SanPham) {
        sanPhamDao.insert(sanPham)
    }

    @WorkerThread
    suspend fun update(sanPham: SanPham) {
        sanPhamDao.update(sanPham)
    }

    @WorkerThread
    suspend fun delete(sanPham: SanPham) {
        sanPhamDao.delete(sanPham)
    }
}
