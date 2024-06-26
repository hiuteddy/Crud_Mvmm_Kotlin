package com.duyle.tutorkot104.viewmodel


import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.asLiveData
import com.duyle.tutorkot104.data.database.SanPhamDatabase
import com.duyle.tutorkot104.data.entity.SanPham
import com.duyle.tutorkot104.data.entity.SanPhamRepository
import kotlinx.coroutines.launch

class SanPhamViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SanPhamRepository
    val allSanPhams: LiveData<List<SanPham>>

    init {
        val sanPhamDao = SanPhamDatabase.getDatabase(application).sanPhamDao()
        repository = SanPhamRepository(sanPhamDao)
        allSanPhams = repository.allSanPhams.asLiveData()
    }

    fun insert(sanPham: SanPham) = viewModelScope.launch {
        repository.insert(sanPham)
    }

    fun update(sanPham: SanPham) = viewModelScope.launch {
        repository.update(sanPham)
    }

    fun delete(sanPham: SanPham) = viewModelScope.launch {
        repository.delete(sanPham)
    }
}

class SanPhamViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SanPhamViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SanPhamViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
