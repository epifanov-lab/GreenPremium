package com.lab.greenpremium.data.entity

import java.io.Serializable

data class SectionProductListRequest(val section_id: Int)
data class ProductRequest(val product_id: Int)

data class CatalogSectionsResponse(val sections: MutableList<Section>?) : Serializable {
    val time: Long = System.currentTimeMillis()
}

data class Section(
        val id: Int,
        val name: String,
        val sort: String) : Serializable {

    var products: MutableList<Product>? = null
}

data class SectionProductsResponse(val products: MutableList<Product>) {
    val time: Long = System.currentTimeMillis()
    var page: Int = 1 // first page as default
}

data class Product(
        val id: Int,
        val name: String,
        val sort: String,
        val detail_text: String,
        val offers: List<Offer>,
        val gallery: List<Photo>,
        val photo: Photo,
        var quantity: Int,

        var isFavorite: Boolean = false

) : Serializable {

    interface Listener {
        fun onSelectedOfferPositionChanged(position: Int)
    }

    var listener: Listener? = null

    var selectedOfferPosition: Int = 0
        set (value) {
            field = value
            listener?.onSelectedOfferPositionChanged(value)
        }

    fun getSelectedOffer(): Offer {
        return offers[selectedOfferPosition]
    }

    fun changeFavoriteState() {
        isFavorite = !isFavorite
    }

    fun getGalleryUrlsList(): ArrayList<String> {
        val result = arrayListOf<String>()
        gallery.forEach { result.add(it.url) }
        return result
    }

}

data class Offer(
        val product_id: Int,
        val price: Double,
        val old_price: Double?,

        //Только у крупномеров
        val height: OfferParam,
        val crown_width: OfferParam,

        //У остальных
        val item_height: OfferParam,
        val plant_size: OfferParam,
        val pot_count: OfferParam,
        val pot_size: OfferParam,

        var quantity: Int = 0
) : Serializable {

    fun getParams(): Array<OfferParam?> {
        return arrayOf(height, crown_width,
                item_height, plant_size,
                pot_count, pot_size)
    }
}

data class OfferParam(
        val name: String,
        val value: String
) : Serializable {

    override fun toString(): String {
        return "$name $value"
    }
}
