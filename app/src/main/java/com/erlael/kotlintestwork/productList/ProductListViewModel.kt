package com.erlael.kotlintestwork.productList


import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.erlael.kotlintestwork.Screen
import com.erlael.kotlintestwork.data.Product
import com.erlael.kotlintestwork.data.ProductDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val dao: ProductDao
) : ViewModel() {

    private val _menuPopup = MutableStateFlow(false)
    val menuPopup = _menuPopup.asStateFlow()

    private val _sortFilter = MutableStateFlow("id")

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _products = _sortFilter
        .flatMapLatest { sortFilter ->
            when (sortFilter) { // Свитч по колонкам базы
                Product::id.name -> dao.getProductsOrderById()
                Product::name.name -> dao.getProductsOrderByName()
                Product::price.name -> dao.getProductsOrderByPrice()
                Product::price.name -> dao.getProductsOrderByCount()
                else -> dao.getProductsOrderById()
            }
        }

    val products = searchText
        .debounce(80L)
        .combine(_products) { text, products ->
            if (text.isBlank()) {
                products
            } else {
                products.filter {
                    it.name.lowercase().contains(text.lowercase())
                }.sortedBy {
                    it.name.lowercase().indexOf(text.lowercase())
                }
            }
        }

    fun onPopupToggle() {
        _menuPopup.value = !_menuPopup.value
    }

    fun onFilterClicked(filter: String) {
        _sortFilter.value = filter
    }

    fun onSearchTextEdit(text: String) {
        _searchText.value = text
    }

    fun onCreateNewProduct(navHostController: NavHostController) {
        navHostController.navigate(route = Screen.CreateProduct.route)
    }


}
