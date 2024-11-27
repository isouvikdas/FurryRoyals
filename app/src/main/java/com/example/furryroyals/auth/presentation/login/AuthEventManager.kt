package com.example.furryroyals.auth.presentation.login

import com.example.furryroyals.core.presentation.util.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class AuthEventManager {
    private val _events = MutableSharedFlow<Event>()
    val events: SharedFlow<Event> = _events.asSharedFlow()

    suspend fun sendEvent(event: Event) {
        _events.emit(event)
    }
}
