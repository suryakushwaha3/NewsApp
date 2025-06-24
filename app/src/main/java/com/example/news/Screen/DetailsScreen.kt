import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.news.MyViewModel.MyViewModel
import com.example.news.utils.toPlainText
import androidx.compose.runtime.getValue




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: MyViewModel = viewModel()) {
    val article by viewModel.selectedArticle.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = article?.title ?: "Detail",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) { paddingValues ->

        if (article == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No article selected", modifier = Modifier.padding(16.dp))
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState()), // Enable vertical scrolling
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Image
            AsyncImage(
                model = article?.urlToImage,
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            // Author and Date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article?.author ?: "Unknown Author",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = article?.publishedAt?.substring(0, 10) ?: "",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            if (article != null) {
                // Description
                if (!article!!.description.isNullOrEmpty()) {
                    Text(
                        text = article!!.description!!.toPlainText(),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = Int.MAX_VALUE,
                        overflow = TextOverflow.Visible
                    )
                } else {
                    Text(
                        text = "No description available.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Content
                if (!article!!.content.isNullOrEmpty()) {
                    Text(
                        text = article!!.content!!.toPlainText(),
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = Int.MAX_VALUE,
                        overflow = TextOverflow.Visible
                    )
                } else {
                    Text(
                        text = "No content available.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            } else {
                // Handle the case when article is null
                Text(text = "No article selected.")
            }



        }
    }
}
