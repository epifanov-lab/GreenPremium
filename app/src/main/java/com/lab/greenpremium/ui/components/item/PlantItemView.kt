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
import com.lab.greenpremium.utills.eventbus.PlantCountChangedEvent
import kotlinx.android.synthetic.main.view_item_plant.view.*
import org.greenrobot.eventbus.EventBus


class PlantItemView : RelativeLayout {

    private lateinit var product: Product
    private lateinit var chosenOffer: Offer
    private var forDeliveryScreen = false

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_item_plant, this, true)
    }

    fun setData(product: Product, forDeliveryScreen: Boolean = false) {
        this.forDeliveryScreen = forDeliveryScreen
        this.product = product
        product.offers?.let { this.chosenOffer = product.offers[0] }

        text_name.text = product.name

        if (!forDeliveryScreen) setupInfoBlock()
        else updateViewForDeliveryScreen()

        product.photo.url?.let {
            Glide.with(context)
                    .load(it)
                    .into(image)
        }

        PlantItemCountControlsHelper(product, text_counter, button_add, button_remove)

    }

    private fun setupInfoBlock() {
        //У крупномеров может быть несколько оферов, в отличии от остальных типов растений
        val isLargePlant = product.offers!!.size > 1

        if (isLargePlant) {
            text_info_1.text = context.getString(R.string.template_s_s, chosenOffer.crown_width.name, chosenOffer.crown_width.value)
            text_info_2.text = context.getText(R.string.title_height)
            showHeightSelector(true)
            //TODO INITIALIZE SELECTOR

        } else {
            text_info_1.text = context.getString(R.string.template_s_s, chosenOffer.pot_size.name, chosenOffer.pot_size.value)
            text_info_2.text = context.getString(R.string.template_s_s, chosenOffer.item_height.name, chosenOffer.item_height.value)
            showHeightSelector(false)
        }

        text_price.text = currencyFormat(chosenOffer.price)

        chosenOffer.old_price?.let {
            text_discount.visibility = View.VISIBLE
            text_discount.text = currencyFormat(chosenOffer.old_price)
        }
    }

    private fun showHeightSelector(enabled: Boolean) {
        height_selector.visibility = if (enabled) View.VISIBLE else View.GONE
        space.visibility = if (enabled) View.VISIBLE else View.GONE
        height_selector.text = "15 м" //TODO прибрать
    }

    private fun updateViewForDeliveryScreen() {
        showHeightSelector(false)
        button_add.visibility = View.INVISIBLE
        button_remove.visibility = View.INVISIBLE
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
            setOnClickListener { setCounter(++product.count) }
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
            setOnClickListener { setCounter(--product.count) }
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

        setCounter(product.count)
    }

    private fun setCounter(n: Int) {
        product.count = if (n < 0) 0 else n
        counter.text = product.count.toString()
        EventBus.getDefault().post(PlantCountChangedEvent())
    }

    inner class RptUpdater : Runnable {
        override fun run() {
            if (isIncrementing) {
                setCounter(++product.count)
                repeatUpdateHandler.postDelayed(RptUpdater(), 100)

            } else if (isDecrementing) {
                setCounter(--product.count)
                repeatUpdateHandler.postDelayed(RptUpdater(), 100)
            }
        }
    }
}