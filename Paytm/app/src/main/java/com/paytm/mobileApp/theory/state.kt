package com.paytm.mobileApp.theory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.paytm.mobileApp.icons.arrow_circle_up

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun StateExampleClass() {
//    var counter by remember { mutableStateOf(0) }
    var counter by rememberSaveable { mutableStateOf(0) }

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
            Icon(
                imageVector = arrow_circle_up, contentDescription = "Up"
            )
        }

        Button(
            onClick = { counter-- }, enabled = counter > 0
        ) {
            Icon(
                imageVector = arrow_circle_up, contentDescription = "Up"
            )
        }

        var showDialog by remember { mutableStateOf(false) }
        Button({ showDialog = true }) {
            Text(text = "Reset")
        }

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