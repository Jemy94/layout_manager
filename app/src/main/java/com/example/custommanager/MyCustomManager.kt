package com.example.custommanager

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.math.MathUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.State
import androidx.recyclerview.widget.RecyclerView.ViewHolder


class MyCustomManager(resources: Resources, private val screenWidth: Int) :
  RecyclerView.LayoutManager() {

  private var horizontalScrollOffset: Int = 0
  private var viewWidth: Int = resources.getDimensionPixelSize(R.dimen.item_width)

  override fun generateDefaultLayoutParams(): LayoutParams =
    LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

  override fun canScrollHorizontally(): Boolean {
    return true
  }

  override fun scrollHorizontallyBy(dx: Int, recycler: Recycler?, state: State?): Int {
    horizontalScrollOffset += dx

    fill(recycler!!)

    return dx
  }

  override fun onLayoutChildren(recycler: Recycler?, state: State?) {

  }

  private fun fill(recycler: Recycler) {
    detachAndScrapAttachedViews(recycler)

    for (i: Int in 0 until itemCount) {

      val left: Int = i * viewWidth - horizontalScrollOffset

      val right: Int = left + viewWidth

      val top = getTopOffsetForView(left.toDouble() + (viewWidth.toDouble() / 2))

      val bottom: Int = top + viewWidth

      val view: View = recycler.getViewForPosition(i)
      addView(view)

      measureChild(view, viewWidth, viewWidth)

      layoutDecorated(view, left, top, right, bottom)

      val scrapListCopy: List<ViewHolder> = recycler.scrapList.toList()
      scrapListCopy.forEach {
        recycler.recycleView(it.itemView)
      }
    }
  }

  private fun getTopOffsetForView(viewCentreX: Double): Int {
    val radius = screenWidth / 2
    val cosAlpha = (radius - viewCentreX) / radius
    val alpha = Math.acos(MathUtils.clamp(cosAlpha, -1.0, 1.0))
    val y = radius - (radius * Math.sin(alpha))
    return y.toInt()
  }
}

