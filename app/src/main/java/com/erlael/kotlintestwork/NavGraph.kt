package com.erlael.kotlintestwork


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erlael.kotlintestwork.createProduct.CreateProductScreen
import com.erlael.kotlintestwork.createProduct.CreateProductViewModel
import com.erlael.kotlintestwork.productList.ProductListScreen
import com.erlael.kotlintestwork.productList.ProductListViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.ProductList.route) {

        composable(route = Screen.ProductList.route) {
           val viewModel = hiltViewModel<ProductListViewModel>()
            ProductListScreen(viewModel, navController)
        }

        composable(route = Screen.CreateProduct.route) {
            val viewModel = hiltViewModel<CreateProductViewModel>()
            CreateProductScreen(viewModel, navController)
        }

    }
}