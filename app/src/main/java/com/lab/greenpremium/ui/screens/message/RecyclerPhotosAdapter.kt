package com.lab.greenpremium.ui.screens.message

import android.content.Context
import android.graphics.Rect
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.lab.greenpremium.R
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.view_added_photo.view.*

interface AddPhotoViewListener {
    fun onClickPhoto(index: Int)
    fun onClickDelete(index: Int)
    fun onClickAdd()
}

class RecyclerPhotosAdapter(private val photoViewSize: Int,
                            private val listener: AddPhotoViewListener?)
    : RecyclerView.Adapter<RecyclerPhotosAdapter.ViewHolder>() {

    val photos: MutableList<Uri> = ArrayList()

    init {
        photos.add(Uri.EMPTY)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AddedPhotoView(parent.context)
        view.layoutParams = ViewGroup.LayoutParams(photoViewSize, photoViewSize)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setData(photos[position], position)
        holder.view.setClickListener(listener)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    fun addItem(uri: Uri?) {
        uri?.let { this.photos.add(0,uri) }
        notifyDataSetChanged()
    }

    fun addItems(list: MutableList<Uri>?) {
        list?.let {
            this.photos.addAll(0, list)
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): Uri {
        return photos[position]
    }


    fun removeItem(position: Int) {
        photos.removeAt(position)
        notifyDataSetChanged()
    }

    class ViewHolder(val view: AddedPhotoView) : RecyclerView.ViewHolder(view)

    class AddedPhotoView : RelativeLayout {

        constructor(context: Context) : this(context, null)

        constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
            LayoutInflater.from(context).inflate(R.layout.view_added_photo, this, true)
        }

        private var photoUri: Uri? = null
        var position: Int = 0

        fun setData(uri: Uri, position: Int) {
            this.photoUri = uri
            this.position = position
            initialize()
        }

        private fun initialize() {
            val isAddButtonTypeItem = photoUri?.toString().isNullOrEmpty()

            view_photo.visibility = if (isAddButtonTypeItem) View.GONE else View.VISIBLE
            button_cancel.visibility = if (isAddButtonTypeItem) View.GONE else View.VISIBLE
            button_add_photo.visibility = if (isAddButtonTypeItem) View.VISIBLE else View.GONE

            if (!isAddButtonTypeItem) {
                Glide.with(context)
                        .load(photoUri)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(view_photo)
            }

        }

        fun setClickListener(listener: AddPhotoViewListener?) {
            listener?.let {
                view_photo.setOnClickListener { listener.onClickPhoto(position) }
                button_cancel.setOnClickListener { listener.onClickDelete(position) }
                button_add_photo.setOnClickListener { listener.onClickAdd() }

                setTouchAnimationShrink(view_photo)
                setTouchAnimationShrink(button_add_photo)
            }
        }
    }

    class MarginItemDecoration(private val spaceSize: Int,
                               private val toTop: Boolean = false,
                               private val toRight: Boolean = false,
                               private val toBottom: Boolean = false,
                               private  val toLeft: Boolean = false) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            with(outRect) {
                if (parent.getChildAdapterPosition(view) == 0 && toTop) top = spaceSize
                if (toRight) left =  spaceSize
                if (toBottom) right = spaceSize
                if (toLeft) bottom = spaceSize
            }
        }

    }
}