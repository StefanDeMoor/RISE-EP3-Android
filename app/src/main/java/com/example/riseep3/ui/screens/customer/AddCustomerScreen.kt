package com.example.riseep3.ui.screens.customer

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.riseep3.R
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.componenten.customer.CustomerTextField
import com.example.riseep3.ui.screens.fake.FakeThemeViewModel
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun AddCustomerScreen(
    themeViewModel: ThemeViewModel,
    onNavigateBack: () -> Unit = {},
    customerViewModel: CustomerViewModel = viewModel(factory = CustomerViewModel.Factory)
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var btwNumber by remember { mutableStateOf("") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val profileCircleSize = 130.dp
    val profileCircleOffset = -(profileCircleSize / 2)

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = "",
                isDarkTheme = isDarkTheme,
                onToggleTheme = themeViewModel::toggleTheme,
                showBackButton = true,
                onNavigateBack = onNavigateBack,
                showBottomGradient = false,
                modifier = Modifier.testTag("AddCustomerScreenTitle")
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF48617A)),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(40),
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .height(45.dp)
                        .width(140.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text(text = "Save", color = Color(0xFF48617A), fontSize = 18.sp)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 160.dp)
                    .background(Color(0xFF48617A), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            ) {
                Box(
                    modifier = Modifier
                        .size(profileCircleSize)
                        .align(Alignment.TopCenter)
                        .offset(y = profileCircleOffset)
                        .clip(CircleShape)
                        .background(Color.White)
                        .clickable { imagePicker.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(profileImageUri),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.image),
                            contentDescription = null,
                            tint = Color(0xFF1B3B5A),
                            modifier = Modifier
                                .size(50.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp, start = 24.dp, end = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ADD NEW CUSTOMER",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 30.dp),
                        thickness = 1.dp,
                        color = Color.White
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp, start = 24.dp, end = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        CustomerTextField(value = firstName, onValueChange = { firstName = it }, placeholder = "Firstname")
                        CustomerTextField(value = lastName, onValueChange = { lastName = it }, placeholder = "Lastname")
                        CustomerTextField(value = email, onValueChange = { email = it }, placeholder = "E-mail")
                        CustomerTextField(value = phone, onValueChange = { phone = it }, placeholder = "PhoneNumber")
                        CustomerTextField(value = btwNumber, onValueChange = { btwNumber = it }, placeholder = "BTW-Number") }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddCustomerScreenPreview() {
    AddCustomerScreen(
        themeViewModel = FakeThemeViewModel(),
        onNavigateBack = {}
    )
}
