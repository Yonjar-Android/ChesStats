package com.example.chesstats.data.repositories

import com.example.chesstats.MotherObject
import com.example.chesstats.data.network.services.PlayerService
import com.example.chesstats.utils.ResultCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response.success

//import org.junit.jupiter.api.Assertions.*

class ChessRepositoryImpTest {

    @MockK
    lateinit var playerService: PlayerService

    lateinit var repositoryImp: ChessRepositoryImp

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repositoryImp = ChessRepositoryImp(playerService)
    }

    @Test
    fun `getPlayerInfo WHEN player and stats exist THEN returns Success with mapped domain model`() =
        runBlocking {
            //Given
            coEvery { playerService.getPlayerInfo("Yonjar") } returns success(MotherObject.profileDataTest)
            coEvery { playerService.getPlayerStatsInfo("Yonjar") } returns success(MotherObject.profileDataStatsTest)
            //When
            val player = repositoryImp.getPlayerInfo("Yonjar")

            val response = player as ResultCase.Success
            //Then

            coVerify(exactly = 1) { playerService.getPlayerInfo("Yonjar") }
            coVerify(exactly = 1) { playerService.getPlayerStatsInfo("Yonjar") }

            assertEquals(
                MotherObject.profileDataStatsTest.chessBlitz?.best?.rating,
                (response.data.eloStats?.blitzStats?.best)
            )

            assertEquals(
                MotherObject.profileDataStatsTest.chessRapid?.best?.rating,
                (response.data.eloStats?.rapidStats?.best)
            )

            assertEquals(MotherObject.profileDataTest.playerId, response.data.playerId)

            assertEquals(MotherObject.profileDataTest.username, response.data.username)
        }

    @Test
    fun `getPlayerInfo WHEN player data is null THEN returns Error with 'Player not found' message`() =
        runBlocking {
            //Given
            coEvery { playerService.getPlayerInfo("Yonjar") } returns success(null)
            coEvery { playerService.getPlayerStatsInfo("Yonjar") } returns success(MotherObject.profileDataStatsTest)
            //When
            val player = repositoryImp.getPlayerInfo("Yonjar")

            val response = player as ResultCase.Error
            //Then

            coVerify(exactly = 1) { playerService.getPlayerInfo("Yonjar") }
            coVerify(exactly = 1) { playerService.getPlayerStatsInfo("Yonjar") }

            assertEquals(response.message, "Error: No se encontró al jugador")
        }

    @Test
    fun `getPlayerInfo WHEN stats service throws exception THEN returns Error with exception message`() =
        runBlocking {
            //Given
            coEvery { playerService.getPlayerInfo("Yonjar") } returns success(MotherObject.profileDataTest)
            coEvery { playerService.getPlayerStatsInfo("Yonjar") } throws Exception("Ocurrio un fallo al obtener los datos")
            //When
            val player = repositoryImp.getPlayerInfo("Yonjar")

            val response = player as ResultCase.Error
            //Then

            coVerify(exactly = 1) { playerService.getPlayerInfo("Yonjar") }
            coVerify(exactly = 1) { playerService.getPlayerStatsInfo("Yonjar") }

            assertEquals(response.message, "Error: Ocurrio un fallo al obtener los datos")
        }

    @Test
    fun `getLeaderBoards WHEN data is available THEN returns Success with leaderboard data`() =
        runBlocking {
            //Given
            coEvery { playerService.getLeaderBoards() } returns success(MotherObject.leaderboard)

            //When
            val leaderBoards = repositoryImp.getLeaderBoards()
            val response = leaderBoards as ResultCase.Success

            //Then
            coVerify(exactly = 1) { playerService.getLeaderBoards() }

            assertNotNull(response.data.blitz)
            assertNotNull(response.data.rapid)
            assertNotNull(response.data.bullet)
            assertEquals(response.data, MotherObject.leaderboard)
        }

    @Test
    fun `getLeaderBoards WHEN data is null THEN returns Error with 'unable to load' message`() =
        runBlocking {
            //Given
            coEvery { playerService.getLeaderBoards() } returns success(null)

            //When
            val leaderBoards = repositoryImp.getLeaderBoards()
            val response = leaderBoards as ResultCase.Error

            //Then
            coVerify(exactly = 1) { playerService.getLeaderBoards() }

            assertNotNull(response.message, "Error: No fue posible cargar los datos")
        }

    @Test
    fun `getLeaderBoards WHEN service throws exception THEN returns Error with exception message`() =
        runBlocking {
            //Given
            coEvery { playerService.getLeaderBoards() } throws Exception("Ocurrio un fallo al obtener los datos")

            //When
            val leaderBoards = repositoryImp.getLeaderBoards()
            val response = leaderBoards as ResultCase.Error

            //Then
            coVerify(exactly = 1) { playerService.getLeaderBoards() }

            assertEquals(response.message, "Error: Ocurrio un fallo al obtener los datos")
        }

    @Test
    fun `getStreamers WHEN data is available THEN returns Success with leaderboard data`() =
        runBlocking {
            //Given
            coEvery { playerService.getStreamers() } returns success(MotherObject.streamersList)

            //When
            val leaderBoards = repositoryImp.getStreamers()
            val response = leaderBoards as ResultCase.Success

            //Then
            coVerify(exactly = 1) { playerService.getStreamers() }

            assertNotNull(response.data)
            assertEquals(response.data, MotherObject.streamersList.streamers)
            assertEquals(
                response.data[0].username,
                MotherObject.streamersList.streamers[0].username
            )
        }

    @Test
    fun `getStreamers WHEN data is null THEN returns Error with 'unable to load' message`() =
        runBlocking {
            //Given
            coEvery { playerService.getStreamers() } returns success(null)

            //When
            val leaderBoards = repositoryImp.getStreamers()
            val response = leaderBoards as ResultCase.Error

            //Then
            coVerify(exactly = 1) { playerService.getStreamers() }

            assertNotNull(response.message, "Error: No fue posible cargar los datos")
        }

    @Test
    fun `getStreamers WHEN service throws exception THEN returns Error with exception message`() =
        runBlocking {
            //Given
            coEvery { playerService.getStreamers() } throws Exception("Ocurrio un fallo al obtener los datos")

            //When
            val leaderBoards = repositoryImp.getStreamers()
            val response = leaderBoards as ResultCase.Error

            //Then
            coVerify(exactly = 1) { playerService.getStreamers() }

            assertEquals(response.message, "Error: Ocurrio un fallo al obtener los datos")
        }

    @Test
    fun `getCountryFromPlayer WHEN data is available THEN returns Success with leaderboard data`() =
        runBlocking {

            val url = "https://api.chess.com/pub/country/"
            //Given
            coEvery { playerService.getCountry(MotherObject.profileDataTest.country!!.substringAfter(url)) } returns success(
                MotherObject.country
            )

            //When
            val country = repositoryImp.getCountryFromPlayer(MotherObject.profileDataTest.country ?: "")
            val response = country as ResultCase.Success

            //Then
            coVerify(exactly = 1) { playerService.getCountry(MotherObject.profileDataTest.country!!.substringAfter(url)) }

            assertNotNull(response.data)
            assertEquals(response.data.name, "Nicaragua")

        }

    @Test
    fun `getCountryFromPlayer WHEN endpoint provides is empty THEN returns Error with 'unable to load' message`() =
        runBlocking {
            //Given
            val emptyEndpoint = ""

            //When
            val country = repositoryImp.getCountryFromPlayer(emptyEndpoint)
            val response = country as ResultCase.Error

            //Then

            assertNotNull(response)
            assertEquals(response.message, "Error: No fue posible cargar el pais")
            coVerify(exactly = 0) { playerService.getCountry(any()) }
        }

    @Test
    fun `getCountryFromPlayer WHEN data is null THEN returns Error with 'unable to load' message`() =
        runBlocking {
            val url = "https://api.chess.com/pub/country/"
            //Given
            coEvery { playerService.getCountry(MotherObject.profileDataTest.country!!.substringAfter(url))} returns success(null)

            //When
            val country = repositoryImp.getCountryFromPlayer(MotherObject.profileDataTest.country ?: "")
            val response = country as ResultCase.Error

            //Then
            coVerify(exactly = 1) { playerService.getCountry(MotherObject.profileDataTest.country!!.substringAfter(url)) }

            assertNotNull(response)
            assertEquals(response.message, "Error: No fue posible cargar el pais")
        }

    @Test
    fun `getCountryFromPlayer WHEN service throws exception THEN returns Error with exception message`() =
        runBlocking {
            val url = "https://api.chess.com/pub/country/"
            //Given
            coEvery { playerService.getCountry(MotherObject.profileDataTest.country!!.substringAfter(url)) } throws Exception("Ocurrio un fallo al obtener los datos")

            //When
            val leaderBoards = repositoryImp.getCountryFromPlayer(MotherObject.profileDataTest.country ?: "")
            val response = leaderBoards as ResultCase.Error

            //Then
            coVerify(exactly = 1) { playerService.getCountry(MotherObject.profileDataTest.country!!.substringAfter(url)) }

            assertNotNull(response)
            assertEquals(response.message, "Error: Ocurrio un fallo al obtener los datos")
        }

}