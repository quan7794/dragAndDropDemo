package com.raywenderlich.android.recyclerDrag

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.raywenderlich.android.masky.R
import com.raywenderlich.android.model.RemoteItem

class DynamicGridLayoutManager {
    //Create a GridLayoutManager with allow to change the span count dynamically, based on RemoteItem attribute name spanCount
//        fun getDynamicGidLayoutManager(context: Context, adapter: RemoteAdapter) : GridLayoutManager {
//            return object : GridLayoutManager(context, 2) {
//                override fun getSpanSizeLookup(): SpanSizeLookup {
//                    return object : SpanSizeLookup() {
//                        override fun getSpanSize(position: Int): Int {
//                            return when (adapter.getItemViewType(position)) {
//                                R.layout.item -> {
//                                    val item = adapter.getItem(position) as RemoteItem
//                                    item.spanCount
//                                }
//                                else -> 1
//                            }
//                        }
//                    }
//                }
//            }
//        }
}


