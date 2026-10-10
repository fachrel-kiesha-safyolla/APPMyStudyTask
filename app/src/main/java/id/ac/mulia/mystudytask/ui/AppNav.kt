package id.ac.mulia.mystudytask.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

object Routes {
    const val LIST = "list"
    const val FORM = "form/{id}"      // id = 0 -> tambah, id > 0 -> edit
    const val DETAIL = "detail/{id}"
    fun form(id: Int) = "form/$id"
    fun detail(id: Int) = "detail/$id"
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val vm: TaskViewModel = viewModel()   // satu ViewModel dibagi ke semua layar
    val idArg = listOf(navArgument("id") { type = NavType.IntType })

    NavHost(nav, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            TaskListScreen(vm, onAdd = { nav.navigate(Routes.form(0)) },
                onOpen = { nav.navigate(Routes.detail(it)) })
        }
        composable(Routes.FORM, idArg) { e ->
            TaskFormScreen(e.arguments!!.getInt("id"), vm, onDone = { nav.popBackStack() })
        }
        composable(Routes.DETAIL, idArg) { e ->
            val id = e.arguments!!.getInt("id")
            TaskDetailScreen(id, vm, onBack = { nav.popBackStack() },
                onEdit = { nav.navigate(Routes.form(it)) })
        }
    }
}
