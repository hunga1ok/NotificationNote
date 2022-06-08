package com.huncorp.myday.notinote.page.main

import androidx.lifecycle.ViewModel
import com.huncorp.myday.notinote.models.NotifyData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {
    val listItem = arrayListOf(
        NotifyData("test1", "test1"),
        NotifyData("test2", "test2"),
        NotifyData("test3", "test3"),
        NotifyData("test4", "test4"),
        NotifyData("test5", "test5")
    )
}