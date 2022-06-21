package com.example.rxjavaapplication

class Task {
    var description: String? = null
    var isComplete = false
    var priority = 0

    constructor(description: String?, isComplete: Boolean, priority: Int) {
        this.description = description
        this.isComplete = isComplete
        this.priority = priority
    }


}