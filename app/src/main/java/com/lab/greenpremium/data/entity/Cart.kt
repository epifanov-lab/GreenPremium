package com.lab.greenpremium.data.entity


//get /order/{order_id}
data class OrderRequest(val order_id: Int)
data class OrderResponse(val products: List<Product>)
