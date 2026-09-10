package com.example.p1.practical2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.p1.practical2.model.RestaurantType

/**
 * A custom RadioButton selection group built using Column and Row composables.
 * Demonstrates:
 * - Mutually exclusive selection logic
 * - Modifier. Selectable for accessibility and whole-row clicking
 * - RadioButtonDefaults parameter customization
 * - Nested Column & Row organization
 */
@Composable
fun RestaurantRadioGroup(
    selectedType: RestaurantType,
    onTypeSelected: (RestaurantType) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.selectableGroup(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        RestaurantType.entries.forEach { type ->
            val isSelected = (type == selectedType)

            // Each option is a full-width interactive Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = isSelected,
                        onClick = { onTypeSelected(type) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // RadioButton with customized colors
                RadioButton(
                    selected = isSelected,
                    onClick = null, // Handled by parent Row selectable modifier for larger tap target
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary,
                        unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Label and description in a vertical Column
                Column {
                    Text(
                        text = type.displayName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = type.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
