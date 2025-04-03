package com.example.chesstats.presentation.detailPlayerScreen

import app.cash.turbine.turbineScope
import com.example.chesstats.MotherObject
import com.example.chesstats.domain.repositories.ChessRepository
import com.example.chesstats.utils.ResultCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class DetailPlayerViewModelTest {

    @MockK
    lateinit var chessRepositoryImp: ChessRepository

    lateinit var detailPlayerViewModel: DetailPlayerViewModel

    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        detailPlayerViewModel = DetailPlayerViewModel(chessRepositoryImp)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `searchPlayer WHEN successful THEN updates player and loading state`() = runTest {
        // Given
        val testPlayer = MotherObject.playerDomainTest
        val testCountry = MotherObject.country
        val username = "Yonjar"

        coEvery { chessRepositoryImp.getPlayerInfo(username) } returns ResultCase.Success(testPlayer)
        coEvery { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) } returns ResultCase.Success(testCountry)

        turbineScope {
            val turbinePlayer = detailPlayerViewModel.player.testIn(backgroundScope)
            val turbineLoading = detailPlayerViewModel.loading.testIn(backgroundScope)

            // Estado inicial
            assertEquals(turbinePlayer.awaitItem(), null)
            assertFalse(turbineLoading.awaitItem())

            // Act: Llama a searchPlayer
            detailPlayerViewModel.searchPlayer(username)

            // Loading true
            assertTrue(turbineLoading.awaitItem())

            // Loading false + player actualizado
            assertFalse(turbineLoading.awaitItem())
            assertEquals(
                turbinePlayer.awaitItem(),
                testPlayer.copy(countryName = testCountry.name)
            )

            // No debería haber más emisiones
            turbinePlayer.cancel()
            turbineLoading.cancel()
        }

        coVerify(exactly = 1) { chessRepositoryImp.getPlayerInfo(username) }
        coVerify(exactly = 1) { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) }
    }

    @Test
    fun `searchPlayer WHEN error THEN updates error and loading state`() = runTest {
        // Given
        val testPlayer = MotherObject.playerDomainTest
        val testCountry = MotherObject.country
        val username = "Yonjar"

        coEvery { chessRepositoryImp.getPlayerInfo(username) } returns ResultCase.Error("Error: 404")
        coEvery { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) } returns ResultCase.Success(testCountry)

        turbineScope {
            val turbineLoading = detailPlayerViewModel.loading.testIn(backgroundScope)
            val turbineError = detailPlayerViewModel.error.testIn(backgroundScope)

            // Estado inicial
            assertFalse(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(),"")

            // Act: Llama a searchPlayer
            detailPlayerViewModel.searchPlayer(username)

            // Loading true
            assertTrue(turbineLoading.awaitItem())

            // Loading false + error actualizado
            assertFalse(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(), "Error: 404")

            // No debería haber más emisiones
            turbineError.cancel()
            turbineLoading.cancel()
        }

        coVerify(exactly = 1) { chessRepositoryImp.getPlayerInfo(username) }
        coVerify(exactly = 0) { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) }
    }

    @Test
    fun `getCountry WHEN error THEN updates error and loading state`() = runTest {
        // Given
        val testPlayer = MotherObject.playerDomainTest
        val username = "Yonjar"

        coEvery { chessRepositoryImp.getPlayerInfo(username) } returns ResultCase.Success(testPlayer)
        coEvery { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) } returns ResultCase.Error("Error: 404")

        turbineScope {
            val turbineLoading = detailPlayerViewModel.loading.testIn(backgroundScope)
            val turbinePlayer = detailPlayerViewModel.player.testIn(backgroundScope)
            val turbineError = detailPlayerViewModel.error.testIn(backgroundScope)

            // Estado inicial
            assertFalse(turbineLoading.awaitItem())
            assertNull(turbinePlayer.awaitItem())
            assertEquals(turbineError.awaitItem(),"")

            // Act: Llama a searchPlayer
            detailPlayerViewModel.searchPlayer(username)

            // Loading true
            assertTrue(turbineLoading.awaitItem())

            // Loading false + error actualizado
            assertFalse(turbineLoading.awaitItem())
            assertEquals(turbinePlayer.awaitItem(), testPlayer)
            assertEquals(turbineError.awaitItem(), "Error: 404")

            // No debería haber más emisiones
            turbineError.cancel()
            turbineLoading.cancel()
            turbinePlayer.cancel()
        }

        coVerify(exactly = 1) { chessRepositoryImp.getPlayerInfo(username) }
        coVerify(exactly = 1) { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) }
    }

}