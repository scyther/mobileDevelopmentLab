package com.example.p1.practical2.model

/**
 * Represents the category or operational style of a restaurant.
 */
enum class RestaurantType(val displayName: String, val description: String) {
    DINE_IN("Dine-In", "Full table service with indoor seating"),
    FAST_FOOD("Fast Food / Quick Service", "Counter service and quick takeaway"),
    CAFE("Cafe / Bistro", "Coffee, beverages, and light snacks"),
    DELIVERY_ONLY("Cloud Kitchen / Delivery", "Online orders and doorstep delivery only"),
    BUFFET("Buffet / Family Restaurant", "All-you-can-eat self-service spread")
}

/**
 * Represents optional operational amenities provided by a restaurant.
 */
enum class Amenity(val displayName: String) {
    AIR_CONDITIONED("AC"),
    WIFI("Free Wi-Fi"),
    PARKING("Valet Parking"),
    PURE_VEG("Pure Veg"),
    OUTDOOR_SEATING("Outdoor Seating")
}

/**
 * Represents a successfully registered Restaurant record.
 */
data class Restaurant(
    val id: Long = System.currentTimeMillis(),
    val name: String,
    val address: String,
    val phone: String,
    val type: RestaurantType,
    val amenities: Set<Amenity> = emptySet(),
    val averageCostForTwo: Int = 500
)
