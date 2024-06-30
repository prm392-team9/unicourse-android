package com.example.unicourse.models.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory implements Serializable {
    @SerializedName("_id")
    private String id;
    @SerializedName("userId")
    private User user;
    @SerializedName("process_date")
    private Date processDate;
    @SerializedName("items_checkout")
    private ArrayList<Item> itemsCheckout;
    @SerializedName("payment_method")
    private String paymentMethod;
    @SerializedName("total_old_amount")
    private int totalOldAmount;
    @SerializedName("total_new_amount")
    private int totalNewAmount;
    private String status;
    @SerializedName("total_used_coin")
    private int totalUsedCoin;
    private String transactionType;
    @SerializedName("is_feedback")
    private boolean isFeedback;
    @SerializedName("updated_at")
    private Date updatedAt;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("__v")
    private int version;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class User {
        @SerializedName("_id")
        private String userId;
        String email;
        String fullName;
        String profileName;
        @SerializedName("profile_image")
        String profileImage;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Item {
        @SerializedName("_id")
        private String itemId;
        private String title;
        private String titleDescription;
        private String subTitle;
        private ArrayList<String> subTitleDescription;
        private String thumbnail;
    }
}
