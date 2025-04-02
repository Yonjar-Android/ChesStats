package com.example.chesstats.presentation.firstScreen

import app.cash.turbine.turbineScope
import com.example.chesstats.MotherObject
import com.example.chesstats.data.repositories.ChessRepositoryImp
import com.example.chesstats.utils.ResultCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
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
class FirstScreenViewModelTest {

    @MockK
    lateinit var chessRepositoryImp: ChessRepositoryImp

    lateinit var firstScreenViewModel: FirstScreenViewModel

    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
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

        firstScreenViewModel = FirstScreenViewModel(chessRepositoryImp)

        // Configura el mock para getPlayerInfo
        coEvery { chessRepositoryImp.getPlayerInfo("magnuscarlsen") } returns ResultCase.Success(testPlayer)

        // Configura el mock para getCountryFromPlayer con el país específico que esperas
        coEvery { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) } returns ResultCase.Success(testCountry)

        turbineScope {
            val turbinePlayer = firstScreenViewModel.player.testIn(backgroundScope)
            val turbineLoading = firstScreenViewModel.loading.testIn(backgroundScope)
            val turbineError = firstScreenViewModel.error.testIn(backgroundScope)

            // Initial states
            assertEquals(turbinePlayer.awaitItem(), null)
            assertTrue(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(), "")

            // After loading completes
            assertFalse(turbineLoading.awaitItem())
            val updatedPlayer = turbinePlayer.awaitItem()
            assertEquals(updatedPlayer, testPlayer.copy(countryName = testCountry.name))

            // Cancel turbines
            turbinePlayer.cancel()
            turbineLoading.cancel()
            turbineError.cancel()
        }

        // Verify interactions
        coVerify(exactly = 1) { chessRepositoryImp.getPlayerInfo("magnuscarlsen") }
        coVerify(exactly = 1) { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) }
    }

    @Test
    fun `searchPlayer WHEN error THEN updates error and loading state`() = runTest {
        // Given
        val testPlayer = MotherObject.playerDomainTest
        val testCountry = MotherObject.country

        firstScreenViewModel = FirstScreenViewModel(chessRepositoryImp)

        // Configura el mock para getPlayerInfo
        coEvery { chessRepositoryImp.getPlayerInfo("magnuscarlsen") } returns ResultCase.Error("Error: 404")

        // Configura el mock para getCountryFromPlayer con el país específico que esperas
        coEvery { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) } returns ResultCase.Success(testCountry)

        turbineScope {
            val turbinePlayer = firstScreenViewModel.player.testIn(backgroundScope)
            val turbineLoading = firstScreenViewModel.loading.testIn(backgroundScope)
            val turbineError = firstScreenViewModel.error.testIn(backgroundScope)

            // Initial states
            assertEquals(turbinePlayer.awaitItem(), null)
            assertTrue(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(), "")

            // After loading completes
            assertFalse(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(),"Error: 404")

            // Cancel turbines
            turbinePlayer.cancel()
            turbineLoading.cancel()
            turbineError.cancel()
        }

        // Verify interactions
        coVerify(exactly = 1) { chessRepositoryImp.getPlayerInfo("magnuscarlsen") }
        coVerify(exactly = 0) { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) }
    }

    @Test
    fun `getCountry WHEN error THEN updates error and loading state`() = runTest {
        // Given
        val testPlayer = MotherObject.playerDomainTest

        firstScreenViewModel = FirstScreenViewModel(chessRepositoryImp)

        // Configura el mock para getPlayerInfo
        coEvery { chessRepositoryImp.getPlayerInfo("magnuscarlsen") } returns ResultCase.Success(testPlayer)

        // Configura el mock para getCountryFromPlayer con el país específico que esperas
        coEvery { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) } returns ResultCase.Error("Error: countryError")

        turbineScope {
            val turbinePlayer = firstScreenViewModel.player.testIn(backgroundScope)
            val turbineLoading = firstScreenViewModel.loading.testIn(backgroundScope)
            val turbineError = firstScreenViewModel.error.testIn(backgroundScope)

            // Initial states
            assertEquals(turbinePlayer.awaitItem(), null)
            assertTrue(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(), "")

            // After loading completes
            assertFalse(turbineLoading.awaitItem())
            assertEquals(turbinePlayer.awaitItem(), testPlayer)
            assertEquals(turbineError.awaitItem(),"Error: countryError")

            // Cancel turbines
            turbinePlayer.cancel()
            turbineLoading.cancel()
            turbineError.cancel()
        }

        // Verify interactions
        coVerify(exactly = 1) { chessRepositoryImp.getPlayerInfo("magnuscarlsen") }
        coVerify(exactly = 1) { chessRepositoryImp.getCountryFromPlayer(testPlayer.country) }
    }

}