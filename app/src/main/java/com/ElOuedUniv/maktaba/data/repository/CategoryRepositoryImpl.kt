package com.ElOuedUniv.maktaba.data.repository

import com.ElOuedUniv.maktaba.data.model.Category
class CategoryRepositoryImpl : CategoryRepository {

    private val categoriesList = listOf(
        Category(
            id = "1",
            name = "Programming",
            description = "Books about software development and coding"
        ),
        Category(
            id = "2",
            name = "Algorithms",
            description = "Books about algorithms and data structures"
        ),
        Category(
            id = "3",
            name = "Databases",
            description = "Books about database design and management"
        ),
        Category(
            id = "4",
            name = "Web Development",
            description = "Books about building web applications"
        ),
        Category(
            id = "5",
            name = "Artificial Intelligence",
            description = "Books about AI and machine learning"
        )
    )

    override fun getAllCategories(): List<Category> {
        return categoriesList
    }

    override fun getCategoryById(id: String): Category? {
        return categoriesList.find { it.id == id }
    }
}
