package id.ac.mulia.mystudytask.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY deadline ASC")
    fun getAll(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Int): Flow<Task?>

    @Insert suspend fun insert(task: Task)
    @Update suspend fun update(task: Task)
    @Delete suspend fun delete(task: Task)
}
