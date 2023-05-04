/*
 * Copyright (c) 2021 Razeware LLC
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 * 
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.masky

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import com.raywenderlich.android.masky.databinding.ActivityMainBinding

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
