package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


//get /cart/info - пустой запрос
//post /cart/order/add - пустой запрос
//post /cart/add
data class AddToCartRequest(@SerializedName("product_id") val product_id: Int,
                            @SerializedName("quantity") val quantity: Int)

data class CartResponse(@SerializedName("added_product") val added_product: Product,
                        @SerializedName("products") val products: MutableList<Product>,
                        @SerializedName("total_quantity") val total_quantity: Int,
                        @SerializedName("service_text") val service_text: String)

data class MakeOrderResponse(@SerializedName("title") val title: String,
                             @SerializedName("message") val message: String) : Serializable


//get /order/{order_id}
data class OrderRequest(@SerializedName("order_id") val order_id: Int)

data class OrderResponse(@SerializedName("order_price") val order_price: OrderPrice,
                         @SerializedName("products") val products: List<Product>)

data class OrderPrice(
    @SerializedName("nds") val nds: Double,
    @SerializedName("nds_f") val nds_f: String,
    @SerializedName("nds_string") val nds_string: String,
    @SerializedName("sum") val sum: Int,
    @SerializedName("sum_f") val sum_f: String,
    @SerializedName("sum_string") val sum_string: String
)