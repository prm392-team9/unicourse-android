package com.example.unicourse.models.cart;

import com.example.unicourse.models.authentication.User;

import java.util.List;

public class CartResponse {
    private String message;
    private int status;
    private Cart data;

    public CartResponse() {}

    public CartResponse(String message, int status, Cart data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Cart getData() {
        return data;
    }

    public void setData(Cart data) {
        this.data = data;
    }

    public static class Cart {
        private String _id;
        private User user_id;
        private int __v;
        private double amount;
        private String created_at;
        private List<Item> items;
        private String updated_at;

        public Cart() {}

        public Cart(String _id, User user_id, int __v, double amount, String created_at, List<Item> items, String updated_at) {
            this._id = _id;
            this.user_id = user_id;
            this.__v = __v;
            this.amount = amount;
            this.created_at = created_at;
            this.items = items;
            this.updated_at = updated_at;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public User getUser_id() {
            return user_id;
        }

        public void setUser_id(User user_id) {
            this.user_id = user_id;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public static class Item {
            private String _id;
            private String title;
            private double amount;
            private String thumbnail;

            public Item() {
            }

            public Item(String _id, String title, double amount, String thumbnail) {
                this._id = _id;
                this.title = title;
                this.amount = amount;
                this.thumbnail = thumbnail;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }
        }
    }
}
