package id.ac.mulia.mystudytask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import id.ac.mulia.mystudytask.ui.AppNav
import id.ac.mulia.mystudytask.ui.MyStudyTaskTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MyStudyTaskTheme { AppNav() } }
    }
}
