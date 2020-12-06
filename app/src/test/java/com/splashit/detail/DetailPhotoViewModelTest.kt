package com.splashit.detail

import com.splashit.core.domain.usecase.IPhotoUseCase
import com.splashit.core.ui.model.PhotoModel
import com.splashit.core.ui.model.UserModel
import com.splashit.core.ui.model.toDomain
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailPhotoViewModelTest {

    @MockK(relaxed = true)
    private lateinit var useCase: IPhotoUseCase

    private lateinit var viewModel: DetailPhotoViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DetailPhotoViewModel(useCase)
    }

    @Test
    fun checkIfLikesIsTrue() {
        val photo = PhotoModel(
            id = "",
            createdDate = "",
            dimension = "2250x1728",
            description = "",
            url = "",
            likes = 0,
            user = UserModel("", "", "", "", ""),
            isFav = false
        )
        val domainPhoto = photo.toDomain()
        every { useCase.setFavoritePhoto(domainPhoto) } answers {}
        val result = viewModel.setFavoritePhoto(photo)
        assertEquals(true, result)
    }
}