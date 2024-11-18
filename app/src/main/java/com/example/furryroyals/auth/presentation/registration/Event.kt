package com.example.furryroyals.auth.presentation.registration

import com.example.furryroyals.core.domain.util.NetworkError

sealed interface Event {
    data class Error(val error: NetworkError): Event
}