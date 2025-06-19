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
import kotlin.String

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



    private fun createCategoryRoomEntity(
        id: Long = 0L,
        title: String = "Education",
        iconUrl: String = "url1",
        countOfTasks: Int = 0
    ): CategoryEntity {
        return CategoryEntity(
            id = id,
            title = title,
            iconUrl = iconUrl,
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
            iconUrl = iconUrl,
            countOfTasks = countOfTasks
        )
    }
}