package com.raywenderlich.android.recyclerDrag

import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager
import java.util.*

class ExampleDataProvider : AbstractDataProvider() {
    private val mData: MutableList<ConcreteData?>
    private var mLastRemovedData: ConcreteData? = null
    private var mLastRemovedPosition = -1

    init {
        val atoz = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        mData = LinkedList()
        for (i in 0..1) {
            for (j in 0 until atoz.length) {
                val id = mData.size.toLong()
                val viewType = 0
                val text = Character.toString(atoz[j])
                val swipeReaction = RecyclerViewSwipeManager.REACTION_CAN_SWIPE_UP or RecyclerViewSwipeManager.REACTION_CAN_SWIPE_DOWN
                mData.add(ConcreteData(id, viewType, text, swipeReaction))
            }
        }
    }

    override val count: Int
        get() = mData.size

    override fun getItem(index: Int): Data {
        if (index < 0 || index >= count) {
            throw IndexOutOfBoundsException("index = $index")
        }
        return mData[index]!!
    }

    override fun undoLastRemoval(): Int {
        return if (mLastRemovedData != null) {
            val insertedPosition: Int
            insertedPosition = if (mLastRemovedPosition >= 0 && mLastRemovedPosition < mData.size) {
                mLastRemovedPosition
            } else {
                mData.size
            }
            mData.add(insertedPosition, mLastRemovedData)
            mLastRemovedData = null
            mLastRemovedPosition = -1
            insertedPosition
        } else {
            -1
        }
    }

    override fun moveItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition == toPosition) {
            return
        }
        val item = mData.removeAt(fromPosition)
        mData.add(toPosition, item)
        mLastRemovedPosition = -1
    }

    override fun swapItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition == toPosition) {
            return
        }
        Collections.swap(mData, toPosition, fromPosition)
        mLastRemovedPosition = -1
    }

    override fun removeItem(position: Int) {
        val removedItem = mData.removeAt(position)
        mLastRemovedData = removedItem
        mLastRemovedPosition = position
    }

    class ConcreteData internal constructor(override val id: Long, override val viewType: Int, text: String, swipeReaction: Int) : Data() {
        override val text: String
        override var isPinned = false

        init {
            this.text = makeText(id, text, swipeReaction)
        }

        override val isSectionHeader: Boolean
            get() = false

        override fun toString(): String {
            return text
        }

        companion object {
            private fun makeText(id: Long, text: String, swipeReaction: Int): String {
                return "$id - $text"
            }
        }
    }
}