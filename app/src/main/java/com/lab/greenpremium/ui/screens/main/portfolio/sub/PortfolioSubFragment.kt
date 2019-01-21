package com.lab.greenpremium.ui.screens.main.portfolio.sub

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.Photo
import com.lab.greenpremium.data.entity.PortfolioSection
import com.lab.greenpremium.data.entity.getPhotosUrls
import com.lab.greenpremium.ui.screens.base.BaseActivity
import com.lab.greenpremium.ui.screens.base.BaseFragment
import com.lab.greenpremium.utills.LogUtil
import com.lab.greenpremium.utills.getScreenWidth
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.sub_fragment_portfolio.*
import javax.inject.Inject


class PortfolioSubFragment : BaseFragment(), PortfolioSubContract.View {

    @Inject
    internal lateinit var presenter: PortfolioSubPresenter

    lateinit var photos: List<Photo>

    private var paddingMedium = 0
    private var paddingSmall = 0

    companion object {
        fun newInstance(portfolioSection: PortfolioSection): PortfolioSubFragment {
            val fragment = PortfolioSubFragment()
            val args = Bundle()
            args.putSerializable(KEY_OBJECT, portfolioSection)
            fragment.arguments = args
            return fragment
        }
    }

    override fun initializeDaggerComponent() {
        DaggerPortfolioSubComponent.builder()
                .appComponent((activity?.application as App).component)
                .portfolioSubModule(PortfolioSubModule(this))
                .build()
                .inject(this)
    }

    override fun layoutResId(): Int {
        return R.layout.sub_fragment_portfolio
    }

    override fun initViews() {
        context?.let {
            paddingMedium = it.resources.getDimensionPixelSize(R.dimen.space_24)
            paddingSmall = it.resources.getDimensionPixelSize(R.dimen.space_12)

            photos = (arguments!!.getSerializable(KEY_OBJECT) as PortfolioSection).photos

            initializeImageGrid()

        }
    }

    private fun initializeImageGrid() {
        val total = photos.size
        val columns = 2
        val rows = photos.size / 3 * 2

        LogUtil.e("GRID: total: $total, columns: $columns, rows: $rows")

        container_grid.columnCount = columns
        container_grid.rowCount = rows

        var rowCounter = 1
        var colCounter = 1

        for ((index, photo) in photos.withIndex()) {

            if (index == 0 || index % 3 == 0) {
                //big image

            } else {
                //two small images

            }

            val image = ImageView(context)
            setImage(photo, image)
            container_grid.addView(image)

            rowCounter++
            colCounter++
        }
    }

    private fun setImage(photo: Photo, view: ImageView) {
        view.setOnClickListener { presenter.onClickImage(photos.indexOf(photo)) }
        setTouchAnimationShrink(view)
        Glide.with(context!!)
                .load(photo.url)
                .into(view)
    }

    override fun goToGalleryScreen(pos: Int) {
        (activity as BaseActivity).goToGalleryScreen(getPhotosUrls(photos), pos)
    }

    /*
    *                 if (index == 0 || index % 3 == 0) {
                    //one big image
                    val view = ImageView(context)
                    val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams.width = getScreenWidth(context!!) - paddingMedium * 2
                    view.layoutParams = layoutParams

                    setImage(photo, view)

                    container_grid.addView(view)

                } else {
                    //TODO FIX: Дублируются фотки
                    //two small images
                    val inflater = layoutInflater
                    val container = inflater.inflate(R.layout.layout_2_images, null)

                    container.findViewById<ImageView>(R.id.image_1)
                            .also { imageView ->
                                setImage(photo, imageView)
                            }
                    if (index != photos.lastIndex) {
                        container.findViewById<ImageView>(R.id.image_2)
                                .also { imageView ->
                                    setImage(photos[index + 1], imageView)
                                }
                    }

                    container_grid.addView(container)
                }

                //set margins to images
                (container_grid.getChildAt(index).layoutParams as ViewGroup.MarginLayoutParams)
                        .setMargins(
                                0,
                                paddingMedium,
                                0,
                                if (index == photos.lastIndex) paddingMedium * 3 else 0
                        )
    * */
}