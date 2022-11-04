package org.android.turnaround.util

import timber.log.Timber

class TurnAroundDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return "${element.fileName} : ${element.lineNumber} - ${element.methodName}"
    }
}
