package com.paytm.mobileApp.theory.passbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class PassbookSummary(
    val totalBalance: Double = 0.0,
    val totalCredit: Double = 0.0,
    val totalDebit: Double = 0.0
)

class TransactionViewModel(private val dao: TransactionDao) : ViewModel() {

    val transactions: StateFlow<List<TransactionEntity>> = dao.getAllTransactions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val summary: StateFlow<PassbookSummary> = combine(
        dao.getTotalCredit(),
        dao.getTotalDebit()
    ) { credit, debit ->
        val cr = credit ?: 0.0
        val dr = debit ?: 0.0
        PassbookSummary(
            totalBalance = cr - dr,
            totalCredit = cr,
            totalDebit = dr
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PassbookSummary()
    )

    fun addTransaction(
        title: String,
        amount: Double,
        type: String,
        category: String
    ) {
        viewModelScope.launch {
            val transaction = TransactionEntity(
                title = title,
                amount = amount,
                type = type,
                category = category
            )
            dao.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: TransactionEntity) {
        viewModelScope.launch {
            dao.deleteTransaction(transaction)
        }
    }
}

class TransactionViewModelFactory(private val dao: TransactionDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
