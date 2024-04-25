package org.example.butacas.dto

import kotlinx.serialization.Serializable

@Serializable
class ButacaDto (
    var id : String,
    var estado : String,
    var ocupamiento: String,
    var tipo : String,
    var createdAt : String,
    var updatedAt : String,
    var isDeleted : Boolean = false
){
}