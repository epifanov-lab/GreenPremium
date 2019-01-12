package com.lab.greenpremium.data.entity

import java.io.Serializable

data class SectionProductListRequest(val section_id: Int)
data class ProductRequest(val product_id: Int)

data class CatalogSectionsResponse(val sections: List<Section>?) : Serializable {
    val time: Long = System.currentTimeMillis()
}

data class Section(
        val id: Int,
        val name: String,
        val sort: String) : Serializable {

    var products: List<Product>? = null
}

data class SectionProductsResponse(val products: List<Product>) {
    val time: Long = System.currentTimeMillis()
}

data class Product(
        val id: Int,
        val name: String,
        val sort: String,
        val detail_text: String,
        val offers: List<Offer>,
        val gallery: List<Photo>,
        val photo: Photo,

        var selectedOfferPosition: Int = 0,
        var isFavorite: Boolean = false,
        var quantity: Int = 0
) : Serializable

data class Offer(
        val product_id: Int,
        val price: Double,
        val old_price: Double?,

        //Только у крупномеров
        val height: Height,
        val crown_width: CrownWidth,

        //У остальных
        val item_height: ItemHeight,
        val plant_size: PlantSize,
        val pot_count: PotCount,
        val pot_size: PotSize
) : Serializable

data class Height(
        val name: String,
        val value: String
) : Serializable

data class CrownWidth(
        val name: String,
        val value: String
) : Serializable

data class PlantSize(
        val name: String,
        val value: String
) : Serializable

data class PotCount(
        val name: String,
        val value: String
) : Serializable

data class ItemHeight(
        val name: String,
        val value: String
) : Serializable

data class PotSize(
        val name: String,
        val value: String
) : Serializable
