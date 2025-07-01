package com.example.ppab_responsi1_kelompok09.presentation.contact
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionContact
import com.example.ppab_responsi1_kelompok09.domain.model.Contact
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetContactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(
    private val getContactsUseCase: GetContactsUseCase,
    private val token: String
) : ViewModel() {
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    private val _initialLoading = MutableStateFlow(true)
    val initialLoading: StateFlow<Boolean> = _initialLoading

    private val _paginationLoading = MutableStateFlow(false)
    val paginationLoading: StateFlow<Boolean> = _paginationLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> = _isLastPage

    private val _totalContacts = MutableStateFlow(0)
    val totalContacts: StateFlow<Int> = _totalContacts

    init {
        fetchContacts(1)
    }

    fun fetchContacts(page: Int) {
        viewModelScope.launch {
            if (page == 1) _initialLoading.value = true else _paginationLoading.value = true
            _error.value = null

            try {
                val response = getContactsUseCase(token, page)

                if (page == 1) {
                    _contacts.value = response.data
                    _totalContacts.value = response.total
                } else {
                    _contacts.value = _contacts.value + response.data
                }

                _currentPage.value = page
                _isLastPage.value = page >= response.last_page
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                if (page == 1) _initialLoading.value = false else _paginationLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        if (_paginationLoading.value || _isLastPage.value) return
        val nextPage = _currentPage.value + 1
        fetchContacts(nextPage)
    }

    fun refreshContacts() {
        _contacts.value = emptyList()
        _currentPage.value = 1
        _isLastPage.value = false
        fetchContacts(1)
    }
}
