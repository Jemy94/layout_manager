package com.example.custommanager

import android.graphics.Point
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.recycler_view

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val myList: List<String> = listOf(
      "banana", "apple", "pear", "strawberry", "cherry", "plum",
      "orange", "kiwi", "kumquat", "wolfberry", "dragonfruit"
    )

    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    val screenWidth = size.x

    recycler_view.layoutParams.height = screenWidth / 2
    recycler_view.layoutManager = MyCustomManager(resources, screenWidth)
    recycler_view.adapter = MyAdapter(myList)
  }
}
