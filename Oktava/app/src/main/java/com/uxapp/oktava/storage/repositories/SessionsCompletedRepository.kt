package com.uxapp.oktava.storage.repositories

import androidx.lifecycle.LiveData
import com.uxapp.oktava.storage.dao.SessionsCompletedDao
import com.uxapp.oktava.storage.model.SessionCompleted
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionsCompletedRepository @Inject constructor(
    private val sessionsCompletedDao: SessionsCompletedDao,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
){
    val liveData = sessionsCompletedDao.getLiveData()

    fun addNewItem(item: SessionCompleted){
        applicationScope.launch(Dispatchers.IO) {
            sessionsCompletedDao.addSession(item)
        }
    }

    fun deleteItem(item: SessionCompleted) {
        applicationScope.launch(Dispatchers.IO) {
            sessionsCompletedDao.removeSession(item)
        }
    }

    fun changeItem(newItem: SessionCompleted){
        applicationScope.launch(Dispatchers.IO) {
            sessionsCompletedDao.editSession(newItem)
        }
    }

    fun getSessionByIdAsLiveData(id: Long): LiveData<SessionCompleted> {
        return sessionsCompletedDao.getRecordingsOfSessionLiveData(id)
    }

    suspend fun getSessionById(id: Long): SessionCompleted {
        return sessionsCompletedDao.getRecordingsOfSession(id)
    }
}