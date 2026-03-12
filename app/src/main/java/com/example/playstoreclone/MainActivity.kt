package com.example.playstoreclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playstoreclone.ui.theme.PlayStoreCloneTheme
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, "app_store_home")
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        })

        setContent {
            PlayStoreCloneTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppStoreHome()
                }
            }
        }
    }
}

data class AppItem(
    val name: String,
    val category: String,
    val rating: String,
    val size: String,
    val colorStart: Color,
    val colorEnd: Color
)

private val featuredApps = listOf(
    AppItem("Pixel Quest", "Aksiyon", "4.8", "145 MB", Color(0xFF34A853), Color(0xFF0F9D58)),
    AppItem("Fit Plus", "Sağlık", "4.6", "92 MB", Color(0xFF4285F4), Color(0xFF5C6BC0)),
    AppItem("CinemaFlow", "Eğlence", "4.7", "78 MB", Color(0xFFDB4437), Color(0xFFEF6C00))
)

private val recommendedApps = listOf(
    AppItem("TaskWave", "Verimlilik", "4.5", "39 MB", Color(0xFF7E57C2), Color(0xFF5E35B1)),
    AppItem("Learnify", "Eğitim", "4.9", "64 MB", Color(0xFF00ACC1), Color(0xFF00838F)),
    AppItem("Beatify", "Müzik", "4.4", "53 MB", Color(0xFF8BC34A), Color(0xFF43A047)),
    AppItem("Travelly", "Seyahat", "4.3", "87 MB", Color(0xFFFF7043), Color(0xFFE64A19))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppStoreHome() {
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sizin için", fontWeight = FontWeight.SemiBold)
                    }
                },
                actions = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Ara",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val tabs = listOf("Uygulamalar", "Oyunlar", "Filmler", "Teklifler")
                val icons = listOf(
                    Icons.Default.Widgets,
                    Icons.Default.Games,
                    Icons.Default.Movie,
                    Icons.Default.LocalOffer
                )
                tabs.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = index == selectedTab,
                        onClick = { selectedTab = index },
                        icon = { Icon(icons[index], contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Text(
                    text = "Öne çıkanlar",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(featuredApps) { app ->
                        FeaturedAppCard(app = app)
                    }
                }
            }
            item {
                Text(
                    text = "Önerilen uygulamalar",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(recommendedApps) { app ->
                RecommendedAppRow(app = app)
            }
        }
    }
}

@Composable
private fun FeaturedAppCard(app: AppItem) {
    Card(
        modifier = Modifier
            .width(260.dp)
            .height(160.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(listOf(app.colorStart, app.colorEnd)))
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.BottomStart)) {
                Text(app.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(app.category, color = Color.White.copy(alpha = 0.9f))
                Spacer(Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFF59D),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text("${app.rating} • ${app.size}", color = Color.White, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
private fun RecommendedAppRow(app: AppItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(listOf(app.colorStart, app.colorEnd)))
        )
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(app.name, fontWeight = FontWeight.SemiBold)
            Text(app.category, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = Color(0xFFFFC107)
                )
                Spacer(Modifier.width(4.dp))
                Text("${app.rating} • ${app.size}", style = MaterialTheme.typography.bodySmall)
            }
        }
        Text(
            text = "Yükle",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(horizontal = 14.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppStorePreview() {
    PlayStoreCloneTheme {
        AppStoreHome()
    }
}
