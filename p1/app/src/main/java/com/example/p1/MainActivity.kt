package com.example.p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p1.practical2.ui.RestaurantFormScreen
import com.example.p1.ui.theme.P1Theme

enum class ActiveScreen {
    HOME,
    PRACTICAL_1,
    PRACTICAL_2
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            P1Theme {
                var currentScreen by remember { mutableStateOf(ActiveScreen.HOME) }

                // Hardware / gesture back navigation returns to the Home menu
                BackHandler(enabled = currentScreen != ActiveScreen.HOME) {
                    currentScreen = ActiveScreen.HOME
                }

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (currentScreen) {
                        ActiveScreen.HOME -> {
                            PracticalsMenuScreen(
                                onSelectPractical1 = { currentScreen = ActiveScreen.PRACTICAL_1 },
                                onSelectPractical2 = { currentScreen = ActiveScreen.PRACTICAL_2 }
                            )
                        }

                        ActiveScreen.PRACTICAL_1 -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                BackToMenuHeader(
                                    title = "Practical 1",
                                    onBack = { currentScreen = ActiveScreen.HOME }
                                )
                                GreetingScreen()
                            }
                        }

                        ActiveScreen.PRACTICAL_2 -> {
                            Column(modifier = Modifier.fillMaxSize()) {
                                BackToMenuHeader(
                                    title = "Practical 2",
                                    onBack = { currentScreen = ActiveScreen.HOME }
                                )
                                RestaurantFormScreen(
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Main dashboard screen displaying buttons for each practical.
 */
@Composable
fun PracticalsMenuScreen(
    onSelectPractical1: () -> Unit,
    onSelectPractical2: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mobile Application Development",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Select a practical to launch:",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
        )

        // Practical 1 Button
        Button(
            onClick = onSelectPractical1,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Practical 1: Environment & Project Setup",
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Practical 2 Button
        Button(
            onClick = onSelectPractical2,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "Practical 2: Restaurant Data Entry Form",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

/**
 * Clean top bar header with a back button to return to the Practicals Menu.
 */
@Composable
fun BackToMenuHeader(
    title: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedButton(
            onClick = onBack,
            shape = RoundedCornerShape(10.dp)
        ) {
            Text("← Back to Practicals")
        }
    }
}

@Composable
fun GreetingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hello Android!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Practical 1: Project configured and running successfully.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    P1Theme {
        PracticalsMenuScreen(onSelectPractical1 = {}, onSelectPractical2 = {})
    }
}