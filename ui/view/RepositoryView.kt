package com.app.modules.module4.pr3.ui.view

import android.R.attr.shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.modules.module4.pr3.domain.usecase.getRepositoryList
import com.app.modules.module4.pr3.domain.util.debounce
import com.app.modules.module4.pr3.ui.viewmodel.RepositoryViewModel

@Preview
@Composable
fun RepositoryView(
    viewModel: RepositoryViewModel = viewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val data = getRepositoryList(context)
        viewModel.setData(data)
        println("===$data")
    }

    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val repositories by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val searchDebounced = remember {
        scope.debounce<String>(500L) {
            viewModel.search(it)
        }
    }
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            Text(
                "Repository Search App",
                style = MaterialTheme.typography.titleLarge
            )
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    searchDebounced(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
            )

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(repositories) { rep ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color.LightGray,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Column {
                                val size = 24.sp
                                Text("Name: ${rep.full_name}", fontSize = size)
                                Text("Description: ${rep.description}", fontSize = size)
                                Text("Stars: ${rep.stargazers_count}", fontSize = size)
                                Text("Language: ${rep.language}", fontSize = size)
                            }
                        }

                    }
                }
            }
        }
    }
}