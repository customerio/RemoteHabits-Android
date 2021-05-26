package io.customer.remotehabits.service.logger

import java.util.*

enum class AppActivityKey {
    Login,
    Logout,

    // Developer related events that are important to make sure features are working.
    PushNotificationReceived // data or UI based. provide param
}

enum class AppActivityParamKey {
    Method,
    Id,
    Name,
    PaidUser, // (Bool) If user is a paying customer
    Type
}
