package com.example.gbsb.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TodayScheduleDecorator(private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        // Set the top margin for all items except the first one
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = verticalSpacing
        }
    }
}