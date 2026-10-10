package id.ac.mulia.mystudytask.data

import kotlinx.coroutines.flow.Flow

/** Satu-satunya pintu akses data bagi ViewModel. UI tidak tahu soal Room. */
class TaskRepository(private val dao: TaskDao) {
    val tasks: Flow<List<Task>> = dao.getAll()
    fun getById(id: Int): Flow<Task?> = dao.getById(id)
    suspend fun insert(task: Task) = dao.insert(task)
    suspend fun update(task: Task) = dao.update(task)
    suspend fun delete(task: Task) = dao.delete(task)
}
