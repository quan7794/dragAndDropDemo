package com.raywenderlich.android.recyclerDrag

abstract class AbstractDataProvider {
    abstract class Data {
        abstract val id: Long
        abstract val isSectionHeader: Boolean
        abstract val viewType: Int
        abstract val text: String?
        abstract var isPinned: Boolean
    }

    abstract val count: Int

    abstract fun getItem(index: Int): Data
    abstract fun removeItem(position: Int)
    abstract fun moveItem(fromPosition: Int, toPosition: Int)
    abstract fun swapItem(fromPosition: Int, toPosition: Int)
    abstract fun undoLastRemoval(): Int
}