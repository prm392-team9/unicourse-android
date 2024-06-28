package com.example.unicourse.models.user;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCartResponse {
    private String id;
    private String userId;
    private int version;
    private int amount;
    private String createdAt;
    private ArrayList<String> items;
    private String updatedAt;
}
