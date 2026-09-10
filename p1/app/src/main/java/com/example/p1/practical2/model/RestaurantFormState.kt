package com.example.p1.practical2.model

/**
 * State holder for the Restaurant Data Entry Form.
 * Demonstrates state management, holding both user input values and validation error messages.
 */
data class RestaurantFormState(
    val name: String = "",
    val nameError: String? = null,

    val address: String = "",
    val addressError: String? = null,

    val phone: String = "",
    val phoneError: String? = null,

    val selectedType: RestaurantType = RestaurantType.DINE_IN,

    val selectedAmenities: Set<Amenity> = emptySet(),

    val costForTwo: String = "500",
    val costError: String? = null
) {
    /**
     * Validates all input fields and returns a new state with updated error messages.
     * Returns true if all fields are valid, along with the updated state.
     */
    fun validate(): Pair<Boolean, RestaurantFormState> {
        val nameErr = when {
            name.trim().isEmpty() -> "Restaurant name cannot be empty"
            name.trim().length < 3 -> "Name must be at least 3 characters"
            else -> null
        }

        val addressErr = when {
            address.trim().isEmpty() -> "Address cannot be empty"
            address.trim().length < 5 -> "Please provide a detailed address"
            else -> null
        }

        val phoneErr = when {
            phone.trim().isEmpty() -> "Phone number is required"
            !phone.matches(Regex("^[6-9]\\d{9}$")) -> "Enter a valid 10-digit mobile number"
            else -> null
        }

        val costInt = costForTwo.toIntOrNull()
        val costErr = when {
            costInt == null || costInt <= 0 -> "Please enter a valid cost (e.g. 500)"
            else -> null
        }

        val hasErrors = nameErr != null || addressErr != null || phoneErr != null || costErr != null
        val validatedState = copy(
            nameError = nameErr,
            addressError = addressErr,
            phoneError = phoneErr,
            costError = costErr
        )

        return Pair(!hasErrors, validatedState)
    }

    /**
     * Converts a validated form state into a domain Restaurant object.
     */
    fun toRestaurant(): Restaurant {
        return Restaurant(
            name = name.trim(),
            address = address.trim(),
            phone = phone.trim(),
            type = selectedType,
            amenities = selectedAmenities,
            averageCostForTwo = costForTwo.toIntOrNull() ?: 500
        )
    }
}
