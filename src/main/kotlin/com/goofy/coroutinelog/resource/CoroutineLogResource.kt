package com.goofy.coroutinelog.resource

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
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
class CoroutineLogService(
    private val testDataMakerService: TestDataMakerService
) {
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

@Service
class TestDataMakerService {
    suspend fun test1(): String {
        return withContext(Dispatchers.IO) { "hello data" }
    }

    suspend fun test2(): LocalDateTime {
        return withContext(Dispatchers.IO) { LocalDateTime.now() }
    }

    suspend fun test3(): Map<String, Any> {
        return withContext(Dispatchers.IO) {
            mutableMapOf(
                "test1" to "String",
                "test2" to "LocalDateTime",
                "test3" to "Map"
            )
        }
    }
}

data class Response(
    val message: String,
    val dateTime: LocalDateTime,
    val extra: Map<String, Any> = emptyMap()
)

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger = KotlinLogging.logger { }

    @ExceptionHandler(Exception::class)
    fun handle(e: Exception): ErrorResponse {
        logger.error(e) { "Exception." }
        return ErrorResponse(e.message ?: e.stackTraceToString())
    }
}

data class ErrorResponse(
    val message: String
)
