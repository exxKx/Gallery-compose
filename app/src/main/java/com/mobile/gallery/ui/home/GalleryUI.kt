package com.mobile.gallery.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mobile.gallery.ui.MainActivity
import com.mobile.gallery.ui.home.HomeMutation.*
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainActivity.HomeGallery(navigator: NavController, viewModel: HomeViewModel = homeViewModel) {
    val state by viewModel.getStateFlow().collectAsState(HomeState())
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val swipeRefreshState = rememberSwipeRefreshState(false)

    SideEffect {
        coroutineScope.launch {
            state.lastScrollPos?.let {
                if (state.resumed && it != 0) {
                    listState.scrollToItem(it)
                    viewModel.mutation(ClearScrollPos)
                }
            }
        }
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { viewModel.mutation(Init) },
    ) {
        LazyColumn(state = listState) {
            if (!state.images.isNullOrEmpty())
                for (image in state.images!!) {
                    listState.layoutInfo.visibleItemsInfo.let { list ->
                        if (list.isEmpty()) return@let
                        if (listState.layoutInfo.totalItemsCount - 10 <= list.last().index)
                            viewModel.mutation(LoadMore)
                    }

                    item {
                        AsyncImage(
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .clickable {
                                    viewModel.mutation(SaveScrollPos(listState.firstVisibleItemIndex))

                                    val encodedUrl = URLEncoder.encode(
                                            image.url,
                                            StandardCharsets.UTF_8.toString()
                                        )
                                    val encodedTitle =
                                        URLEncoder.encode(
                                            image.title.let { it.ifEmpty { "Безымянная" } },
                                            StandardCharsets.UTF_8.toString()
                                        )
                                    navigator.navigate("image/$encodedUrl/$encodedTitle")
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .fillMaxWidth(),
                            model = image.url,
                            contentDescription = ""
                        )
                    }
                } else if (!state.progress) item { Text(text = "Пусто") }

            if (state.progress)
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(color = Color.Blue)
                    }
                }

        }
    }
}
