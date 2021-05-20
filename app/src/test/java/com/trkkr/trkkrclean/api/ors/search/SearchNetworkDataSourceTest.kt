package com.trkkr.trkkrclean.api.ors.search

import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

class SearchNetworkDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SearchService::class.java)

    private val sut = SearchNetworkDataSource(api)

    @After
    fun tearDown() = mockWebServer.shutdown()

    @Test
    fun `should fetch search results correctly given 200 response`() {
        mockWebServer.enqueueResponse("searchResult.json", 200)

        runBlocking {
            val actual = sut.fetchSearchResult()

            val lang = SearchDto.Lang(name = "Swedish", iso6391 = "sv", iso6393 = "swe", defaulted = false)

            val query = SearchDto.Query(text = "Lund", size = 1, layers = listOf("locality"), sources = listOf("whosonfirst"),
                private = false, lat = 55, lon = 13, lang = lang, bc = listOf("SWE"), querySize = 20)

            val engine = SearchDto.Engine(name = "Pelias", author = "Mapzen", version = "1.0")

            val geoCoding = SearchDto.Geocoding(version= "0.2", attribution = "https://openrouteservice.org/terms-of-service/#attribution-geocode",
            query = query, engine = engine, timestamp = 1621538677362)

            val geometry = SearchDto.Geometry(type = "Point", coordinates = listOf(13.197253, 55.70811))

            val properties = SearchDto.Properties(id = "101752279", gid = "whosonfirst:locality:101752279", layer = "locality",
            source = "whosonfirst", sourceId = "101752279", name = "Lund", confidence = 0.1, matchType = "unknown",
            distance = 79.809, accuracy = "centroid", country = "Sverige", countryGid = "whosonfirst:country:85633789",
            countryA = "SWE", region = "Skåne län", regionGid = "whosonfirst:region:85688377", regionA = "SN",
            county = "Lund", countyGid = "whosonfirst:county:1159303449", localadmin = "Lunds Allhelgona",
            localadminGid = "whosonfirst:localadmin:1125362435", locality = "Lund", localityGid = "whosonfirst:locality:101752279",
            continent = "Europa", continentGid = "whosonfirst:continent:102191581", label = "Lund, Sverige")

            val feature = SearchDto.Feature(type = "Feature", geometry = geometry, properties = properties, bbox = listOf(
                13.1457503323,
                55.678776639,
                13.2553449291,
                55.742051341
            ))

            val features = listOf(feature)

            val expected = SearchDto(geocoding = geoCoding, type = "FeatureCollection", features = features)

            assertEquals(expected, actual)

        }
    }

    fun MockWebServer.enqueueResponse(filename: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$filename")

        val source = inputStream ?.let { inputStream.source().buffer() }
        source ?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }


}