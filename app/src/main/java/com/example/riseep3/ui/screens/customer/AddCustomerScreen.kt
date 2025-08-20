package com.example.riseep3.ui.screens.customer

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.riseep3.ui.theme.CustomerTheme
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun AddCustomerScreen(
    themeViewModel: ThemeViewModel,
    onNavigateBack: () -> Unit = {},
) {
    val addCustomerViewModel: AddCustomerViewModel = viewModel(factory = AddCustomerViewModel.Factory)
    val state by addCustomerViewModel.state.collectAsState()

    var profileImageUri = state.profileImageUri

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val profileCircleSize = 130.dp
    val profileCircleOffset = -(profileCircleSize / 2)

    CustomerTheme(darkTheme = isDarkTheme) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            topBar = {
                TopBar(
                    title = "",
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = themeViewModel::toggleTheme,
                    showBackButton = true,
                    onNavigateBack = onNavigateBack,
                    showBottomGradient = false,
                    invertedColors = true,
                    modifier = Modifier.testTag("AddCustomerScreenTitle")
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { addCustomerViewModel.saveCustomer { onNavigateBack() } },
                        shape = RoundedCornerShape(45),
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .height(45.dp)
                            .width(140.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Text(text = "Save", color = MaterialTheme.colorScheme.primary, fontSize = 18.sp)
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
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .size(profileCircleSize)
                            .align(Alignment.TopCenter)
                            .offset(y = profileCircleOffset)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
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
                                tint = MaterialTheme.colorScheme.primary,
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
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 30.dp),
                            thickness = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp, start = 24.dp, end = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            CustomerTextField(value = state.firstName, onValueChange = addCustomerViewModel::updateFirstName, placeholder = "Firstname")
                            CustomerTextField(value = state.lastName, onValueChange = addCustomerViewModel::updateLastName, placeholder = "Lastname")
                            CustomerTextField(value = state.email, onValueChange = addCustomerViewModel::updateEmail, placeholder = "E-mail")
                            CustomerTextField(value = state.phone, onValueChange = addCustomerViewModel::updatePhone, placeholder = "PhoneNumber")
                            CustomerTextField(value = state.btwNumber, onValueChange = addCustomerViewModel::updateBtwNumber, placeholder = "BTW-Number") }
                    }
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
