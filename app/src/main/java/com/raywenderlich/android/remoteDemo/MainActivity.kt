
package com.raywenderlich.android.remoteDemo

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.raywenderlich.android.remoteDemo.databinding.ActivityMainBinding

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

    private val maskDragMessage = "Mask Added"
    private val maskOn = "Bingo! Mask On"
    private val maskOff = "Mask off"

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attachViewDragListener()
        binding.dropArea.setOnDragListener(dragListener)
    }

    private fun attachViewDragListener() {
        val onLongClickListener = View.OnLongClickListener { view: View ->
            val item = ClipData.Item(maskDragMessage)
            val dataToDrag = ClipData(maskDragMessage, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
            val maskShadow = View.DragShadowBuilder(view)
            view.startDragAndDrop(dataToDrag, maskShadow, view, 0)
            view.visibility = View.INVISIBLE
            true
        }
        binding.item1.setOnLongClickListener(onLongClickListener)
        binding.item2.setOnLongClickListener(onLongClickListener)
    }

    private val dragListener = View.OnDragListener { view: View, dragEvent: DragEvent ->
        val draggableItem = dragEvent.localState as View
        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                draggableItem.visibility = View.VISIBLE
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                binding.dropArea.alpha = 0.3f
                true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                binding.dropArea.alpha = 1.0f
                draggableItem.visibility = View.VISIBLE
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                binding.dropArea.alpha = 1.0f
                if (dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    val draggedData = dragEvent.clipData.getItemAt(0).text
                    println("draggedData $draggedData")
                }
                draggableItem.x = dragEvent.x - draggableItem.width / 2
                draggableItem.y = dragEvent.y - draggableItem.height / 2
                val parent = draggableItem.parent as ConstraintLayout
                parent.removeView(draggableItem)
                val dropArea = view as ConstraintLayout
                dropArea.addView(draggableItem)
                true
            }
            else -> false
        }
    }
}
