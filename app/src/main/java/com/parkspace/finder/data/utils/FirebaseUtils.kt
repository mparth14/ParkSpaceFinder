/**
 * Utility function for suspending a coroutine until a Task completes.
 */
package com.parkspace.finder.data.utils

import android.util.Log
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

/**
 * Suspends the coroutine until the Task completes.
 * @return The result of the Task.
 */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T{
    return suspendCancellableCoroutine { cont ->
        addOnCompleteListener {
            if(it.exception != null){
                cont.resumeWithException(it.exception!!)
            } else {
                cont.resume(it.result, null)
            }
        }

    }
}
