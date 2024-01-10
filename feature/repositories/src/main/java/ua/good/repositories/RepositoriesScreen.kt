package ua.good.repositories

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ua.good.model.RepositoriesFilter
import ua.good.model.Repository
import ua.good.utils.ResourcesUtil
import ua.good.utils.base.compose.widget.ErrorText
import ua.good.utils.base.compose.widget.Progress
import ua.good.utils.base.compose.widget.SingleSelectDialog
import ua.good.utils.base.compose.widget.TopBar

@Composable
fun RepositoriesRoute(backClickListener: () -> Unit = {}) {
    val model: RepositoriesModel = hiltViewModel()
    var currentFilter by rememberSaveable { mutableIntStateOf(0) }
    var showFilterDialog by rememberSaveable { mutableStateOf(false) }
    val state by model.state.collectAsStateWithLifecycle()

    BackHandler(true, backClickListener)
    RepositoriesScreen(state, showFilterDialog, currentFilter, {
        showFilterDialog = true
    }, {
        showFilterDialog = false
        currentFilter = it
        model.handleFilterChanged(RepositoriesFilter.entries[it])
    }, {
        showFilterDialog = false
    })
}

@Composable
fun RepositoriesScreen(
    state: RepositoriesStates<List<Repository>>,
    showFilterDialog: Boolean = false,
    currentFilter: Int = 0,
    filterIconClicked: () -> Unit = {},
    filterChanged: (Int) -> Unit = {},
    filterDialogHided: () -> Unit = {}
) {
    val filters = ResourcesUtil(LocalContext.current).getStringArray(R.array.repository_filter)
    Scaffold(
        topBar = {
            TopBar(
                R.string.repositories_title,
                filterIconClicked,
                R.drawable.baseline_filter_list_24
            )
        },
        content = { padding ->
            Content(padding, state)
            if (showFilterDialog) {
                SingleSelectDialog(
                    R.string.repositories_title,
                    filters,
                    currentFilter,
                    R.string.repositories_filter_title,
                    filterChanged,
                    filterDialogHided
                )
            }
        }
    )
}

@Composable
fun Content(padding: PaddingValues, state: RepositoriesStates<List<Repository>>) {
    if (state is RepositoriesStates.Loaded) {
        val list = state.list
        if (list.isEmpty()) {
            ErrorText(R.string.error_count_repositories)
        } else {
            Repositories(list, padding)
        }
    } else if (state is RepositoriesStates.Progress) {
        Progress()
    } else if (state == RepositoriesStates.ServerError) {
        ErrorText(ua.good.utils.R.string.error_internet)
    } else if (state == RepositoriesStates.InternetError) {
        ErrorText(ua.good.utils.R.string.error_server)
    }
}

@Composable
fun Repositories(titles: List<Repository>, padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.padding(padding)
    ) {
        itemsIndexed(titles) { index, title ->
            AppDivider()
            RepositoryItem(title)
            if (index == titles.size - 1) {
                AppDivider()
            }
        }
    }
}

@Composable
fun RepositoryItem(item: Repository, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(10.dp, 0.dp, 10.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://cdn-icons-png.flaticon.com/512/4436/4436481.png")
                    .crossfade(true)
                    .build(),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(40.dp)
                    .clip(shape = MaterialTheme.shapes.small)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun AppDivider() {
    Divider(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    )
}

@Preview
@Composable
fun RepositoriesScreenPreview() = RepositoriesScreen(RepositoriesStates.Loaded(getTestRepositories()))

@Preview
@Composable
fun DialogPreview() =
    RepositoriesScreen(RepositoriesStates.Loaded(getTestRepositories()), true)

@Preview
@Composable
fun ErrorPreview() {
    RepositoriesScreen(RepositoriesStates.InternetError, false, 0)
}
