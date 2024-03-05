package com.goofy.coroutinelog.resource

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class CoroutineLogResource(
    private val coroutineLogService: CoroutineLogService,
) {
    @GetMapping("/test1")
    fun getV1(): Response {
        return coroutineLogService.getV1()
    }

    @GetMapping("/test2")
    suspend fun getV2(): Response {
        return coroutineLogService.getV2()
    }

    @GetMapping("/test3")
    suspend fun getV3(): Response {
        return coroutineLogService.getV3()
    }
}

@Service
class CoroutineLogService {
    fun getV1(): Response {
        return Response(
            message = "test1",
            dateTime = LocalDateTime.now()
        )
    }

    suspend fun getV2(): Response {
        return Response(
            message = "test2",
            dateTime = LocalDateTime.now()
        )
    }

    suspend fun getV3(): Response {
        return Response(
            message = "test3",
            dateTime = LocalDateTime.now()
        )
    }
}

data class Response(
    val message: String,
    val dateTime: LocalDateTime,
    val extra: Map<String, Any> = emptyMap()
)
