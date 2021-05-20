package com.trkkr.trkkrclean.api.trkkr.osmtype

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


@ExperimentalCoroutinesApi
class OSMTypesNetworkDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(OSMTypeService::class.java)

    private val sut = OSMTypesNetworkDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch types correctly given 200 response`() {
        mockWebServer.enqueueResponse("osmtypes.json", 200)

        runBlocking {
            val actual = sut.fetchOsmTypes()
            val osmPoiType = OSMTypesDto.OsmPoiType(osmTypeId = 477, osmKey = "amenity", osmValue = "bar", nameEn = "bar", nameSv= "bar", canBeNode = true,
            canBeWay= false, canBeArea = true, canBeCreated= true, isCommon= false,
                descriptionEn = "Bar is a purpose-built commercial establishment that sells alcoholic drinks to be consumed on the premises. They are characterised by a noisy and vibrant atmosphere, similar to a party and usually don't sell food. See also the description of the tags amenity=pub;bar;restaurant for a distinction between these.",
            descriptionSv= "En bar är en kommersiell verksamhet som säljer alkoholhaltiga drycker som konsumeras på plats. De kännetecknas av en bullrig och pulserande atmosfär, liknar en fest och brukar inte sälja mat. Se även beskrivningen av taggar rekreations = pub, bar, restaurang för en skillnad mellan dessa.",
            imageName = "Bar MXCT.JPG")
            val expected = OSMTypesDto(result = "success", types = listOf(osmPoiType))

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