package com.lab.greenpremium.utills

import com.lab.greenpremium.data.entity.raw.Image

fun getMockImageList(count: Int, size: Int = 600): List<Image> {
    val result: ArrayList<Image> = ArrayList()

    for (i in 1..count) {
        when {
            i % 2 == 0 -> result.add(Image(size, size, "https://picsum.photos/$size/$size/?random"))
            i % 3 == 0 -> result.add(Image(size, size, "https://picsum.photos/${size + 1}/${size + 1}/?random"))
            else -> result.add(Image(size, size, "https://picsum.photos/${size + 2}/${size + 2}/?random"))
        }
    }

    return result
}