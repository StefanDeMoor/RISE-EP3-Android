package com.example.riseep3.ui.componenten.customer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.riseep3.data.customer.CustomerEntity
import com.example.riseep3.ui.componenten.SectionHeader

@Composable
fun CustomerSection(
    customers: List<CustomerEntity>,
    searchQuery: String,
    onAddClick: () -> Unit,
    onImageClick: (CustomerEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SectionHeader(
            title = "Customers",
            onAddClick = onAddClick,
            invertedColors = true
        )

        val filteredCustomers = customers.filter {
            "${it.firstName} ${it.lastName}".contains(searchQuery, ignoreCase = true)
        }

        if (filteredCustomers.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No customers available",
                    modifier = Modifier.testTag("NoCustomersMessage")
                )
            }
        } else {
            filteredCustomers.forEach { customer ->
                CustomerCard(
                    name = "${customer.firstName} ${customer.lastName}",
                    phoneNumber = customer.phoneNumber,
                    profileImagePath = customer.profileImagePath,
                    onImageClick = { onImageClick(customer) }
                )
            }
        }
    }
}
