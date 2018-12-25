package com.lab.greenpremium.ui.screen.main.portfolio.sub

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
import com.lab.greenpremium.ui.screen.base.BaseFragment
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

            //TODO Refactoring
            for ((index, photo) in photos.withIndex()) {

                if (index == 0 || index % 3 == 0) {
                    //one big image
                    val view = ImageView(context)
                    val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                    layoutParams.width = getScreenWidth(context!!) - paddingMedium * 2
                    view.layoutParams = layoutParams

                    setTouchAnimationShrink(view)
                    Glide.with(context!!)
                            .load(photo.url)
                            .into(view)

                    container_grid.addView(view)

                } else {
                    //two small images
                    val inflater = layoutInflater
                    val container = inflater.inflate(R.layout.layout_2_images, null)

                    container.findViewById<ImageView>(R.id.image_1)
                            .also { imageView ->
                                Glide.with(context!!)
                                        .load(photo.url)
                                        .into(imageView)
                                setTouchAnimationShrink(imageView)
                            }

                    container.findViewById<ImageView>(R.id.image_2)
                            .also { imageView ->
                                Glide.with(context!!)
                                        .load(photo.url)
                                        .into(imageView)
                                setTouchAnimationShrink(imageView)
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
            }

        }
    }

}