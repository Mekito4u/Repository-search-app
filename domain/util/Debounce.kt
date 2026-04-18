package com.app.modules.module4.pr3.domain.util

import kotlinx.coroutines.*

fun <T> CoroutineScope.debounce(
    waitMs: Long = 500L,
    destinationFunction: (T) -> Unit
): (T) -> Unit {
    var debounceJob: Job? = null
    return { param: T ->
        debounceJob?.cancel()
        debounceJob = launch {
            delay(waitMs)
            destinationFunction(param)
        }
    }
}