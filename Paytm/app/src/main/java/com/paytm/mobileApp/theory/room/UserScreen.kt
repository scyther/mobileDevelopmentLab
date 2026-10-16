package com.paytm.mobileApp.theory.room

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Entry point composable that provides the ViewModel and launches the screen.
 */
@Composable
fun RoomPracticalApp() {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val dao = remember { database.userDao() }
    val viewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(dao)
    )

    UserScreen(viewModel = viewModel)
}

/**
 * Step 5: Build the Jetpack Compose UI
 * Collects state reactively and provides basic CRUD (Insert, Read, Delete).
 */
@Composable
fun UserScreen(viewModel: UserViewModel) {
    // Collect the flow reactively from ViewModel
    val userList by viewModel.allUsers.collectAsState()

    var nameText by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("") }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Room SQLite Database Practical",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            // Input: Name
            OutlinedTextField(
                value = nameText,
                onValueChange = { nameText = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Input: Age
            OutlinedTextField(
                value = ageText,
                onValueChange = { ageText = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Button: Insert into SQLite
            Button(
                onClick = {
                    val age = ageText.toIntOrNull()
                    if (nameText.isNotBlank() && age != null) {
                        viewModel.addUser(name = nameText.trim(), age = age)
                        nameText = ""
                        ageText = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add User to SQLite")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = "Stored Users (${userList.size}):",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            // Live display of SQLite database contents
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userList, key = { it.id }) { user ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = user.name,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Age: ${user.age}  •  ID: ${user.id}",
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            OutlinedButton(
                                onClick = { viewModel.deleteUser(user) }
                            ) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
