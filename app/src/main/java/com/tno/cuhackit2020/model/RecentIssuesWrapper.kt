package com.tno.cuhackit2020.model

sealed class RecentIssuesWrapper {
    class RecentIssue(val issue: Issue, val author: User, val area: String) : RecentIssuesWrapper()
    class RecentSuggestion(
        val suggestion: Suggestion,
        val author: User,
        val area: String,
        val parent: Suggestion?,
        val issue: Issue?
    ) : RecentIssuesWrapper()
}