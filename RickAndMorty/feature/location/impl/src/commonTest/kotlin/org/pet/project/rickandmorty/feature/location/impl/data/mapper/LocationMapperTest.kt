package org.pet.project.rickandmorty.feature.location.impl.data.mapper

import org.pet.project.rickandmorty.feature.location.api.domain.entity.ResidentState
import org.pet.project.rickandmorty.feature.location.impl.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.location.impl.data.model.ResidentResponse
import org.pet.project.rickandmorty.feature.location.impl.data.paginator.RequestResidentState
import org.pet.project.rickandmorty.library.result.Result
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue

class LocationMapperTest {

    @Test
    fun `map LocationResponse to Location EXPECT correct id`() {
        val response = createLocationResponse(id = 42)

        val location = response.toItem(amountResidents = 5)

        assertEquals(42, location.id)
    }

    @Test
    fun `map LocationResponse to Location EXPECT correct name`() {
        val response = createLocationResponse(name = "Earth (C-137)")

        val location = response.toItem(amountResidents = 5)

        assertEquals("Earth (C-137)", location.name)
    }

    @Test
    fun `map LocationResponse to Location EXPECT correct type`() {
        val response = createLocationResponse(type = "Planet")

        val location = response.toItem(amountResidents = 5)

        assertEquals("Planet", location.type)
    }

    @Test
    fun `map LocationResponse to Location EXPECT correct dimension`() {
        val response = createLocationResponse(dimension = "Dimension C-137")

        val location = response.toItem(amountResidents = 5)

        assertEquals("Dimension C-137", location.dimension)
    }

    @Test
    fun `map LocationResponse to Location EXPECT correct amountResidents`() {
        val response = createLocationResponse()

        val location = response.toItem(amountResidents = 100)

        assertEquals(100, location.amountResidents)
    }

    @Test
    fun `map LocationResponse with zero residents EXPECT amountResidents zero`() {
        val response = createLocationResponse()

        val location = response.toItem(amountResidents = 0)

        assertEquals(0, location.amountResidents)
    }

    @Test
    fun `map ResidentResponse to Resident EXPECT correct id`() {
        val response = createResidentResponse(id = 38)

        val resident = response.toItem()

        assertEquals(38, resident.id)
    }

    @Test
    fun `map ResidentResponse to Resident EXPECT correct name`() {
        val response = createResidentResponse(name = "Beth Smith")

        val resident = response.toItem()

        assertEquals("Beth Smith", resident.name)
    }

    @Test
    fun `map ResidentResponse to Resident EXPECT correct image`() {
        val response = createResidentResponse(image = "https://rickandmortyapi.com/api/character/avatar/38.jpeg")

        val resident = response.toItem()

        assertEquals("https://rickandmortyapi.com/api/character/avatar/38.jpeg", resident.image)
    }

    @Test
    fun `map RequestResidentState Loading EXPECT ResidentState Loading`() {
        val requestState: RequestResidentState = RequestResidentState.Loading

        val residentState = requestState.toItem()

        assertIs<ResidentState.Loading>(residentState)
    }

    @Test
    fun `map RequestResidentState Error EXPECT ResidentState Error`() {
        val requestState: RequestResidentState = RequestResidentState.Error

        val residentState = requestState.toItem()

        assertIs<ResidentState.Error>(residentState)
    }

    @Test
    fun `map RequestResidentState Ended EXPECT ResidentState Ended`() {
        val requestState: RequestResidentState = RequestResidentState.Ended

        val residentState = requestState.toItem()

        assertIs<ResidentState.Ended>(residentState)
    }

    @Test
    fun `map RequestResidentState Success with empty list EXPECT ResidentState Success`() {
        val requestState = RequestResidentState.Success(
            value = emptyList(),
            reached = false
        )

        val residentState = requestState.toItem()

        assertIs<ResidentState.Success>(residentState)
    }

    @Test
    fun `map RequestResidentState Success with empty list EXPECT empty residents`() {
        val requestState = RequestResidentState.Success(
            value = emptyList(),
            reached = false
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertTrue(residentState.value.isEmpty())
    }

    @Test
    fun `map RequestResidentState Success with reached false EXPECT reached false`() {
        val requestState = RequestResidentState.Success(
            value = emptyList(),
            reached = false
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertEquals(false, residentState.reached)
    }

    @Test
    fun `map RequestResidentState Success with reached true EXPECT reached true`() {
        val requestState = RequestResidentState.Success(
            value = emptyList(),
            reached = true
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertEquals(true, residentState.reached)
    }

    @Test
    fun `map RequestResidentState Success with successful results EXPECT ResidentState Success`() {
        val residentResponse1 = createResidentResponse(id = 1, name = "Rick Sanchez")
        val residentResponse2 = createResidentResponse(id = 2, name = "Morty Smith")
        val requestState = RequestResidentState.Success(
            value = listOf(
                Result.Success.Value(residentResponse1),
                Result.Success.Value(residentResponse2)
            ),
            reached = false
        )

        val residentState = requestState.toItem()

        assertIs<ResidentState.Success>(residentState)
    }

    @Test
    fun `map RequestResidentState Success with successful results EXPECT correct size`() {
        val residentResponse1 = createResidentResponse(id = 1, name = "Rick Sanchez")
        val residentResponse2 = createResidentResponse(id = 2, name = "Morty Smith")
        val requestState = RequestResidentState.Success(
            value = listOf(
                Result.Success.Value(residentResponse1),
                Result.Success.Value(residentResponse2)
            ),
            reached = false
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertEquals(2, residentState.value.size)
    }

    @Test
    fun `map RequestResidentState Success with successful results EXPECT first resident id correct`() {
        val residentResponse1 = createResidentResponse(id = 1, name = "Rick Sanchez")
        val residentResponse2 = createResidentResponse(id = 2, name = "Morty Smith")
        val requestState = RequestResidentState.Success(
            value = listOf(
                Result.Success.Value(residentResponse1),
                Result.Success.Value(residentResponse2)
            ),
            reached = false
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertEquals(1, residentState.value[0].id)
    }

    @Test
    fun `map RequestResidentState Success with successful results EXPECT first resident name correct`() {
        val residentResponse1 = createResidentResponse(id = 1, name = "Rick Sanchez")
        val residentResponse2 = createResidentResponse(id = 2, name = "Morty Smith")
        val requestState = RequestResidentState.Success(
            value = listOf(
                Result.Success.Value(residentResponse1),
                Result.Success.Value(residentResponse2)
            ),
            reached = false
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertEquals("Rick Sanchez", residentState.value[0].name)
    }

    @Test
    fun `map RequestResidentState Success with successful results EXPECT second resident id correct`() {
        val residentResponse1 = createResidentResponse(id = 1, name = "Rick Sanchez")
        val residentResponse2 = createResidentResponse(id = 2, name = "Morty Smith")
        val requestState = RequestResidentState.Success(
            value = listOf(
                Result.Success.Value(residentResponse1),
                Result.Success.Value(residentResponse2)
            ),
            reached = false
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertEquals(2, residentState.value[1].id)
    }

    @Test
    fun `map RequestResidentState Success with mixed results EXPECT only successful residents`() {
        val residentResponse = createResidentResponse(id = 1, name = "Rick Sanchez")
        val requestState = RequestResidentState.Success(
            value = listOf(
                Result.Success.Value(residentResponse),
                Result.Failure.Error(Exception("Network error"))
            ),
            reached = true
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertEquals(1, residentState.value.size)
    }

    @Test
    fun `map RequestResidentState Success with mixed results EXPECT correct resident id`() {
        val residentResponse = createResidentResponse(id = 1, name = "Rick Sanchez")
        val requestState = RequestResidentState.Success(
            value = listOf(
                Result.Success.Value(residentResponse),
                Result.Failure.Error(Exception("Network error"))
            ),
            reached = true
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertEquals(1, residentState.value[0].id)
    }

    @Test
    fun `map RequestResidentState Success with only failures EXPECT empty residents`() {
        val requestState = RequestResidentState.Success(
            value = listOf(
                Result.Failure.Error(Exception("Error 1")),
                Result.Failure.Error(Exception("Error 2"))
            ),
            reached = true
        )

        val residentState = requestState.toItem() as ResidentState.Success

        assertTrue(residentState.value.isEmpty())
    }

    private fun createLocationResponse(
        id: Int = 1,
        name: String = "Earth",
        type: String = "Planet",
        dimension: String = "Dimension C-137",
        residents: List<String> = emptyList(),
        url: String = "https://rickandmortyapi.com/api/location/1",
        created: String = "2017-11-10T12:42:04.162Z"
    ): LocationResponse {
        return LocationResponse(
            id = id,
            name = name,
            type = type,
            dimension = dimension,
            residents = residents,
            url = url,
            created = created
        )
    }

    private fun createResidentResponse(
        id: Int = 1,
        name: String = "Test Resident",
        image: String = "https://example.com/image.png"
    ): ResidentResponse {
        return ResidentResponse(
            id = id,
            name = name,
            image = image
        )
    }
}
