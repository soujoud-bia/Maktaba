package com.ElOuedUniv.maktaba.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ElOuedUniv.maktaba.data.model.Book
import com.ElOuedUniv.maktaba.domain.usecase.AddBookUseCase
import com.ElOuedUniv.maktaba.domain.usecase.GetBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase ,
    private val addBookUseCase: AddBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(BookUiState())
    val uiState: StateFlow<BookUiState> = _uiState.asStateFlow()

    init {
        loadBooks()
    }

    fun loadBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getBooksUseCase()
                .catch { exception ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = exception.message) }
                }
                .collect { bookList ->
                    _uiState.update { it.copy(books = bookList, isLoading = false) }
                }
        }
    }
    // 1. تعريف الـ Event flow (تأكدي أنه مكتوب مرة واحدة فقط)
    private val _uiEvent = MutableSharedFlow<BookUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    // 2. دالة onAction (تأكدي أنها الوحيدة بهذا الاسم داخل الكلاس)
    fun onAction(action: BookUiAction) {
        when (action) {
            BookUiAction.OnAddBookClick -> {
                _uiState.update { it.copy(isAddingBook = true) }
            }
            BookUiAction.OnDismissAddBook -> {
                _uiState.update { it.copy(isAddingBook = false) }
            }
            is BookUiAction.OnAddBookConfirm -> {
                viewModelScope.launch {
                    addBookUseCase(action.book)
                    _uiState.update { it.copy(isAddingBook = false) }
                    // ملاحظة: تأكدي من وجود ShowToast في ملف BookUiEvent
                    // _uiEvent.emit(BookUiEvent.ShowToast("تمت الإضافة بنجاح"))
                }
            }
            BookUiAction.RefreshBooks -> {
                loadBooks()
            }
        }
    }

    fun refreshBooks() {
        loadBooks()
    }

}

