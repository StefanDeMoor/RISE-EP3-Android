//package com.example.riseep3.screens
//
//import android.annotation.SuppressLint
//import androidx.activity.ComponentActivity
//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createAndroidComposeRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.example.riseep3.data.customer.CustomerEntity
//import com.example.riseep3.ui.screens.customer.CustomerScreen
//import com.example.riseep3.ui.screens.customer.CustomerState
//import com.example.riseep3.ui.screens.customer.CustomerViewModel
//import com.example.riseep3.ui.screens.fake.FakeCustomerRepository
//import com.example.riseep3.ui.screens.fake.FakeCustomerViewModel
//import com.example.riseep3.ui.screens.fake.FakeThemeViewModel
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.runBlocking
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//
//@RunWith(AndroidJUnit4::class)
//class CustomerScreenTest {
//
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
//
//    private val testCustomers = listOf(
//        CustomerEntity(
//            id = 1,
//            firstName = "John",
//            lastName = "Doe",
//            email = "john@example.com",
//            phoneNumber = "1234567890",
//            profileImagePath = null
//        ),
//        CustomerEntity(
//            id = 2,
//            firstName = "Jane",
//            lastName = "Smith",
//            email = "jane@example.com",
//            phoneNumber = "0987654321",
//            profileImagePath = null
//        )
//    )
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun customerScreen_displaysTitle_usingTag() {
//        val fakeRepo = FakeCustomerRepository()
//        runBlocking { fakeRepo.insertAll(flowOf(testCustomers)) }
//        val vm = CustomerViewModel(fakeRepo)
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithTag("CustomerScreenTitle").assertIsDisplayed()
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun customerScreen_backButtonClick_triggersCallback() {
//        val fakeRepo = FakeCustomerRepository()
//        runBlocking { fakeRepo.insertAll(flowOf(testCustomers)) }
//        val vm = CustomerViewModel(fakeRepo)
//
//        var backClicked = false
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = { backClicked = true }
//            )
//        }
//
//        composeTestRule.onNodeWithContentDescription("Back").assertIsDisplayed().performClick()
//        composeTestRule.waitForIdle()
//        assert(backClicked)
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun customerScreen_toggleTheme_changesThemeValue() {
//        val fakeRepo = FakeCustomerRepository()
//        runBlocking { fakeRepo.insertAll(flowOf(testCustomers)) }
//        val vm = CustomerViewModel(fakeRepo)
//        val fakeTheme = FakeThemeViewModel()
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = fakeTheme,
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithTag("ThemeToggleButton").assertIsDisplayed().performClick()
//        composeTestRule.waitForIdle()
//
//        assert(fakeTheme.isDarkTheme.value)
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun searchBar_filtersCustomerList() {
//        val fakeRepo = FakeCustomerRepository()
//        runBlocking { fakeRepo.insertAll(flowOf(testCustomers)) }
//        val vm = CustomerViewModel(fakeRepo)
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Jane Smith").assertIsDisplayed()
//
//        composeTestRule.onNodeWithTag("SearchBar").performTextInput("Jane")
//        composeTestRule.waitForIdle()
//
//        composeTestRule.onNodeWithText("Jane Smith").assertIsDisplayed()
//        composeTestRule.onNodeWithText("John Doe").assertDoesNotExist()
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun errorMessage_isShownWhenRepositoryThrows() {
//        val errorRepo = object : FakeCustomerRepository() {
//            override fun getAllCustomers() = kotlinx.coroutines.flow.flow<List<CustomerEntity>> {
//                throw RuntimeException("Test Error")
//            }
//        }
//        val vm = CustomerViewModel(errorRepo)
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.waitForIdle()
//        composeTestRule.onNodeWithText("Test Error").assertIsDisplayed()
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun clickingCustomerCard_and_manualUpdate_updatesProfileImageInState() {
//        val fakeRepo = FakeCustomerRepository()
//        runBlocking { fakeRepo.insertAll(flowOf(testCustomers)) }
//        val vm = CustomerViewModel(fakeRepo)
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed().performClick()
//
//        composeTestRule.runOnUiThread {
//            vm.updateProfileImage(1, "dummy/path/to/image.jpg")
//        }
//        composeTestRule.waitForIdle()
//
//        val updated = vm.state.value.customers.first { it.id == 1 }
//        assert(updated.profileImagePath == "dummy/path/to/image.jpg")
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun loadingIndicator_isShown_whenLoadingTrue() {
//        val vm = FakeCustomerViewModel(
//            CustomerState(customers = emptyList(), isLoading = true),
//            FakeCustomerRepository()
//        )
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithTag("LoadingIndicator").assertIsDisplayed()
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun emptyList_showsNoCustomersMessage() {
//        val vm = FakeCustomerViewModel(
//            CustomerState(customers = emptyList(), isLoading = false),
//            FakeCustomerRepository()
//        )
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithTag("NoCustomersMessage").assertIsDisplayed()
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun themeToggle_twice_returnsToOriginalValue() {
//        val vm = CustomerViewModel(FakeCustomerRepository())
//        val fakeTheme = FakeThemeViewModel()
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = fakeTheme,
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithTag("ThemeToggleButton").performClick()
//        composeTestRule.onNodeWithTag("ThemeToggleButton").performClick()
//        composeTestRule.waitForIdle()
//
//        assert(!fakeTheme.isDarkTheme.value)
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun searchBar_clearQuery_resetsList() {
//        val fakeRepo = FakeCustomerRepository()
//        runBlocking { fakeRepo.insertAll(flowOf(testCustomers)) }
//        val vm = CustomerViewModel(fakeRepo)
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithTag("SearchBar").performTextInput("Jane")
//        composeTestRule.waitForIdle()
//        composeTestRule.onNodeWithTag("SearchBar").performTextClearance()
//        composeTestRule.waitForIdle()
//
//        composeTestRule.onNodeWithText("John Doe").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Jane Smith").assertIsDisplayed()
//    }
//
//    @SuppressLint("ViewModelConstructorInComposable")
//    @Test
//    fun searchBar_caseInsensitiveSearch() {
//        val fakeRepo = FakeCustomerRepository()
//        runBlocking { fakeRepo.insertAll(flowOf(testCustomers)) }
//        val vm = CustomerViewModel(fakeRepo)
//
//        composeTestRule.setContent {
//            CustomerScreen(
//                themeViewModel = FakeThemeViewModel(),
//                customerViewModel = vm,
//                onNavigateBack = {}
//            )
//        }
//
//        composeTestRule.onNodeWithTag("SearchBar").performTextInput("jAnE")
//        composeTestRule.waitForIdle()
//
//        composeTestRule.onNodeWithText("Jane Smith").assertIsDisplayed()
//        composeTestRule.onNodeWithText("John Doe").assertDoesNotExist()
//    }
//}
