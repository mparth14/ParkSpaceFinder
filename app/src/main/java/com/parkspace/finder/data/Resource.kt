package com.parkspace.finder.data

/**
 * A sealed class representing different states of a resource.
 */
sealed class Resource<out R> {
    /**
     * Represents a successful state with a result of type [R].
     */
    data class Success<out R>(val result: R): Resource<R>()

    /**
     * Represents a failure state with an exception.
     */
    data class Failure(val exception: Exception): Resource<Nothing>()

    /**
     * Represents a loading state.
     */
    object Loading: Resource<Nothing>()
}
