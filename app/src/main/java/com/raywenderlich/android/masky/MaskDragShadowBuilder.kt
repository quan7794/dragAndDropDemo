package com.raywenderlich.android.masky

import android.graphics.Canvas
import android.graphics.Point
import android.view.View
import androidx.core.content.res.ResourcesCompat


class MaskDragShadowBuilder(view: View) : View.DragShadowBuilder(view) {

    //1
    private val shadow = ResourcesCompat.getDrawable(view.context.resources, R.drawable.ic_mask, view.context.theme)

    // 2
    override fun onProvideShadowMetrics(size: Point, touch: Point) {
        // 3
        val width: Int = view.width

        // 4
        val height: Int = view.height

        // 5
        shadow?.setBounds(0, 0, width, height)

        // 6
        size.set(width, height)

        // 7
        touch.set(width / 2, height / 2)
    }

    // 8
    override fun onDrawShadow(canvas: Canvas) {
        // 9
        shadow?.draw(canvas)
    }
}