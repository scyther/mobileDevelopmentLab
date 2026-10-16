package com.paytm.mobileApp.theory.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Step 4: Bridge Room with a ViewModel
 * Exposes a StateFlow for Compose to observe and dispatches coroutine operations safely.
 */
class UserViewModel(private val userDao: UserDao) : ViewModel() {

    // Expose the Flow as a StateFlow for Compose to observe reactively
    val allUsers: StateFlow<List<User>> = userDao.getAllUsers()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addUser(name: String, age: Int) {
        viewModelScope.launch {
            userDao.insertUser(User(name = name, age = age))
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userDao.deleteUser(user)
        }
    }
}

/**
 * Factory class to instantiate UserViewModel with the UserDao dependency.
 */
class UserViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
