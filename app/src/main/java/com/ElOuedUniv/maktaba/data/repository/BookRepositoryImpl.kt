package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Book

class BookRepositoryImpl : BookRepository {


        private val booksList = listOf(
            Book(isbn = "978-9-17-699426-9", title = "Clean Code", nbPages = 120),
            Book(isbn = "978-2-32-201722-5", title = "The Pragmatic Programmer", nbPages = 540),
            Book(isbn = "978-0-59-600712-6", title = "Design Patterns", nbPages = 638),
            Book(isbn = "978-0-59-600874-1", title = "Refactoring", nbPages = 220),
            Book(isbn = "978-0-13-276409-4", title = "Code Complete", nbPages = 496),
            Book(isbn = "979-8-59-236528-7", title = "Head First Java", nbPages = 286),
            Book(isbn = "978-1-68-392622-1", title = "The Mythical Man-Month", nbPages = 572),
            Book(isbn = "978-0-13-277804-6", title = "Effective Java", nbPages = 384),
            Book(isbn = "978-1-49-190526-5", title = "You Don’t Know JS Yet", nbPages = 278)
        )

    
    override fun getAllBooks(): List<Book> {
        return booksList
    }

    override fun getBookByIsbn(isbn: String): Book? {
        return booksList.find { it.isbn == isbn }
    }
}

