package id.ac.mulia.mystudytask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/** Model data: satu baris = satu tugas. */
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mataKuliah: String,
    val judul: String,
    val deskripsi: String,
    val deadline: String,      // format yyyy-MM-dd agar bisa diurutkan
    val prioritas: String,     // Rendah / Sedang / Tinggi
    val status: String         // Belum / Proses / Selesai
)

object TaskOptions {
    val prioritas = listOf("Rendah", "Sedang", "Tinggi")
    val status = listOf("Belum", "Proses", "Selesai")
}
