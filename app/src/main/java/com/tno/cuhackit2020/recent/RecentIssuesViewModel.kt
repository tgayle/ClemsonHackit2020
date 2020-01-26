package com.tno.cuhackit2020.recent

import androidx.lifecycle.*
import com.tno.cuhackit2020.model.Group
import com.tno.cuhackit2020.model.RecentIssuesWrapper
import com.tno.cuhackit2020.model.User
import com.tno.cuhackit2020.net.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecentIssuesViewModel : ViewModel() {
    val service = Networking.service;


    val user = MutableLiveData<User>()
    val groups = MutableLiveData<List<Group>>()
    val activeIssue = MutableLiveData<Int>()
    val issueInfo = activeIssue.switchMap {
        liveData(Dispatchers.IO) {
            val issueId = it
            val issue = service.getIssue(issueId)
            val suggestions = service.getIssueSuggestions(issueId)

            if (issue == null) {
                emit(null)
                return@liveData
            }

            val all = mutableListOf<RecentIssuesWrapper>()



            all += RecentIssuesWrapper.RecentIssue(
                issue,
                service.getUser(issue.user) ?: return@liveData,
                issue.area
            )
            all.addAll(suggestions.map {
                return@map RecentIssuesWrapper.RecentSuggestion(
                    it,
                    service.getUser(it.user) ?: return@map,
                    it.area,
                    null,
                    issue
                )
            })

            emit(all)
        }
    }
    val recents = MutableLiveData<List<RecentIssuesWrapper>>(listOf())

    init {
        initUser()
        refresh()
    }

    fun initUser() {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val fetchedUser = service.getUsers().firstOrNull()
                fetchedUser ?: throw IllegalStateException("user is null")
                user.postValue(fetchedUser)
                groups.postValue(fetchedUser.groupmembers)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.Main) {
            _refresh()
        }
    }

    private suspend fun _refresh() {
        val issues = withContext(Dispatchers.IO) {
            service.getIssues().mapNotNull {
                val userLiked =
                    service.userLikedIssue(it.issueid, user.value?.userid ?: 0, it.issueid)
                val user = service.getUser(it.user) ?: return@mapNotNull null

                RecentIssuesWrapper.RecentIssue(it.copy(liked = userLiked), user, it.area)
            }
//            service.getIssues().mapNotNull { issue ->
//                Log.d("VM", "Issue: $issue")
//                service.getUser(issue.user)?.let {
//                    RecentIssuesWrapper.RecentIssue(issue, it, issue.area)
//                }
//            }
        }

        recents.postValue(issues)
    }

    fun loadIssue(issue: Int) {
        activeIssue.value = issue
    }

    fun likePost(item: RecentIssuesWrapper, status: Boolean?) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val liked = when (status) {
                    true -> 1
                    false -> 2
                    null -> 0
                }

                val userId = user.value?.userid ?: return@withContext

                when (item) {
                    is RecentIssuesWrapper.RecentIssue -> service.likeIssue(
                        userId,
                        item.issue.issueid,
                        liked
                    )
                    is RecentIssuesWrapper.RecentSuggestion -> service.likeSuggestion(
                        userId,
                        item.suggestion.suggestionsid,
                        liked

                    )
                }

                _refresh()
            }
        }
    }

    fun getIssueAndSuggestions(issueId: Int) = liveData(Dispatchers.IO) {
        val issue = service.getIssue(issueId)
        val suggestions = service.getIssueSuggestions(issueId)

        if (issue == null) {
            emit(null)
            return@liveData
        }

        val all = mutableListOf<RecentIssuesWrapper>()



        all += RecentIssuesWrapper.RecentIssue(
            issue,
            service.getUser(issue.user) ?: return@liveData,
            issue.area
        )
        all.addAll(suggestions.map {
            return@map RecentIssuesWrapper.RecentSuggestion(
                it,
                service.getUser(it.user) ?: return@map,
                it.area,
                null,
                issue
            )
        })

        emit(all)

    }

}
