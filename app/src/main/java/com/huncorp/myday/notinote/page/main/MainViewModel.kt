package com.huncorp.myday.notinote.page.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huncorp.myday.notinote.manager.NotiManager
import com.huncorp.myday.notinote.model.Notice
import com.huncorp.myday.notinote.models.NotifyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val manager: NotiManager
) : ViewModel() {
    var listItem = ArrayList<Notice>()
    var listItemLiveData = MutableLiveData<ArrayList<Notice>>()
    var newNoticeAdded = MutableLiveData<Notice>()

    fun onReady() {
        loadListItem()
    }

    fun addNewNotify(title: String, desc: String) {
        if (title.isBlank() && desc.isBlank()) return
        viewModelScope.launch {
            val noticeId = manager.insert(title, desc)
            val notice = manager.getById(noticeId)
            listItem.add(notice)
            listItemLiveData.value = listItem
            newNoticeAdded.value = notice
        }
    }

    private fun loadListItem() {
        viewModelScope.launch {
            runCatching { manager.getAll() }
                .onSuccess {
                    listItem.clear()
                    listItem.addAll(it)
                    listItemLiveData.value = listItem
                }
        }
    }
}