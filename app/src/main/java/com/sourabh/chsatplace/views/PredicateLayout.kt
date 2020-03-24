package com.sourabh.chsatplace.views

import android.R.layout
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup


internal class PredicateLayout : ViewGroup {

    private var line_height: Int = 0

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        assert(MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED)

        val width = MeasureSpec.getSize(widthMeasureSpec)

        // The next line is WRONG!!! Doesn't take into account requested MeasureSpec mode!
        var height = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        val count = childCount
        var line_height = 0

        var xpos = paddingLeft
        var ypos = paddingTop

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility !== View.GONE) {
                val lp = child.layoutParams as ViewGroup.LayoutParams
                child.measure(
                    MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED)
                )

                val childw = child.measuredWidth
                line_height = Math.max(line_height, child.measuredHeight + lp.height)

                if (xpos + childw > width) {
                    xpos = paddingLeft
                    ypos += line_height
                }

                xpos += childw + lp.width
            }
        }
        this.line_height = line_height

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.UNSPECIFIED) {
            height = ypos + line_height

        } else if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            if (ypos + line_height < height) {
                height = ypos + line_height
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(1, 1) // default of 1px spacing
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is ViewGroup.LayoutParams
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = r - l
        var xpos = paddingLeft
        var ypos = paddingTop

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility !== View.GONE) {
                val childw = child.measuredWidth
                val childh = child.measuredHeight
                val lp = child.layoutParams as ViewGroup.LayoutParams
                if (xpos + childw > width) {
                    xpos = paddingLeft
                    ypos += line_height
                }
                child.layout(xpos, ypos, xpos + childw, ypos + childh)
                xpos += childw + lp.width
            }
        }
    }
}