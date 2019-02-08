package me.matryoshkadoll.app.api.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("data")
    @Expose
    private Data data;


    public class Data {

        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("access_token")
        @Expose
        private String accessToken;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

    }


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}


