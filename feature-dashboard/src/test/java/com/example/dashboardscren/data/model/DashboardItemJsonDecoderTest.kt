package com.example.dashboardscren.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class DashboardItemJsonDecoderTest {
    @Test
    fun decode_mapsDashboardItemAndIgnoresExtraJsonFields() {
        val json = """
            [{
              "userId": 1,
              "id": 7,
              "postTitle": "A post",
              "body": "Post body",
              "photoTitle": "A photo",
              "url": "https://example.com/full.jpg",
              "thumbnailUrl": "https://example.com/thumb.jpg"
            }]
        """.trimIndent()

        val result = DashboardItemJsonDecoder.decode(json)

        assertEquals(1, result.size)
        assertEquals(1, result.single().userId)
        assertEquals(7, result.single().id)
        assertEquals("A post", result.single().postTitle)
        assertEquals("https://example.com/thumb.jpg", result.single().thumbnailUrl)
    }
}
