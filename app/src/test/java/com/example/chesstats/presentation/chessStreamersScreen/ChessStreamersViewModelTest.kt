package com.example.chesstats.presentation.chessStreamersScreen

import app.cash.turbine.turbineScope
import com.example.chesstats.MotherObject
import com.example.chesstats.data.models.streamers.StreamerModel
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
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ChessStreamersViewModelTest {

    @MockK
    lateinit var chessRepositoryImp: ChessRepositoryImp

    lateinit var chessStreamersViewModel: ChessStreamersViewModel

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
    fun `getStreamers WHEN successful THEN updates leaderBoardLists and loading state`() = runTest {
        chessStreamersViewModel = ChessStreamersViewModel(chessRepositoryImp)

        coEvery { chessRepositoryImp.getStreamers() } returns ResultCase.Success(MotherObject.streamersList.streamers)

        turbineScope {
            val turbineStreamers = chessStreamersViewModel.streamers.testIn(backgroundScope)
            val turbineLoading= chessStreamersViewModel.loading.testIn(backgroundScope)
            val turbineError = chessStreamersViewModel.error.testIn(backgroundScope)

            assertEquals(turbineStreamers.awaitItem(),listOf<StreamerModel>())
            assertTrue(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(),"")
            advanceUntilIdle()
            assertEquals(turbineStreamers.awaitItem(), MotherObject.streamersList.streamers)
            assertFalse(turbineLoading.awaitItem())

            //Cancel turbines
            turbineStreamers.cancel()
            turbineLoading.cancel()
            turbineError.cancel()
        }
        coVerify(exactly = 1) { chessRepositoryImp.getStreamers() }
    }

    @Test
    fun `getStreamers WHEN error THEN updates leaderBoardLists and loading state`() = runTest {
        chessStreamersViewModel = ChessStreamersViewModel(chessRepositoryImp)

        coEvery { chessRepositoryImp.getStreamers() } returns ResultCase.Error("Error: 404")

        turbineScope {
            val turbineStreamers = chessStreamersViewModel.streamers.testIn(backgroundScope)
            val turbineLoading= chessStreamersViewModel.loading.testIn(backgroundScope)
            val turbineError = chessStreamersViewModel.error.testIn(backgroundScope)

            assertEquals(turbineStreamers.awaitItem(),listOf<StreamerModel>())
            assertTrue(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(),"")
            advanceUntilIdle()
            assertFalse(turbineLoading.awaitItem())
            assertEquals("Error: 404", turbineError.awaitItem())

            //Cancel turbines
            turbineStreamers.cancel()
            turbineLoading.cancel()
            turbineError.cancel()
        }

        coVerify(exactly = 1) { chessRepositoryImp.getStreamers() }
    }

}