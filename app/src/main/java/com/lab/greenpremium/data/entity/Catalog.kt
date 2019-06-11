package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SectionProductListRequest(@SerializedName("section_id") val section_id: Int)
data class ProductRequest(@SerializedName("product_id") val product_id: Int)

data class CatalogSectionsResponse(@SerializedName("sections") val sections: MutableList<Section>?) : Serializable {
    val time: Long = System.currentTimeMillis()
}

data class Section(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("sort") val sort: String) : Serializable {

    @SerializedName("products") var products: MutableList<Product>? = null
}

data class SectionProductsResponse(val products: MutableList<Product>) {
    val time: Long = System.currentTimeMillis()
    var page: Int = 1 // first page as default
}

data class Product(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("sort") val sort: String,
        @SerializedName("detail_text") val detail_text: String,
        @SerializedName("offers") var offers: List<Offer>,
        @SerializedName("gallery") val gallery: List<Photo>,
        @SerializedName("photo") val photo: Photo,
        @SerializedName("quantity") var quantity: Int,
        @SerializedName("isFavorite") var isFavorite: Boolean = false
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
        @SerializedName("product_id") var product_id: Int,
        @SerializedName("price") var price: Double,
        @SerializedName("old_price") var old_price: Double?,

        //Только у крупномеров
        @SerializedName("height") var height: OfferParam,
        @SerializedName("crown_width") var crown_width: OfferParam,

        //У остальных
        @SerializedName("item_height") var item_height: OfferParam,
        @SerializedName("plant_size") var plant_size: OfferParam,
        @SerializedName("pot_count") var pot_count: OfferParam,
        @SerializedName("pot_size") var pot_size: OfferParam,

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
        @SerializedName("name") val name: String,
        @SerializedName("value") val value: String
) : Serializable {

    override fun toString(): String {
        return "$name $value"
    }
}
