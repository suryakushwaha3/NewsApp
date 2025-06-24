import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.news.MyViewModel.MyViewModel
import com.example.news.Screen.NewsItemCard
import com.example.news.models.Article

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUI(
    viewModel: MyViewModel,
    navController: NavController
) {

    val categories = listOf("general", "business", "sports", "technology", "health", "entertainment")
    var selectedCategory by remember { mutableStateOf("general") }
    var searchQuery by remember { mutableStateOf("") }

    val articles = viewModel.response.collectAsState().value?.articles ?: emptyList()
    val isLoading = viewModel.isLoading.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value

    // Fetch news when category or search query changes
    LaunchedEffect(selectedCategory, searchQuery) {
        viewModel.getNewsByCategory(selectedCategory, searchQuery)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News App", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->

        Column(modifier = Modifier.padding(paddingValues)) {

            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Animated Category Tabs
            ScrollableTabRow(
                selectedTabIndex = categories.indexOf(selectedCategory),
                edgePadding = 8.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[categories.indexOf(selectedCategory)]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                categories.forEachIndexed { index, category ->
                    val isSelected = selectedCategory == category
                    val textColor by animateColorAsState(
                        targetValue = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
                    )
                    Tab(
                        selected = isSelected,
                        onClick = { selectedCategory = category },
                        text = { Text(category.replaceFirstChar { it.uppercase() }, color = textColor) }
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                    errorMessage.isNotEmpty() -> Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    articles.isEmpty() -> Text(
                        text = "No news available",
                        modifier = Modifier.align(Alignment.Center)
                    )

                    else -> LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(articles) { article ->
                            NewsItemCard(article = article, onClick = {
                                viewModel.selectArticle(article)
                                navController.navigate("details")
                            })
                        }
                    }
                }
            }
        }
    }
}
