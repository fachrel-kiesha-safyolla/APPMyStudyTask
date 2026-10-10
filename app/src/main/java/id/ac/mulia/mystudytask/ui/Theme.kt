package id.ac.mulia.mystudytask.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val AppColors = lightColorScheme(
    primary = Color(0xFF07575B),            // warna utama: top bar, tombol
    onPrimary = Color(0xFFC4dfe6),                // teks di atas primary
    secondary = Color(0xFF07575B),
    primaryContainer = Color(0xFFDCE6F5),   // chip terpilih, dll
    onPrimaryContainer = Color(0xFF003B46),
    background = Color(0xFF66a5ad),         // latar layar
    surface = Color(0xFFc4dfe6),                  // latar card
)

private val AppShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp)
)

@Composable
fun MyStudyTaskTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = AppColors, shapes = AppShapes, content = content)
}