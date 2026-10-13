package id.ac.mulia.mystudytask.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.ac.mulia.mystudytask.data.TaskOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int,
    vm: TaskViewModel,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit
) {
    val task by remember(taskId) { vm.getTask(taskId) }.collectAsState(initial = null)
    var confirmDelete by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Tugas") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
                actions = {
                    IconButton(onClick = { onEdit(taskId) }) { Icon(Icons.Default.Edit, "Edit") }
                    IconButton(onClick = { confirmDelete = true }) { Icon(Icons.Default.Delete, "Hapus") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { pad ->
        val t = task
        if (t == null) {
            Box(Modifier.padding(pad).padding(16.dp)) { Text("Memuat...") }
        } else {
            Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(t.judul, style = MaterialTheme.typography.headlineSmall)
                Text("Mata kuliah: ${t.mataKuliah}")
                Text("Deadline: ${t.deadline}")
                Text("Prioritas: ${t.prioritas}")
                Text("Deskripsi:", style = MaterialTheme.typography.labelLarge)
                Text(t.deskripsi.ifBlank { "-" })
                Spacer(Modifier.height(8.dp))
                DropdownField(
                    label = "Status",
                    options = TaskOptions.status,
                    selected = t.status,
                    onSelect = { vm.changeStatus(t, it) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Hapus tugas?") },
            text = { Text("Tugas yang dihapus tidak dapat dikembalikan.") },
            confirmButton = {
                TextButton(onClick = {
                    task?.let { vm.delete(it) }
                    confirmDelete = false
                    onBack()
                }) { Text("Hapus") }
            },
            dismissButton = { TextButton(onClick = { confirmDelete = false }) { Text("Batal") } }
        )
    }
}