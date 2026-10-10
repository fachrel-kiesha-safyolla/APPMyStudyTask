package id.ac.mulia.mystudytask.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.ac.mulia.mystudytask.data.TaskOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    vm: TaskViewModel,
    onAdd: () -> Unit,
    onOpen: (Int) -> Unit
) {
    val state by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("MyStudyTask") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAdd) { Icon(Icons.Default.Add, "Tambah tugas") }
        }
    ) { pad ->
        Column(Modifier.padding(pad)) {
            Row(
                Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = state.filterStatus == null,
                    onClick = { vm.setFilter(null) },
                    label = { Text("Semua") }
                )
                TaskOptions.status.forEach { s ->
                    FilterChip(
                        selected = state.filterStatus == s,
                        onClick = { vm.setFilter(s) },
                        label = { Text(s) }
                    )
                }
            }
            if (state.tasks.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("Belum ada tugas. Tekan + untuk menambah.")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.tasks, key = { it.id }) { t ->
                        Card(Modifier.fillMaxWidth().clickable { onOpen(t.id) }) {
                            Column(Modifier.padding(16.dp)) {
                                Text(t.judul, style = MaterialTheme.typography.titleMedium)
                                Text("${t.mataKuliah} • Deadline ${t.deadline}")
                                Text("Prioritas ${t.prioritas} • ${t.status}",
                                    style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}
