package com.ekodex.manpowerhrms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LeaveDateViewModel : ViewModel() {

    private val _fromDate = MutableLiveData<String>()
    private val _toDate = MutableLiveData<String>()

    val fromDate: LiveData<String> = _fromDate
    val toDate: LiveData<String> = _toDate

    // Expose a single LiveData pair of dates
    val dateRange = MediatorLiveData<Pair<String, String>>().apply {
        addSource(_fromDate) { f ->
            val t = _toDate.value ?: ""
            if(f.isNotEmpty() && t.isNotEmpty()) value = Pair(f, t)
        }
        addSource(_toDate) { t ->
            val f = _fromDate.value ?: ""
            if(f.isNotEmpty() && t.isNotEmpty()) value = Pair(f, t)
        }
    }

    fun setDates(from: String, to: String) {
        _fromDate.value = from
        _toDate.value = to
    }
}
