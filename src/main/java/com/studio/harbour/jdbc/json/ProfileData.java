package com.studio.harbour.jdbc.json;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonRootName( value = "profile")
public class ProfileData {
    private String username;
    private String bio;
    private String image;
    private boolean following;
}
