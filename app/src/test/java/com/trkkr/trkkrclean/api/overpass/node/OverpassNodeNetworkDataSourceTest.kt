package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.api.trkkr.osmtype.OSMTypesDto
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

class OverpassNodeNetworkDataSourceTest {

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
            val actual = sut.fetchNode("data=[out:json];(node(2713060210););out;")


            val expected = OverpassNodeDto(version = 0.6, generator = "Overpass API 0.7.56.9 76e5016d",
                osm3s = OverpassNodeDto.Osm3s(timestampOsmBase = "2021-05-17T17:49:56Z",
                copyright = "The data included in this document is from www.openstreetmap.org. The data is made available under ODbL."),
                elements = listOf(
                    OverpassNodeDto.Element(
                        type = "node",
                        id = 2713060210,
                        lat = 50.7320436,
                        lon = 7.0967647,
                        tags = mapOf(
                            "VRS:gemeinde" to "BONN",
                            "VRS:ortsteil" to "Innenstadt",
                            "VRS:ref" to  "61101",
                            "name" to  "Bonn Hauptbahnhof",
                            "network" to  "VRS",
                            "official_name" to  "Bonn Hbf",
                            "operator" to  "DB Netz AG",
                            "public_transport" to  "station",
                            "railway" to  "station",
                            "railway:ref" to  "KB",
                            "railway:station_category" to  "2",
                            "ref:ibnr" to  "8000044",
                            "toilets:wheelchair" to  "no",
                            "uic_ref" to  "8000044",
                            "wheelchair" to  "limited",
                            "wikidata" to  "Q55423",
                            "wikipedia" to  "de:Bonn Hauptbahnhof"
                        )
                    )
                ))
            assertEquals(expected, actual)
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