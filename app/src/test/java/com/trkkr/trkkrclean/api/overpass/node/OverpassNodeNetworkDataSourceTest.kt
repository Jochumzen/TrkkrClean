package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.domain.OsmNode
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

class OverpassNodeNetworkDataSourceTest() {

    private val mockWebServer = MockWebServer()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OverpassNodeService::class.java)

    private val sut = OverpassNodeNetworkDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch node correctly given 200 response`() {
        mockWebServer.enqueueResponse("overpassnode.json", 200)

        runBlocking {
            val actual = sut.fetchNode("data=[out:json];(node(6874973681););out meta;")

            val expected = OverpassNodeDto(
                version = 0.6,
                generator = "Overpass API 0.7.56.9 76e5016d",
                osm3s = OverpassNodeDto.Osm3s(
                    timestampOsmBase = "2021-06-06T13:23:27Z",
                    copyright = "The data included in this document is from www.openstreetmap.org. The data is made available under ODbL."),
                    elements = listOf(
                    OverpassNodeDto.Element(
                        type = "node",
                        id = 6874973681,
                        lat = 55.7276769,
                        lon = 13.1749253,
                        timestamp = "2020-10-05T12:08:46Z",
                        version = 2,
                        changeset = 91985217,
                        user = "Jochumzen",
                        uid = 4842557,
                        tags = mapOf(
                            "name" to "Honey Bunny Tattoo",
                            "shop" to "tattoo",
                            "website" to  "www.honeybunnytattoo.se"
                        )
                    )
                ))
            assertEquals(expected, actual)
        }
    }


    @Test
    fun `should fetch node correctly and map to domain model`() {

        val overpassNodeDtoMapper = OverpassNodeDtoMapper()

        mockWebServer.enqueueResponse("overpassnode.json", 200)

        runBlocking {
            val actual = sut.fetchNode("data=[out:json];(node(2713060210););out;")

            val expected = OsmNode(
                type = "node",
                id = 6874973681,
                lat = 55.7276769,
                lon = 13.1749253,
                timestamp = "2020-10-05T12:08:46Z",
                version = 2,
                changeset = 91985217,
                user = "Jochumzen",
                uid = 4842557,
                tags = mapOf(
                    "name" to "Honey Bunny Tattoo",
                    "shop" to "tattoo",
                    "website" to  "www.honeybunnytattoo.se"
                )
            )
            assertEquals(expected, overpassNodeDtoMapper.mapToDomainModel(actual)[0])
        }
    }


    fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }
}