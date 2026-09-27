package devPilot.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 
@Table(name="users") 
@Builder 
 // generate a fluent API, that when ever you want to create an object without long constructors
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "github_id", unique = true, nullable = false)
    private Long githubId;
    //private String test;

    @Column(name = "github_username",nullable = false, length = 100)
    private String githubUsername;

    @Column(name = "display_name", nullable = false, length =200)
    private String displayName;

    @Column(name = "avatarUrl",length =500)
    private String avatarUrl;

    // we are going to integrate github, so need some access to user's repo, accessToken
    @Column(name = "access_token", nullable = false, columnDefinition = "TEXT")
    private String accessToken;

    @Column(name="token_scopes" , length = 500)
    private String tokenScopes;

    @Column(name= "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // specifies a callback method for the corresponding lifecycle event
    @PrePersist 
    void onCreate(){ // automatic time stamp allocation if current timestamp is null when the data is set in DB
        if(createdAt== null){
            createdAt = Instant.now();
        }
    }
}


