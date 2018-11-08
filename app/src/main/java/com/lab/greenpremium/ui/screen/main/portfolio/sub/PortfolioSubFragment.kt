package com.lab.greenpremium.ui.screen.main.portfolio.sub

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.lab.greenpremium.App
import com.lab.greenpremium.KEY_OBJECT
import com.lab.greenpremium.R
import com.lab.greenpremium.data.entity.raw.Image
import com.lab.greenpremium.ui.screen.base.BaseFragment
import com.lab.greenpremium.ui.screen.main.portfolio.PortfolioType
import com.lab.greenpremium.utills.getMockImageList
import com.lab.greenpremium.utills.getScreenWidth
import com.lab.greenpremium.utills.setTouchAnimationShrink
import kotlinx.android.synthetic.main.sub_fragment_portfolio.*
import javax.inject.Inject


class PortfolioSubFragment : BaseFragment(), PortfolioSubContract.View {

    @Inject
    internal lateinit var presenter: PortfolioSubPresenter

    lateinit var type: PortfolioType
    private var paddingMedium = 0
    private var paddingSmall = 0

    companion object {
        fun newInstance(type: Int): PortfolioSubFragment {
            val fragment = PortfolioSubFragment()
            val args = Bundle()
            args.putInt(KEY_OBJECT, type)
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
            paddingMedium = it.resources.getDimensionPixelSize(R.dimen.space_medium_2)
            paddingSmall = it.resources.getDimensionPixelSize(R.dimen.space_small_3)
        }

        type = PortfolioType.values()[arguments!!.getInt(KEY_OBJECT)]

        val list = getMockImageList(12)

        if (list.size % 3 == 0) {

            var i = 0
            while (i < list.size) {

                if (i == 0) {
                    container_grid.addView(getOneBigImage(list[i]))
                } else {
                    container_grid.addView(getTwoSmallImagesInContainer(list[i - 2], list[i - 1]))
                    container_grid.addView(getOneBigImage(list[i]))
                }

                i += 3
            }


            (container_grid.getChildAt(container_grid.childCount - 1).layoutParams as ViewGroup.MarginLayoutParams).setMargins(0, 0, 0, paddingMedium * 3)


        } else {
            //TODO implement
        }
    }

    private fun getOneBigImage(image: Image): View {
        val view = ImageView(context)
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        view.layoutParams = layoutParams

        context?.let {
            initImage(it, view, image, true)
        }
        return view
    }

    private fun getTwoSmallImagesInContainer(image1: Image, image2: Image): View {
        val inflater = layoutInflater
        val container = inflater.inflate(R.layout.layout_2_images, null)

        val imageView1 = container.findViewById<ImageView>(R.id.image_1)
        val imageView2 = container.findViewById<ImageView>(R.id.image_2)

        context?.let {
            initImage(it, imageView1, image1, false)
            initImage(it, imageView2, image2, false)
        }

        return container
    }

    private fun initImage(context: Context, imageView: ImageView, image: Image, isBig: Boolean) {

        if (isBig) {
            imageView.layoutParams.width = getScreenWidth(context) - paddingMedium * 2
            imageView.layoutParams.height = getScreenWidth(context) - paddingMedium * 2
        }

        Glide.with(context)
                .load(image.url)
                .into(imageView)

        setTouchAnimationShrink(imageView)
    }
}