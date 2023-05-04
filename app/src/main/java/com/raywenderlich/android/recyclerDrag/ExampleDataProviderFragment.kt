package com.raywenderlich.android.recyclerDrag

import android.os.Bundle
import androidx.fragment.app.Fragment


class ExampleDataProviderFragment : Fragment() {
    var dataProvider: AbstractDataProvider? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true // keep the mDataProvider instance
        dataProvider = ExampleDataProvider()
    }
}