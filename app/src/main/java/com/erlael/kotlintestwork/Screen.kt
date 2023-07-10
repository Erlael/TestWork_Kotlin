package com.erlael.kotlintestwork


sealed class Screen(val route: String) {
    object ProductList : Screen(route = "product_list")
    object CreateProduct : Screen(route = "create_product")
}