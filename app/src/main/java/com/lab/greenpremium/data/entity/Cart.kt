package com.lab.greenpremium.data.entity


//get /cart/info - пустой запрос
//post /cart/order/add - пустой запрос
//post /cart/add
data class AddToCartRequest(val product_id: Int, val quantity: Int)
data class CartResponse(val products: List<Product>, val total_quantity: Int)
data class MakeOrderResponse(val title: String, val message: String)


//get /order/{order_id}
data class OrderRequest(val order_id: Int)
data class OrderResponse(val products: List<Product>)