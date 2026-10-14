package com.paytm.mobileApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paytm.mobileApp.theory.StateExampleClass
import com.paytm.mobileApp.theory.navigation.NavGraph
import com.paytm.mobileApp.ui.theme.PaytmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaytmTheme {
//                StateExampleClass()
                NavGraph()
            }
        }
    }
}


@Composable
fun TestScreen(modifier: Modifier) {
//    Row(
//        modifier = Modifier.fillMaxSize(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically
//    ) { Text(text = "Hello World")
//        Text(text = "hi")
//    }
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        item { Text(text = "Hello") }
//        item { Text(text = "World") }
//        item {
//            Image(
//                painter = painterResource(R.drawable.img1),
//                contentDescription = "New Image"
//            )
//        }
//
//    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "hello")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "hello")
            Text(text = "world")
            Text(text = "hello")
        }
        Image(
            painter = painterResource(R.drawable.img),
            contentDescription = "New Img",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(40.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestScreenPreview() {
    PaytmTheme {
        TestScreen(modifier = Modifier)
    }
}

