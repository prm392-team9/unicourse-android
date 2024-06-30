package com.example.unicourse.models.transaction;

import java.util.List;

public class TransactionResponse {
    private String message;
    private Number status;
    private TransactionData data;

    public TransactionResponse(String message, Number status, TransactionData data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Number getStatus() {
        return status;
    }

    public void setStatus(Number status) {
        this.status = status;
    }

    public TransactionData getData() {
        return data;
    }

    public void setData(TransactionData data) {
        this.data = data;
    }

    // Nested class for transaction data
    public static class TransactionData {
        private String userId;
        private String process_date;
        private Payer payer;
        private List<String> items_checkout;
        private String payment_method;
        private double total_old_amount;
        private double total_new_amount;
        private String status;
        private String transaction_code;
        private int total_used_coin;
        private String transactionType;
        private boolean is_feedback;
        private String _id;
        private String updated_at;
        private String created_at;
        private int __v;

        public TransactionData() {}

        public TransactionData(String userId, String process_date, Payer payer, List<String> items_checkout, String payment_method, double total_old_amount, double total_new_amount, String status, String transaction_code, int total_used_coin, String transactionType, boolean is_feedback, String _id, String updated_at, String created_at, int __v) {
            this.userId = userId;
            this.process_date = process_date;
            this.payer = payer;
            this.items_checkout = items_checkout;
            this.payment_method = payment_method;
            this.total_old_amount = total_old_amount;
            this.total_new_amount = total_new_amount;
            this.status = status;
            this.transaction_code = transaction_code;
            this.total_used_coin = total_used_coin;
            this.transactionType = transactionType;
            this.is_feedback = is_feedback;
            this._id = _id;
            this.updated_at = updated_at;
            this.created_at = created_at;
            this.__v = __v;
        }

        public static class Payer {
            private String name;
            private String email;
            private String address;

            public Payer() {}

            public Payer(String name, String email, String address) {
                this.name = name;
                this.email = email;
                this.address = address;
            }

            // Getters and setters
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getProcess_date() {
            return process_date;
        }

        public void setProcess_date(String process_date) {
            this.process_date = process_date;
        }

        public Payer getPayer() {
            return payer;
        }

        public void setPayer(Payer payer) {
            this.payer = payer;
        }

        public List<String> getItems_checkout() {
            return items_checkout;
        }

        public void setItems_checkout(List<String> items_checkout) {
            this.items_checkout = items_checkout;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public double getTotal_old_amount() {
            return total_old_amount;
        }

        public void setTotal_old_amount(double total_old_amount) {
            this.total_old_amount = total_old_amount;
        }

        public double getTotal_new_amount() {
            return total_new_amount;
        }

        public void setTotal_new_amount(double total_new_amount) {
            this.total_new_amount = total_new_amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTransaction_code() {
            return transaction_code;
        }

        public void setTransaction_code(String transaction_code) {
            this.transaction_code = transaction_code;
        }

        public int getTotal_used_coin() {
            return total_used_coin;
        }

        public void setTotal_used_coin(int total_used_coin) {
            this.total_used_coin = total_used_coin;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public boolean isIs_feedback() {
            return is_feedback;
        }

        public void setIs_feedback(boolean is_feedback) {
            this.is_feedback = is_feedback;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }
    }
}

