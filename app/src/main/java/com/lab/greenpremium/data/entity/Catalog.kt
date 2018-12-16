package com.lab.greenpremium.data.entity

/**
 * get /catalog/sections
 * auth = false
 * empty request
 * */
data class CatalogSectionsData(val sections: List<Section>) {
    val time: Long = System.currentTimeMillis()
}

data class Section(
        val id: String,
        val name: String,
        val sort: String) {

    var products: SectionProductsData? = null
}

/**
 * get /catalog/sections/{section_id}
 * auth = true
 * */
data class SectionProductsRequest(val section_id: Int)

data class SectionProductsData(val products: List<Product>) {
    val time: Long = System.currentTimeMillis()
}

data class Height(
        val name: String,
        val value: String
)

data class CrownWidth(
        val name: String,
        val value: String
)

/**
 * get /catalog/products/{product_id}
 * auth = true
 * */
data class ProductDetailRequest(val product_id: Int)

data class Product(
        val detail_text: String,
        val gallery: List<Gallery>,
        val id: String,
        val name: String,
        val offers: List<Offer>,
        val photo: Photo,
        val sort: String
)

data class Gallery(
        val height: String,
        val id: String,
        val url: String,
        val width: String
)

data class Offer(
        val crown_width: CrownWidth,
        val item_height: Height,
        val old_price: Any,
        val plant_size: PlantSize,
        val pot_count: PotCount,
        val pot_size: PotSize,
        val price: Int,
        val product_id: String
)

data class PlantSize(
        val name: String,
        val value: String
)

data class PotCount(
        val name: String,
        val value: String
)

data class PotSize(
        val name: String,
        val value: String
)
