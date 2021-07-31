package com.example.sterio

class User(
    private var userId: Int = 0,
    private var userReview: String = "",
    private var userRating: Int = 0
) {

    var id: Int
        get() = userId
        set(value) { userId=value }

    var review: String
        get() = userReview
        set(value) { userReview=value }

    var rating: Int
        get() = userRating
        set(value) { userRating = value }
}