package com.lab.greenpremium.data.entity

//post /projects/add
data class AddProjectRequest(val message: String, val photos: List<String>)

data class AddProjectResponse(val project_id: Int, val event_id: Int)

//post /messages/add
data class AddMessageRequest(val theme: String, val message: String, val photos: List<String>)

data class AddMessageResponse(val message_id: Int, val event_id: Int)

//post /claims/add
data class AddClaimRequest(val message: String, val photos: List<String>)

data class AddClaimResponse(val claim_id: Int, val event_id: Int)

//post /ratings/add
data class AddRatingRequest(val rating: Int, val message: String)

data class AddRatingResponse(val rating_id: Int, val event_id: Int)