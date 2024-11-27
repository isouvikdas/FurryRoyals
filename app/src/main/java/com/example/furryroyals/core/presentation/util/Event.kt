package com.example.furryroyals.core.presentation.util

import com.example.furryroyals.core.domain.util.NetworkError

sealed interface Event {
    data class Error(val error: NetworkError): Event
    object LoggedIn : Event
}