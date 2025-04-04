package com.kayky.response;


import lombok.*;

@Setter
@Getter
@Builder
public class UserGetResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}