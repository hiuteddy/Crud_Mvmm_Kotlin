package com.duyle.tutorkot104.ui.theme.view

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.duyle.tutorkot104.data.entity.SanPham

import com.duyle.tutorkot104.viewmodel.SanPhamViewModel
import com.duyle.tutorkot104.viewmodel.SanPhamViewModelFactory
import java.text.Normalizer
import java.util.regex.Pattern

var sanPhamTemp: SanPham =
    SanPham(name = "", price = 0f, description = "", status = false, image = "")


// Hàm chuyển đổi chuỗi có dấu thành không dấu
fun removeAccents(input: String): String {
    val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
    val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
    return pattern.matcher(normalized).replaceAll("")
}

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val viewModel: SanPhamViewModel =
        viewModel(factory = SanPhamViewModelFactory(context.applicationContext as Application))
    val sanPhams by viewModel.allSanPhams.observeAsState(emptyList())

    var filteredSanPhams by remember { mutableStateOf<List<SanPham>>(emptyList()) }
    var searchKeyword by remember { mutableStateOf("") }

    var showDialogItemInfor by remember { mutableStateOf(false) }
    var sp: SanPham? by remember { mutableStateOf(null) }
    var showDialogXoaSp by remember { mutableStateOf(false) }
    var showDialogThemSanPham by remember { mutableStateOf(false) }
    var showDialogSuaSanPham by remember { mutableStateOf(false) }

    if (showDialogItemInfor) {
        ShowDialoginfo(
            title = "Thông tin SP",
            sanPham = sp ?: SanPham(
                name = "",
                price = 0f,
                description = "",
                status = false,
                image = ""
            ),
            onDismiss = { showDialogItemInfor = false },
            onConfirm = { showDialogItemInfor = false })
    }

    if (showDialogXoaSp) {
        ShowDialog(
            title = "Xóa SP",
            content = "Bạn có chắc chắn xóa sản phẩm?",
            onDismiss = { showDialogXoaSp = false },
            onConfirm = {
                sp?.let { viewModel.delete(it) }
                showDialogXoaSp = false
            })
    }

    if (showDialogThemSanPham) {
        ShowDialogThemSuaSP(
            title = "Thêm SP",
            onDismiss = { showDialogThemSanPham = false },
            onConfirm = {
                viewModel.insert(sanPhamTemp.copy())
                showDialogThemSanPham = false
                sanPhamTemp = SanPham(name = "", price = 0f, description = "", status = false, image = "")

            })
    }

    if (showDialogSuaSanPham) {
        ShowDialogThemSuaSP(title = "Sửa SP",
            sanPham = sp,
            onDismiss = { showDialogSuaSanPham = false },
            onConfirm = {
                viewModel.update(sanPhamTemp.copy())
                showDialogSuaSanPham = false
                sanPhamTemp = SanPham(name = "", price = 0f, description = "", status = false, image = "")

            })
    }



    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp) // Điều chỉnh khoảng cách padding tổng thể cho Column
    ) {
        Text(
            text = "Quản lý sản phẩm",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = searchKeyword,
            onValueChange = { searchKeyword = it },
            label = { Text("Tìm kiếm sản phẩm") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Box(Modifier.fillMaxSize().padding(top = 16.dp)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(if (searchKeyword.isNotEmpty()) filteredSanPhams else sanPhams) { item ->
                    Column(
                        Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .padding(5.dp)

                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .clickable {
                                    sp = item
                                    showDialogItemInfor = true
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = item.image,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(5.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                ItemText(content = "Tên: ${item.name}")
                                ItemText(content = "Giá: ${item.price}")
                                ItemText(content = "Mô tả: ${item.description}")
                                ItemText(content = "Trạng thái: ${if (item.status == true) "Còn" else "Hết"}")
                            }
                        }
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(top = 1.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = {
                                sp = item.copy()
                                sanPhamTemp = item.copy()
                                showDialogSuaSanPham = true
                            }) {
                                Text(
                                    text = "Update",
                                    color = Color.Blue,
                                )
                            }
                            TextButton(onClick = {
                                sp = item
                                showDialogXoaSp = true
                            }) {
                                Text(
                                    text = "Delete",
                                    color = Color.Red,
                                )
                            }
                        }
                    }
                    Divider(modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp))
                }
            }

            FloatingActionButton(
                onClick = { showDialogThemSanPham = true },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }

            DisposableEffect(searchKeyword) {
                if (searchKeyword.isNotEmpty()) {
                    val keyword = removeAccents(searchKeyword).lowercase()
                    filteredSanPhams = sanPhams.filter {
                        removeAccents(it.name).lowercase().contains(keyword)
                    }
                } else {
                    filteredSanPhams = emptyList()
                }
                onDispose { }
            }
        }
    }
}

@Composable
fun ShowDialogThemSuaSP(
    title: String,
    sanPham: SanPham? = null,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var name by remember { mutableStateOf(sanPham?.name ?: "") }
    var price by remember { mutableStateOf(sanPham?.price?.toString() ?: "") }
    var description by remember { mutableStateOf(sanPham?.description ?: "") }
    var status by remember { mutableStateOf(sanPham?.status ?: false) }
    var image by remember { mutableStateOf(sanPham?.image ?: "") }

    var nameError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    var imageError by remember { mutableStateOf(false) }


    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                nameError = name.isEmpty()
                priceError = price.isEmpty()
                descriptionError = description.isEmpty()
                imageError = image.isEmpty()


                if (!nameError && !priceError && !descriptionError && !imageError) {
                    sanPhamTemp = sanPhamTemp.copy(
                        name = name,
                        price = price.toFloat(),
                        description = description,
                        status = status,
                        image = image
                    )
                    onConfirm()
                }
            }) {
                Text(text = "Xác nhận")
            }
        },
        dismissButton = { Button(onClick = { onDismiss() }) { Text(text = "Hủy") } },
        title = { Text(text = title) },
        text = {
            Column {
                TextField(
                    value = image,
                    onValueChange = {
                        image = it
                        imageError = it.isEmpty()
                    },
                    label = { Text("Nhập link url") },
                    isError = imageError
                )
                if (imageError) {
                    Text(
                        text = "Ảnh sản phẩm không được để trống",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }


                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = it.isEmpty()
                    },
                    label = { Text("Tên sản phẩm") },
                    isError = nameError
                )
                if (nameError) {
                    Text(
                        text = "Tên sản phẩm không được để trống",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                TextField(
                    value = price,
                    onValueChange = {
                        price = it
                        priceError = it.isEmpty()
                    },
                    label = { Text("Giá sản phẩm") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    isError = priceError
                )
                if (priceError) {
                    Text(
                        text = "Giá sản phẩm không được để trống",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                TextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descriptionError = it.isEmpty()
                    },
                    label = { Text("Mô tả sản phẩm") },
                    isError = descriptionError
                )
                if (descriptionError) {
                    Text(
                        text = "Mô tả sản phẩm không được để trống",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = status, onCheckedChange = { status = it })
                    Text(text = "Trạng thái sản phẩm")
                }
            }
        })
}

@Composable
fun ShowDialog(
    title: String, content: String, onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        Button(onClick = { onConfirm() }) {
            Text(text = "Xác nhận")
        }
    }, dismissButton = {
        Button(onClick = { onDismiss() }) {
            Text(text = "Hủy")
        }
    }, title = { Text(text = title) },
        text = { Text(text = content) })
}

@Composable
fun ShowDialoginfo(
    title: String, sanPham: SanPham, onDismiss: () -> Unit, onConfirm: () -> Unit
) {
    AlertDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        Button(onClick = { onConfirm() }) {
            Text(text = "Xác nhận")
        }
    }, dismissButton = {
        Button(onClick = { onDismiss() }) {
            Text(text = "Hủy")
        }
    }, title = { Text(text = title) },
        text = {
            Column {
                AsyncImage(
                    model = sanPham.image,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(text = "Tên sản phẩm: ${sanPham.name}")
                Text(text = "Giá sản phẩm: ${sanPham.price}")
                Text(text = "Mô tả sản phẩm: ${sanPham.description ?: ""}")
                Text(text = "Trạng thái sản phẩm: ${sanPham.status}")
            }
        })
}

@Composable
fun ItemText(content: String) {
    Text(
        text = content, Modifier.padding(4.dp),  style = MaterialTheme.typography.labelSmall
    )
}

