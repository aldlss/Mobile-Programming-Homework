package cn.suwako.speedrun.ui.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.suwako.speedrun.MainActivity
import cn.suwako.speedrun.data.local.entities.RunData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataViewModel: ViewModel() {

    private var runData = listOf<RunData>()
    var showData = mutableStateMapOf<Int, List<RunData>>()

    fun loadData(userId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MainActivity.database.runDataDao().loadRunDataByUserId(userId).collect { runDataList ->
                    withContext(Dispatchers.Default) {
                        runData = runDataList
                        showData.clear()
                        showData.putAll(runData
                            .sortedBy { it.runDate }
                            .groupBy { it.runDate.year * 12 + it.runDate.monthValue - 1 }
                            .toSortedMap()
                        )
                    }
                }
            }
        }
    }
}