package id.ac.mulia.mystudytask.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import id.ac.mulia.mystudytask.data.AppDatabase
import id.ac.mulia.mystudytask.data.Task
import id.ac.mulia.mystudytask.data.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/** UI state untuk layar daftar. */
data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val filterStatus: String? = null
)

class TaskViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = TaskRepository(AppDatabase.get(app).taskDao())
    private val filter = MutableStateFlow<String?>(null)

    val uiState: StateFlow<TaskListUiState> =
        combine(repo.tasks, filter) { list, f ->
            TaskListUiState(
                tasks = if (f == null) list else list.filter { it.status == f },
                filterStatus = f
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TaskListUiState())

    fun setFilter(status: String?) { filter.value = status }
    fun getTask(id: Int): Flow<Task?> = repo.getById(id)

    fun save(task: Task) = viewModelScope.launch {
        if (task.id == 0) repo.insert(task) else repo.update(task)
    }
    fun delete(task: Task) = viewModelScope.launch { repo.delete(task) }
    fun changeStatus(task: Task, status: String) = viewModelScope.launch {
        repo.update(task.copy(status = status))
    }
}
