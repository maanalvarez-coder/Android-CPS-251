package com.example.navappassignment6.screens


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




@Composable
fun ProfileScreen(
    onBackClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "User Profile", fontSize = 50.sp )
        Card(modifier = Modifier
            .size(400.dp)  // Set the size of the box
            .padding(16.dp)  // Add space around the box
            .background(Color.LightGray, shape = RoundedCornerShape(8.dp)),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(8.dp)



        ){


            Row (modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(20
                    .dp, Alignment.CenterHorizontally)){
                FlowColumn (
                    modifier =  Modifier.weight(1f).padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    Text("Name:")
                    Text("Email:")
                    Text("Student ID:")
                    Text("Major:")
                    Text("Year:")

                }
                FlowColumn (
                    modifier =  Modifier.weight(1f).padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    Text("Name:")
                    Text("Email:")
                    Text("Student ID:")
                    Text("Major:")
                    Text("Year:")
                }
                }



                  }
        Button(onClick = ({onBackClick()})) {
            Text("Go Back") }  }
}














//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.navappassignment6.modelViews.LoginView
//
//@Composable
//fun ProfileScreen(
//    onBackClick: () -> Unit
//){
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ){
//        Button(onClick = ({onBackClick()})) {
//            Text("Go Back")
//        }
//        Text("Hekki")
//    }
//}