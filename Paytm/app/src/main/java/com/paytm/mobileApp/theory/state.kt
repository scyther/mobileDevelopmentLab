package com.paytm.mobileApp.theory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.paytm.mobileApp.R


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun StateExampleClass() {
    var counter by remember { mutableStateOf(0) }
//    var counter by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Counter = $counter", fontSize = 40.sp)


        Button(
            onClick = { counter += 1 }, enabled = counter < 10
        ) {
//            https://fonts.google.com/icons
//            https://developer.android.com/studio/write/vector-asset-studio#svg

            Icon(
                painter = painterResource(R.drawable.arrow_upward),
                contentDescription = "Arrow Up",
                tint = Color.Gray
            )
        }

        Button(
            onClick = { counter-- }, enabled = counter > 0
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_downward),
                contentDescription = "Arrow Up",
            )
        }

        var showDialog by remember { mutableStateOf(false) }

        Button({ showDialog = true }) {
            Text(text = "Reset")
        }

        var text by remember { mutableStateOf("") }
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Enter text") }
        )

        if (showDialog) {
            AlertDialog(
                confirmButton = {
                    Button(onClick = {
                        counter = 0
                        showDialog = false
                    }) {
                        Text(text = "Reset")
                    }
                },
                title = {
                    Text(text = "Reset Counter")
                },
                text = {
                    Text(text = "Are you sure you want to reset the counter?")
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                },
                onDismissRequest = { showDialog = false })
        }
    }

}


// 1. How to add Icons?
// 2. How to use State?
// 3. State usage with Text and TextField.
// 4. Using AlertDialogs.


@Composable
fun TestStateScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_downward),
            contentDescription = "Arrow Down"
        )
    }

}