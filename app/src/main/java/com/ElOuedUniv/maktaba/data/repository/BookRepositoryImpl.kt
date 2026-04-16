package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor() : BookRepository {

    // 1. حولنا القائمة إلى mutableListOf لنستطيع الإضافة إليها
    private val _booksList = mutableListOf(
        Book(isbn = "11111", title = "Clean Code", nbPages = 10),
        Book(isbn = "22222", title = "The Pragmatic Programmer", nbPages = 0),
        Book(isbn = "33333", title = "Design Patterns", nbPages = 0),
        Book(isbn = "44444", title = "Refactoring", nbPages = 0),
        Book(isbn = "55555", title = "Head First Design Patterns", nbPages = 0)
    )

    // 2. تأكدي من تسمية الـ Flow بـ booksFlow (بدون _) لتطابق دالة الإضافة
    private val booksFlow = MutableSharedFlow<List<Book>>(replay = 1).apply {
        tryEmit(_booksList.toList())
    }

    override fun getAllBooks(): Flow<List<Book>> = booksFlow


    override fun getBookByIsbn(isbn: String): Book? {
        return _booksList.find { it.isbn == isbn }
    }

    // 3. تصحيح دالة الإضافة لتستخدم الأسماء الصحيحة
    override suspend fun addBook(book: Book) {
        // إضافة الكتاب للقائمة الفعلية
        _booksList.add(book)

        // إرسال القائمة المحدثة عبر الـ Flow ليراها الـ ViewModel والـ UI
        booksFlow.emit(_booksList.toList())
    }
}