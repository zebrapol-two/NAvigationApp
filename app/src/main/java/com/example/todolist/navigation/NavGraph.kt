package com.example.todolist.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.presentation.ui.screen.TodoDetailScreen
import com.example.todolist.presentation.ui.screen.TodoListScreen
import com.example.todolist.presentation.viewmodel.TodoViewModel
import com.example.todolist.presentation.viewmodel.TodoViewModelFactory
import com.example.todolist.domain.repository.TodoRepository
import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.repository.TodoRepositoryImpl

@Composable
fun NavGraph(appContext: Context, repo: TodoRepository? = null) {
    val navController = rememberNavController()
    val repository = repo ?: TodoRepositoryImpl(TodoJsonDataSource(appContext))
    val factory = TodoViewModelFactory(appContext as android.app.Application, repository)
    val vm: TodoViewModel = viewModel(factory = factory)

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            TodoListScreen(viewModel = vm, onItemClick = { id ->
                navController.navigate("detail/$id")
            })
        }
        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: -1
            val item = vm.getById(id)
            TodoDetailScreen(item = item, onBack = { navController.popBackStack() })
        }
    }
}
