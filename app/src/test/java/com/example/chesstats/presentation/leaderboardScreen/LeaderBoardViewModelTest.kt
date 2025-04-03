package com.example.chesstats.presentation.leaderboardScreen

import app.cash.turbine.turbineScope
import com.example.chesstats.MotherObject
import com.example.chesstats.domain.repositories.ChessRepository
import com.example.chesstats.utils.ResultCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LeaderBoardViewModelTest {

    @MockK
    lateinit var chessRepositoryImp: ChessRepository

    lateinit var leaderBoardViewModel: LeaderBoardViewModel

    @get:Rule
    private val testDispatcher = StandardTestDispatcher()

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
    fun `getLeaderBoards WHEN successful THEN updates leaderBoardLists and loading state`() = runTest {

        leaderBoardViewModel = LeaderBoardViewModel(chessRepositoryImp)

        coEvery { chessRepositoryImp.getLeaderBoards() } returns ResultCase.Success(MotherObject.leaderboard)

        turbineScope {
            val turbineLoading = leaderBoardViewModel.loading.testIn(backgroundScope)
            val turbineLeaderBoard = leaderBoardViewModel.leaderBoardLists.testIn(backgroundScope)

            assertTrue(turbineLoading.awaitItem())
            assertNull(turbineLeaderBoard.awaitItem())

            assertFalse(turbineLoading.awaitItem())
            assertEquals(turbineLeaderBoard.awaitItem(), MotherObject.leaderboard)

            //Cancel turbines
             turbineLoading.cancel()
             turbineLeaderBoard.cancel()
        }
    }

    @Test
    fun `getLeaderBoards WHEN error THEN updates message and loading state`() = runTest {

        leaderBoardViewModel = LeaderBoardViewModel(chessRepositoryImp)

        coEvery { chessRepositoryImp.getLeaderBoards() } returns ResultCase.Error("Error: 404")

        turbineScope {
            val turbineLoading = leaderBoardViewModel.loading.testIn(backgroundScope)
            val turbineLeaderBoard = leaderBoardViewModel.leaderBoardLists.testIn(backgroundScope)
            val turbineError = leaderBoardViewModel.error.testIn(backgroundScope)

            assertTrue(turbineLoading.awaitItem())
            assertNull(turbineLeaderBoard.awaitItem())
            assertEquals(turbineError.awaitItem(), "")

            assertFalse(turbineLoading.awaitItem())
            assertEquals(turbineError.awaitItem(),"Error: 404")

            //Cancel

             turbineLoading.cancel()
             turbineLeaderBoard.cancel()
             turbineError.cancel()
        }
    }

}