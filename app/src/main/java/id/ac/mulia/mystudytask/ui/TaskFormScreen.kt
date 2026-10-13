package id.ac.mulia.mystudytask.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.ac.mulia.mystudytask.data.Task
import id.ac.mulia.mystudytask.data.TaskOptions
import kotlinx.coroutines.flow.flowOf

/** Dipakai untuk Tambah (taskId = 0) dan Edit (taskId > 0). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(taskId: Int, vm: TaskViewModel, onDone: () -> Unit) {
    val existing by remember(taskId) {
        if (taskId > 0) vm.getTask(taskId) else flowOf(null)
    }.collectAsState(initial = null)

    var mataKuliah by remember { mutableStateOf("") }
    var judul by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var prioritas by remember { mutableStateOf(TaskOptions.prioritas[1]) }
    var status by remember { mutableStateOf(TaskOptions.status[0]) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(existing) {
        existing?.let {
            mataKuliah = it.mataKuliah; judul = it.judul; deskripsi = it.deskripsi
            deadline = it.deadline; prioritas = it.prioritas; status = it.status
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId > 0) "Edit Tugas" else "Tambah Tugas") },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
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
        Column(
            Modifier.padding(pad).padding(16.dp).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(mataKuliah, { mataKuliah = it }, label = { Text("Mata kuliah") },
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(judul, { judul = it }, label = { Text("Judul tugas") },
                modifier = Modifier.fillMaxWidth())
            OutlinedTextField(deskripsi, { deskripsi = it }, label = { Text("Deskripsi") },
                modifier = Modifier.fillMaxWidth(), minLines = 3)

            DateField(
                label = "Deadline",
                value = deadline,
                onDateSelected = { deadline = it },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownField(
                label = "Prioritas",
                options = TaskOptions.prioritas,
                selected = prioritas,
                onSelect = { prioritas = it },
                modifier = Modifier.fillMaxWidth()
            )
            DropdownField(
                label = "Status",
                options = TaskOptions.status,
                selected = status,
                onSelect = { status = it },
                modifier = Modifier.fillMaxWidth()
            )

            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            Button(
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                onClick = {
                    if (judul.isBlank() || mataKuliah.isBlank()) {
                        error = "Mata kuliah dan judul wajib diisi."
                    } else if (deadline.isBlank()) {
                        error = "Deadline wajib dipilih."
                    } else {
                        vm.save(
                            Task(
                                taskId, mataKuliah.trim(), judul.trim(), deskripsi.trim(),
                                deadline, prioritas, status
                            )
                        )
                        onDone()
                    }
                }
            ) { Text("Simpan") }
        }
    }
}