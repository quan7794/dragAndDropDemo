package com.raywenderlich.android.recyclerDrag

import android.graphics.drawable.NinePatchDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils
import com.raywenderlich.android.masky.R


class DragFragment : Fragment() {
    private var mRecyclerView: RecyclerView? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mAdapter: DraggableGridExampleAdapter? = null
    private var mWrappedAdapter: RecyclerView.Adapter<*>? = null
    private var mRecyclerViewDragDropManager: RecyclerViewDragDropManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview_listview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)

        mLayoutManager = GridLayoutManager(context, 6, GridLayoutManager.VERTICAL, false)

        (mLayoutManager as GridLayoutManager).spanSizeLookup = object : SpanSizeLookup() {
            var map = intArrayOf(6, 2, 2, 2, 1, 1, 1, 3, 3, 3, 1, 3, 1, 1)
            override fun getSpanSize(position: Int): Int {
                val index = mWrappedAdapter!!.getItemId(position).toInt()
                // int index = position;   // uncomment this line to restore the previous gif behavior
                return map[index % map.size]
            }
        }

        // drag & drop manager
        mRecyclerViewDragDropManager = RecyclerViewDragDropManager()
//        mRecyclerViewDragDropManager!!.setDraggingItemShadowDrawable(
//            ContextCompat.getDrawable(requireContext(), R.drawable.material_shadow_z3) as NinePatchDrawable?
//        )
        // Start dragging after long press
        mRecyclerViewDragDropManager!!.setInitiateOnLongPress(true)
        mRecyclerViewDragDropManager!!.setInitiateOnMove(false)
        mRecyclerViewDragDropManager!!.setLongPressTimeout(750)

        // setup dragging item effects (NOTE: DraggableItemAnimator is required)
        mRecyclerViewDragDropManager!!.dragStartItemAnimationDuration = 250
        mRecyclerViewDragDropManager!!.draggingItemAlpha = 0.8f
        mRecyclerViewDragDropManager!!.draggingItemScale = 1.3f
        mRecyclerViewDragDropManager!!.draggingItemRotation = 15.0f

        //adapter
        val myItemAdapter = DraggableGridExampleAdapter(dataProvider!!)
        mAdapter = myItemAdapter
        mWrappedAdapter = mRecyclerViewDragDropManager!!.createWrappedAdapter(myItemAdapter) // wrap for dragging
        val animator: GeneralItemAnimator = DraggableItemAnimator() // DraggableItemAnimator is required to make item animations properly.
        mRecyclerView!!.setLayoutManager(mLayoutManager)
        mRecyclerView!!.setAdapter(mWrappedAdapter) // requires *wrapped* adapter
        mRecyclerView!!.setItemAnimator(animator)

        // additional decorations
        if (supportsViewElevation()) {
            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
        } else {
            mRecyclerView!!.addItemDecoration(ItemShadowDecorator((ContextCompat.getDrawable(requireContext(), R.drawable.material_shadow_z3) as NinePatchDrawable?)!!))
        }
        mRecyclerViewDragDropManager!!.attachRecyclerView(mRecyclerView!!)

        // for debugging
//        animator.setDebug(true);
//        animator.setMoveDuration(2000);
    }

    override fun onPause() {
        mRecyclerViewDragDropManager!!.cancelDrag()
        super.onPause()
    }

    override fun onDestroyView() {
        if (mRecyclerViewDragDropManager != null) {
            mRecyclerViewDragDropManager!!.release()
            mRecyclerViewDragDropManager = null
        }
        if (mRecyclerView != null) {
            mRecyclerView!!.itemAnimator = null
            mRecyclerView!!.adapter = null
            mRecyclerView = null
        }
        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter)
            mWrappedAdapter = null
        }
        mAdapter = null
        mLayoutManager = null
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_drag_grid, menu)

        // setting up the item move mode selection switch
        val menuSwitchItem = menu.findItem(R.id.menu_switch_swap_mode)
        val actionView = menuSwitchItem.actionView.findViewById<CompoundButton>(R.id.switch_view)
        actionView.setOnCheckedChangeListener { buttonView: CompoundButton?, isChecked: Boolean ->
            updateItemMoveMode(
                isChecked
            )
        }
    }

    private fun updateItemMoveMode(swapMode: Boolean) {
        val mode = if (swapMode) RecyclerViewDragDropManager.ITEM_MOVE_MODE_SWAP else RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT
        mRecyclerViewDragDropManager!!.itemMoveMode = mode
        mAdapter!!.setItemMoveMode(mode)
        view?.let { Snackbar.make(it, "Item move mode: " + if (swapMode) "SWAP" else "DEFAULT", Snackbar.LENGTH_SHORT).show() }
    }

    private fun supportsViewElevation(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    val dataProvider: AbstractDataProvider?
        get() = (activity as DragActivity?)?.dataProvider
}