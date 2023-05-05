package com.raywenderlich.android.recyclerDrag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.android.remoteDemo.R


class DragActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(ExampleDataProviderFragment(), FRAGMENT_TAG_DATA_PROVIDER)
                .commit()
            supportFragmentManager.beginTransaction()
                .add(R.id.container, DragFragment(), FRAGMENT_LIST_VIEW)
                .commit()
        }
    }

    val dataProvider: AbstractDataProvider?
        get() {
            val fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG_DATA_PROVIDER)
            return (fragment as ExampleDataProviderFragment?)?.dataProvider
        }

    companion object {
        private const val FRAGMENT_TAG_DATA_PROVIDER = "data provider"
        private const val FRAGMENT_LIST_VIEW = "list view"
    }
}