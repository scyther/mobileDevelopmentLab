package com.example.p1.practical2.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.p1.practical2.model.Amenity
import com.example.p1.practical2.model.Restaurant
import com.example.p1.practical2.model.RestaurantFormState

/**
 * Primary Screen for Practical 2: Modern Restaurant Data Entry Form.
 * Demonstrates:
 * - Nested Column and Row layouts
 * - Exploring maximum options of OutlinedTextField, RadioButton, Button, and Card
 * - Unidirectional Data Flow and State Hoisting
 * - Input validation with visual error feedback
 * - In-memory reactive list of registered restaurants
 */
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RestaurantFormScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    // Form state holder
    var formState by remember { mutableStateOf(RestaurantFormState()) }

    // Live reactive list of submitted restaurants
    val registeredRestaurants = remember { mutableStateListOf<Restaurant>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Form Header Section ---
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Restaurant Registration",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Fill in the details below to register a new dining establishment.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // --- Card 1: Basic Information (Column & OutlinedTextFields) ---
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Basic Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Restaurant Name (Exploring options of OutlinedTextField)
                OutlinedTextField(
                    value = formState.name,
                    onValueChange = { formState = formState.copy(name = it, nameError = null) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Restaurant Name *") },
                    placeholder = { Text("e.g. Spice Symphony") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Home, contentDescription = null)
                    },
                    trailingIcon = {
                        if (formState.name.isNotEmpty()) {
                            IconButton(onClick = { formState = formState.copy(name = "", nameError = null) }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Name")
                            }
                        }
                    },
                    isError = formState.nameError != null,
                    supportingText = {
                        formState.nameError?.let {
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        } ?: Text("Minimum 3 characters")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                // Address Field
                OutlinedTextField(
                    value = formState.address,
                    onValueChange = { formState = formState.copy(address = it, addressError = null) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Address / Location *") },
                    placeholder = { Text("e.g. 42 MG Road, Bengaluru") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                    },
                    trailingIcon = {
                        if (formState.address.isNotEmpty()) {
                            IconButton(onClick = { formState = formState.copy(address = "", addressError = null) }) {
                                Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear Address")
                            }
                        }
                    },
                    isError = formState.addressError != null,
                    supportingText = {
                        formState.addressError?.let {
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        } ?: Text("Enter full street address")
                    },
                    maxLines = 2,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                // Row containing Contact Number & Average Cost side-by-side
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Contact Number
                    OutlinedTextField(
                        value = formState.phone,
                        onValueChange = { input ->
                            // Allow only numeric digits up to 10 characters
                            if (input.length <= 10 && input.all { it.isDigit() }) {
                                formState = formState.copy(phone = input, phoneError = null)
                            }
                        },
                        modifier = Modifier.weight(1.2f),
                        label = { Text("Phone *") },
                        placeholder = { Text("9876543210") },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Phone, contentDescription = null)
                        },
                        isError = formState.phoneError != null,
                        supportingText = {
                            formState.phoneError?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            } ?: Text("${formState.phone.length}/10 digits")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone,
                            imeAction = ImeAction.Next
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Average Cost
                    OutlinedTextField(
                        value = formState.costForTwo,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() }) {
                                formState = formState.copy(costForTwo = input, costError = null)
                            }
                        },
                        modifier = Modifier.weight(0.8f),
                        label = { Text("Cost (₹)") },
                        placeholder = { Text("500") },
                        isError = formState.costError != null,
                        supportingText = {
                            formState.costError?.let {
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            } ?: Text("For two")
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // --- Card 2: Restaurant Type Selection (RadioGroup in Rows) ---
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Restaurant Type *",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "Select the operational category of your restaurant:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                // Our custom RadioGroup composable built with Column and Row
                RestaurantRadioGroup(
                    selectedType = formState.selectedType,
                    onTypeSelected = { selected ->
                        formState = formState.copy(selectedType = selected)
                    }
                )
            }
        }

        // --- Card 3: Amenities & Features (FilterChips in FlowRow) ---
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Available Amenities",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Tap to select the facilities available at your venue:",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Amenity.entries.forEach { amenity ->
                        val isChecked = formState.selectedAmenities.contains(amenity)
                        FilterChip(
                            selected = isChecked,
                            onClick = {
                                val updated = if (isChecked) {
                                    formState.selectedAmenities - amenity
                                } else {
                                    formState.selectedAmenities + amenity
                                }
                                formState = formState.copy(selectedAmenities = updated)
                            },
                            label = { Text(amenity.displayName) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                }
            }
        }

        // --- Form Action Buttons (Row with Reset & Submit) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Reset Button
            OutlinedButton(
                onClick = {
                    formState = RestaurantFormState()
                    focusManager.clearFocus()
                    Toast.makeText(context, "Form reset to defaults", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reset")
            }

            // Submit / Register Button
            Button(
                onClick = {
                    focusManager.clearFocus()
                    val (isValid, validatedState) = formState.validate()
                    formState = validatedState

                    if (isValid) {
                        val newRestaurant = validatedState.toRestaurant()
                        registeredRestaurants.add(0, newRestaurant) // Add to top of list
                        formState = RestaurantFormState() // Clear form after success
                        Toast.makeText(
                            context,
                            "🎉 \"${newRestaurant.name}\" registered successfully!",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "⚠️ Please fix the highlighted errors above.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .weight(1.5f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Register Restaurant",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // --- Dynamic Registered Restaurants Ledger ---
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Registered Establishments",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "(${registeredRestaurants.size})",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        if (registeredRestaurants.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "No restaurants registered yet",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Fill out the form above and click Register to add your first restaurant.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            registeredRestaurants.forEach { restaurant ->
                RestaurantRecordCard(
                    restaurant = restaurant,
                    onDelete = {
                        registeredRestaurants.remove(restaurant)
                        Toast.makeText(
                            context,
                            "Removed \"${restaurant.name}\"",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
