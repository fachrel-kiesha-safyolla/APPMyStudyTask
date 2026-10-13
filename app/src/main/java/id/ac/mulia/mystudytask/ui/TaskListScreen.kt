package id.ac.mulia.mystudytask.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val filterOptions = listOf("Semua") + TaskOptions.status

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MyStudyTask") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAdd,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text("Tambah Tugas") }
            )
        }
    ) { pad ->
        Column(Modifier.padding(pad)) {
            DropdownField(
                label = "Filter status",
                options = filterOptions,
                selected = state.filterStatus ?: "Semua",
                onSelect = { vm.setFilter(if (it == "Semua") null else it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )

            if (state.tasks.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada tugas. Tekan Tambah Tugas untuk menambah.")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.tasks, key = { it.id }) { t ->
                        Card(
                            modifier = Modifier.fillMaxWidth().clickable { onOpen(t.id) },
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(t.judul, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    "${t.mataKuliah} • Deadline ${t.deadline}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text(
                                        "Prioritas ${t.prioritas}",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                    Text(
                                        t.status,
                                        color = statusColor(t.status),
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun statusColor(status: String): Color = when (status) {
    "Selesai" -> Color(0xFF2E9E5B)   // hijau
    "Proses"  -> Color(0xFFF2A900)   // kuning
    else      -> Color(0xFFD9534F)   // merah (Belum)
}