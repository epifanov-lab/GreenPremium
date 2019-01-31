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
        var offers: List<Offer>,
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

    fun getGalleryUrlsList(): ArrayList<String> {
        val result = arrayListOf<String>()
        gallery.forEach { result.add(it.url) }
        return result
    }

    fun changeFavoriteState() {
        isFavorite = !isFavorite
    }

}

data class Offer(
        var product_id: Int,
        var price: Double,
        var old_price: Double?,

        //Только у крупномеров
        var height: OfferParam,
        var crown_width: OfferParam,

        //У остальных
        var item_height: OfferParam,
        var plant_size: OfferParam,
        var pot_count: OfferParam,
        var pot_size: OfferParam,

        var quantity: Int = 0
) : Serializable {

    fun getParams(): Array<OfferParam?> {
        return arrayOf(height, crown_width,
                item_height, plant_size,
                pot_count, pot_size)
    }

    fun sync(other: Offer) {
        this.product_id = other.product_id
        this.price = other.price
        this.old_price = other.old_price
        this.height = other.height
        this.crown_width = other.crown_width
        this.item_height = other.item_height
        this.plant_size = other.plant_size
        this.pot_count = other.pot_count
        this.pot_size = other.pot_size
        this.quantity = other.quantity
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
