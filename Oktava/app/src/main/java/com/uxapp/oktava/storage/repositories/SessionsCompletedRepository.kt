package com.uxapp.oktava.storage.repositories

import android.util.Log
import com.uxapp.oktava.storage.dao.SessionsCompletedDao
import com.uxapp.oktava.storage.model.Recording
import com.uxapp.oktava.storage.model.SessionCompleted
import com.uxapp.oktava.utils.Layer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SessionsCompletedRepository @Inject constructor(
    private val sessionsCompletedDao: SessionsCompletedDao,
    private val recordingsRepository: RecordingsRepository,
    @com.uxapp.oktava.utils.CoroutineScopeQualifier(Layer.APP)
    private val applicationScope: CoroutineScope
) {
    val sessionsCompletedFlow = sessionsCompletedDao.getSessionsCompletedFlow()

    fun addNewItem(item: SessionCompleted) {
        applicationScope.launch(Dispatchers.IO) {
            sessionsCompletedDao.addSession(item)
        }
    }

    fun deleteItem(item: SessionCompleted) {
        applicationScope.launch(Dispatchers.IO) {
            sessionsCompletedDao.removeSession(item)
        }
    }

    fun changeItem(newItem: SessionCompleted?) {
        if (newItem == null) return
        applicationScope.launch(Dispatchers.IO) {
            sessionsCompletedDao.editSession(newItem)
        }
    }

    fun getSessionByIdAsFlow(id: Long): Flow<SessionCompleted?> {
        return sessionsCompletedDao.getSessionByIdFlow(id).map {
            if(it.isNotEmpty()) it.first() else null
        }
    }

    fun getRecordingsOfSessionFlow(id: Long): Flow<List<Recording>> {
        Log.e("KOSTIK", "SessionRecordings called")
        return getSessionByIdAsFlow(id).map { session ->
            Log.e("KOSTIK", "SessionRecordings: ${session?.recordingsID}")
            session?.recordingsID?.mapNotNull {
                recordingsRepository.getRecordingByPath(it)
            } ?: ArrayList()
        }
    }

    suspend fun getAllSessions(): List<SessionCompleted> {
        return sessionsCompletedDao.all()
    }

    suspend fun getSessionById(id: Long): SessionCompleted? {
        val listResult = sessionsCompletedDao.getSessionById(id)
        return if (listResult.isNotEmpty()) listResult.first() else null
    }
}