package com.tno.cuhackit2020.net

import com.tno.cuhackit2020.model.Group
import com.tno.cuhackit2020.model.Issue
import com.tno.cuhackit2020.model.Suggestion
import com.tno.cuhackit2020.model.User
import retrofit2.http.*

interface IssueService {

    @GET("/suggestion")
    suspend fun getSuggestions(): List<Suggestion>

    @GET("/suggestion/{id}")
    suspend fun getSuggestion(@Path("id") id: Int): Suggestion?

    @POST("/suggestion")
    suspend fun createSuggestion(@Body suggestionBody: SuggestionData)

    @GET("/group")
    suspend fun getGroups(): List<Group>

    @GET("/group/{id}")
    suspend fun getGroup(@Path("id") id: Int): Group?

    @GET("/issue")
    suspend fun getIssues(): List<Issue>

    @GET("/issue/{id}")
    suspend fun getIssue(@Path("id") id: Int): Issue?

    @GET("/issue/{id}/suggestions")
    suspend fun getIssueSuggestions(@Path("id") id: Int): List<Suggestion>

    @GET("/user")
    suspend fun getUsers(): List<User>

    @GET("/user/{id}")
    suspend fun getUser(@Path("id") id: Int): User?

    @GET("/issue/{id}/like")
    suspend fun userLikedIssue(@Path("id") id: Int, @Query("user") user: Int, @Query("issue") issue: Int): Boolean?

    @POST("/issue/{issueId}/like")
    suspend fun likeIssue(@Path("issueId") issueId: Int, @Query("user") userId: Int, @Query("liked") status: Int)

    @POST("/suggestion/{suggestionId}/like")
    suspend fun likeSuggestion(
        @Path("suggestionId") suggestionId: Int,
        @Query("user") userId: Int,
        @Query("liked") status: Int
    )
}