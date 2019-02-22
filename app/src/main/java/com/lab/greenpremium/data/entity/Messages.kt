package com.lab.greenpremium.data.entity

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

//post /projects/add
data class AddProjectRequest(@SerializedName("message") val message: String,
                             @SerializedName("photos") val photos: List<MultipartBody.Part>)

data class AddProjectResponse(@SerializedName("project_id") val project_id: Int,
                              @SerializedName("event_id") val event_id: Int)

//post /messages/add
data class AddMessageRequest(@SerializedName("theme") val theme: String,
                             @SerializedName("message") val message: String,
                             @SerializedName("photos") val photos:  List<MultipartBody.Part>)

data class AddMessageResponse(@SerializedName("message_id") val message_id: Int,
                              @SerializedName("event_id") val event_id: Int)

//post /claims/add
data class AddClaimRequest(@SerializedName("message") val message: String,
                           @SerializedName("photos") val photos:  List<MultipartBody.Part>)

data class AddClaimResponse(@SerializedName("claim_id") val claim_id: Int,
                            @SerializedName("event_id") val event_id: Int)

//post /ratings/add
data class AddRatingRequest(@SerializedName("rating") val rating: Int,
                            @SerializedName("message") val message: String)

data class AddRatingResponse(@SerializedName("rating_id") val rating_id: Int,
                             @SerializedName("event_id") val event_id: Int)