package devPilot.backend.dto;

import java.util.UUID;

//A DTO is usually a simple POJO (Plain Old Java Object) whose purpose is to 
// carry/transfer data between layers or between client and server.


public record UserResponse(
    UUID id,
    Long githubId,
    String githubUsername,
    String displayName,
    String avatarUrl
){

    

    

}