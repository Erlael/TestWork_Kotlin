package com.erlael.kotlintestwork.productList


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.erlael.kotlintestwork.data.Product


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(viewModel: ProductListViewModel, navHostController: NavHostController) {

    val searchText by viewModel.searchText.collectAsState()
    val products by viewModel.products.collectAsState(emptyList())
    val menuPopup by viewModel.menuPopup.collectAsState()

    Scaffold(
        floatingActionButton = { AddProductActionButton(viewModel, navHostController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchField(searchText, viewModel, menuPopup)
            ProductList(paddingValues, products)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchField(
    searchText: String,
    viewModel: ProductListViewModel,
    menuPopup: Boolean
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = searchText,
        leadingIcon = {
            FilterButton(viewModel)
            FilterDropDownMenu(menuPopup, viewModel)
        },
        onValueChange = { viewModel.onSearchTextEdit(it) }
    )
}

@Composable
private fun ProductList(
    paddingValues: PaddingValues,
    products: List<Product>
) {
     LazyColumn(
        contentPadding = paddingValues,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(products) { product ->
            ProductListItem(product)
        }
    }
}

@Composable
private fun FilterButton(viewModel: ProductListViewModel) {
    IconButton(onClick = { viewModel.onPopupToggle() }) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Sorting",
        )
    }
}

@Composable
private fun FilterDropDownMenu(
    menuPopup: Boolean,
    viewModel: ProductListViewModel
) {
     DropdownMenu(
        expanded = menuPopup,
        onDismissRequest = { viewModel.onPopupToggle() })
    {
        FilterMenuItem(viewModel, Product::id.name)
        FilterMenuItem(viewModel, Product::name.name)
        FilterMenuItem(viewModel, Product::price.name)
        FilterMenuItem(viewModel, Product::count.name)
    }
}

@Composable
private fun FilterMenuItem(viewModel: ProductListViewModel, filter: String) {
    Text(
        text = filter.replaceFirstChar(Char::titlecase),
        modifier = Modifier
            .padding(10.dp)
            .clickable { viewModel.onPopupToggle(); viewModel.onFilterClicked(filter) })
}

@Composable
private fun AddProductActionButton(
    viewModel: ProductListViewModel,
    navHostController: NavHostController
) {
    FloatingActionButton(onClick = {
        viewModel.onCreateNewProduct(navHostController)
    }) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
    }
}