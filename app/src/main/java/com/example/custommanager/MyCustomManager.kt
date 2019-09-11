package com.example.custommanager

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.State
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import kotlin.math.floor


class MyCustomManager(resources: Resources, private val screenWidth: Int) :
  RecyclerView.LayoutManager() {

  private var horizontalScrollOffset: Int = 0
  private var viewWidth: Int = resources.getDimensionPixelSize(R.dimen.item_width)
  private val recyclerViewHeight =
    (resources.getDimensionPixelSize(R.dimen.recycler_view_height)).toDouble()

  override fun generateDefaultLayoutParams(): LayoutParams =
    LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

  override fun canScrollHorizontally(): Boolean {
    return true
  }

  override fun scrollHorizontallyBy(dx: Int, recycler: Recycler?, state: State?): Int {
    horizontalScrollOffset += dx

    fill(recycler!!, state!!)
    return dx
  }

  override fun onLayoutChildren(recycler: Recycler?, state: State?) {
    fill(recycler!!, state!!)
  }

  private fun fill(recycler: Recycler, state: State) {
    detachAndScrapAttachedViews(recycler)

    for (i: Int in 0 until itemCount) {

      val firstVisiblePosition =
        floor(horizontalScrollOffset.toDouble() / viewWidth.toDouble()).toInt()
      val lastVisiblePosition = (horizontalScrollOffset + screenWidth) / viewWidth

      for (index in firstVisiblePosition..lastVisiblePosition) {
        var recyclerIndex = index % itemCount
        if (recyclerIndex < 0) {
          recyclerIndex += itemCount
        }
        val view = recycler.getViewForPosition(recyclerIndex)
        addView(view)

        layoutChildView(index, viewWidth, view)
      }

      val scrapListCopy: List<ViewHolder> = recycler.scrapList.toList()
      scrapListCopy.forEach {
        recycler.recycleView(it.itemView)
      }
    }
  }

  private fun layoutChildView(i: Int, viewWidthWithSpacing: Int, view: View) {
    val left = i * viewWidth - horizontalScrollOffset
    val right = left + viewWidth
    val top = 0
    val bottom = top + viewWidth
    measureChild(view, viewWidth, viewWidth)

    layoutDecorated(view, left, top, right, bottom)
  }

  /*private fun getTopOffsetForView(viewCentreX: Double): Int {
    val radius = screenWidth / 2
    val cosAlpha = (radius - viewCentreX) / radius
    val alpha = Math.acos(MathUtils.clamp(cosAlpha, -1.0, 1.0))
    val y = radius - (radius * Math.sin(alpha))
    return y.toInt()
  }*/
}

