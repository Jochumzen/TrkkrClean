package com.trkkr.trkkrclean.api.overpass.way

import com.trkkr.trkkrclean.api.overpass.node.OverpassNodeDtoMapper
import com.trkkr.trkkrclean.domain.OsmWay
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

class OverpassWayNetworkDataSourceTest{

    private val mockWebServer = MockWebServer()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OverpassWayService::class.java)

    private val sut = OverpassWayNetworkDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch way correctly given 200 response`() {
        mockWebServer.enqueueResponse("overpassway.json", 200)

        runBlocking {
            val actual = sut.fetchWay("data=[out:json];(way(94831558););out meta;")

            val expected = OverpassWayDto(
                version = 0.6,
                generator = "Overpass API 0.7.56.9 76e5016d",
                osm3s = OverpassWayDto.Osm3s(
                    timestampOsmBase = "2021-06-06T17:09:40Z",
                    copyright = "The data included in this document is from www.openstreetmap.org. The data is made available under ODbL."),
                elements = listOf(
                    OverpassWayDto.Element(
                        type = "way",
                        id = 94831558,
                        timestamp = "2019-10-23T21:01:30Z",
                        version = 8,
                        changeset = 76114291,
                        user = "wheelmap_visitor",
                        uid = 290680,
                        nodes = listOf(1100809937, 1100809695, 1100809718, 1100809954, 2010566724, 2010566702, 1100809897, 1100809909, 1100809937),
                        tags = mapOf(
                            "building" to "retail",
                            "building:levels" to "1",
                            "height" to "5",
                            "name" to "ICA Jägaren",
                            "opening_hours" to "Mo-Su,PH 08:00-21:00",
                            "shop" to "supermarket",
                            "wheelchair" to "yes"
                        )
                    )
                ))
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `should fetch way correctly and map to domain model`() {

        val overpassWayDtoMapper = OverpassWayDtoMapper()

        mockWebServer.enqueueResponse("overpassway.json", 200)

        runBlocking {
            val actual = sut.fetchWay("data=[out:json];(way(94831558););out meta;")

            val expected = OsmWay(
                type = "way",
                id = 94831558,
                timestamp = "2019-10-23T21:01:30Z",
                version = 8,
                changeset = 76114291,
                user = "wheelmap_visitor",
                uid = 290680,
                nodes = listOf(1100809937, 1100809695, 1100809718, 1100809954, 2010566724, 2010566702, 1100809897, 1100809909, 1100809937),
                tags = mapOf(
                    "building" to "retail",
                    "building:levels" to "1",
                    "height" to "5",
                    "name" to "ICA Jägaren",
                    "opening_hours" to "Mo-Su,PH 08:00-21:00",
                    "shop" to "supermarket",
                    "wheelchair" to "yes"
                )
            )
            assertEquals(expected, overpassWayDtoMapper.mapToDomainModel(actual)[0])
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