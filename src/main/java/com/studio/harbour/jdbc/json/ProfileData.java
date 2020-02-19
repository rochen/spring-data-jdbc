package com.studio.harbour.jdbc.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName( value = "profile")
public class ProfileData {
    @JsonIgnore
    private String id;
    private String username;
    private String bio;
    private String image;
    private boolean following;
}
