package com.moscow.tudee.data.service

import com.google.common.truth.Truth.assertThat
import com.moscow.tudee.data.local.dao.CategoryDao
import com.moscow.tudee.data.local.entity.CategoryEntity
import com.moscow.tudee.data.local.mapper.toCategoryEntity
import com.moscow.tudee.domain.entity.Category
import com.moscow.tudee.domain.service.CategoryServices
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryServicesImplTest {

    private lateinit var categoryDao: CategoryDao
    private lateinit var categoryServices: CategoryServices

    @BeforeEach
    fun setup() {
        categoryDao = mockk()
        categoryServices = CategoryServicesImpl(categoryDao)
    }

    @Test
    fun `getCategories should return mapped categories when list is not empty`() = runTest {
        // Given
        val categoryEntities = listOf(
            createCategoryRoomEntity(id = 1, title = "Category 1"),
            createCategoryRoomEntity(id = 2, title = "Category 2")
        )
        val expectedCategories = listOf(
            createCategoryDomainEntity(id = 1, title = "Category 1"),
            createCategoryDomainEntity(id = 2, title = "Category 2")
        )

        coEvery { categoryDao.getCategories() } returns categoryEntities

        // When
        val result = categoryServices.getCategories()

        // Then
        coVerify(exactly = 1) { categoryDao.getCategories() }
        assertThat(result).containsExactlyElementsIn(expectedCategories)
    }

    @Test
    fun `getCategories should return empty list when DAO returns empty list`() = runTest {
        // Given
        coEvery { categoryDao.getCategories() } returns emptyList()

        // When
        val result = categoryServices.getCategories()

        // Then
        coVerify(exactly = 1) { categoryDao.getCategories() }
        assertThat(result).isEmpty()
    }

    @Test
    fun `getCategories should return single item when DAO returns single item`() = runTest {
        // Given
        val categoryEntity = createCategoryRoomEntity(id = 3, title = "Single Category")
        val expectedCategory = createCategoryDomainEntity(id = 3, title = "Single Category")

        coEvery { categoryDao.getCategories() } returns listOf(categoryEntity)

        // When
        val result = categoryServices.getCategories()

        // Then
        coVerify(exactly = 1) { categoryDao.getCategories() }
        assertThat(result).containsExactly(expectedCategory)
    }

    @Test
    fun `addCategory should handle null id`() = runTest {
        // Given
        val category = createCategoryDomainEntity(id = null, title = "New Category")
        val expectedEntity = category.toCategoryEntity()
        coEvery { categoryDao.addCategory(any()) } just Runs

        // When
        categoryServices.addCategory(category)

        // Then
        coVerify(exactly = 1) {
            categoryDao.addCategory(match {
                it.id == expectedEntity.id &&
                        it.title == expectedEntity.title
            })
        }
    }

    @Test
    fun `updateCategory should call DAO`() = runTest {
        // Given
        val category = createCategoryDomainEntity(id = null, title = "Updated")
        val expectedEntity = category.toCategoryEntity()
        coEvery { categoryDao.updateCategory(any()) } just Runs

        // When
        categoryServices.updateCategory(category)

        // Then
        coVerify(exactly = 1) {
            categoryDao.updateCategory(match {
                it.id == expectedEntity.id &&
                        it.title == expectedEntity.title
            })
        }
    }

    @Test
    fun `updateCategory should handle null id by using zero`() = runTest {
        // Given
        val category = createCategoryDomainEntity(id = null, title = "No ID")
        coEvery { categoryDao.updateCategory(any()) } just Runs

        // When
        categoryServices.updateCategory(category)

        // Then
        coVerify {
            categoryDao.updateCategory(match { it.id == 0L })
        }
    }

    @Test
    fun `deleteCategory should call DAO with correct ID`() = runTest {
        // Given
        val categoryId = 99L
        coEvery { categoryDao.deleteCategory(any()) } just Runs

        // When
        categoryServices.deleteCategory(categoryId)

        // Then
        coVerify(exactly = 1) { categoryDao.deleteCategory(categoryId) }
    }

    private fun createCategoryRoomEntity(
        id: Long = 0L,
        title: String = "Education",
        iconUrl: String = "url1",
        countOfTasks: Int = 0
    ): CategoryEntity {
        return CategoryEntity(
            id = id,
            title = title,
            iconUri = iconUrl,
            countOfTasks = countOfTasks
        )
    }

    private fun createCategoryDomainEntity(
        id: Long? = 0L,
        title: String = "Education",
        iconUrl: String = "url1",
        countOfTasks: Int = 0
    ): Category {
        return Category(
            id = id,
            title = title,
            iconUri = iconUrl,
            countOfTasks = countOfTasks
        )
    }
}