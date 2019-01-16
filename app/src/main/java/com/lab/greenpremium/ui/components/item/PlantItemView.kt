package com.lab.greenpremium.ui.components.item

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Offer
import com.lab.greenpremium.data.entity.Product
import com.lab.greenpremium.utills.currencyFormat
import com.lab.greenpremium.utills.eventbus.ProductQuantityChangedEvent
import kotlinx.android.synthetic.main.view_item_plant.view.*
import org.greenrobot.eventbus.EventBus


class PlantItemView : RelativeLayout {

    enum class PlantViewType {
        CATALOG, // Есть возможность выбора высоты, в случае, если несколько офферов.
        OTHER // Без выбора высоты, только текстовые поля с информацией по растению
    }

    private lateinit var product: Product
    private lateinit var offer: Offer
    lateinit var type: PlantViewType

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_item_plant, this, true)
    }

    fun setData(product: Product, type: PlantViewType) {
        this.product = product
        this.offer = product.offers[0]
        this.type = type

        text_name.text = product.name

        product.photo.url?.let {
            Glide.with(context)
                    .load(it)
                    .into(image)
        }

        updateViewByType()

        PlantItemCountControlsHelper(product, text_counter, button_add, button_remove)
    }

    private fun updateViewByType() {
        showHeightSelector(false)
        val isLargePlant = offer.height != null && offer.crown_width != null
        val isStandartPlant = offer.plant_size != null && offer.item_height != null && offer.pot_count != null && offer.pot_size != null

        text_info_1.text = when {
            isLargePlant -> context.getString(R.string.template_s_s, offer.crown_width.name, offer.crown_width.value)
            isStandartPlant -> context.getString(R.string.template_s_s, offer.plant_size.name, offer.plant_size.value)
            else -> ""
        }


        text_info_2.text =
                when {
                    isLargePlant -> when (type) {
                        PlantViewType.CATALOG -> when (product.offers.size > 1) {
                            true -> context.getText(R.string.title_height).also { showHeightSelector(true) }
                            false -> context.getString(R.string.template_s_s, offer.height.name, offer.height.value)
                        }
                        PlantViewType.OTHER -> context.getString(R.string.template_s_s, offer.height.name, offer.height.value)
                    }
                    isStandartPlant -> context.getString(R.string.template_s_s, offer.item_height.name, offer.item_height.value)
                    else -> ""
                }

        text_price.text = currencyFormat(offer.price)

        offer.old_price?.let {
            text_discount.visibility = View.VISIBLE
            text_discount.text = currencyFormat(it)
        }

        container_controls.visibility = if (type == PlantViewType.CATALOG) View.VISIBLE else View.GONE
    }

    private fun showHeightSelector(enabled: Boolean) {
        height_selector.visibility = if (enabled) View.VISIBLE else View.GONE
        space.visibility = if (enabled) View.VISIBLE else View.GONE
        if (enabled) height_selector.text = "${offer.height.value}м"
    }

    fun setMargins(left: Int, top: Int, right: Int, bottom: Int) {
        val p = LinearLayout.LayoutParams(LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        p.setMargins(left, top, right, bottom)
        this.layoutParams = p
        this.requestLayout()
    }

    override fun setOnClickListener(onClickListener: OnClickListener) {
        container_main.setOnClickListener(onClickListener)
    }

    override fun setOnTouchListener(touchListener: OnTouchListener?) {
        container_main.setOnTouchListener(touchListener)
    }
}

class PlantItemCountControlsHelper(val product: Product,
                                   val counter: TextView,
                                   val add: View,
                                   val remove: View) {


    val repeatUpdateHandler = Handler()
    var isIncrementing = false
    var isDecrementing = false

    init {

        add.run {
            setOnClickListener { setCounter(product.quantity + 1) }
            setOnLongClickListener {
                isIncrementing = true
                repeatUpdateHandler.post(RptUpdater())
                false
            }

            setOnTouchListener { _, event ->
                if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) && isIncrementing) {
                    isIncrementing = false
                }
                false
            }
        }

        remove.run {
            setOnClickListener { setCounter(product.quantity - 1) }
            setOnLongClickListener {
                isDecrementing = true
                repeatUpdateHandler.post(RptUpdater())
                false
            }

            setOnTouchListener { _, event ->
                if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) && isDecrementing) {
                    isDecrementing = false
                }
                false
            }
        }

        setCounter(product.quantity)
    }

    private fun setCounter(n: Int) {
        if (n >= 0 && product.quantity != n) {
            product.quantity = n
            EventBus.getDefault().post(ProductQuantityChangedEvent(product))
        }

        counter.text = product.quantity.toString()
    }

    inner class RptUpdater : Runnable {
        override fun run() {
            if (isIncrementing) {
                setCounter(product.quantity + 1)
                repeatUpdateHandler.postDelayed(RptUpdater(), 100)

            } else if (isDecrementing) {
                setCounter(product.quantity - 1)
                repeatUpdateHandler.postDelayed(RptUpdater(), 100)
            }
        }
    }
}