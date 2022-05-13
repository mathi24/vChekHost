package com.v_chek_host.vcheckhostsdk.model.entity;


import androidx.annotation.NonNull;


import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.v_chek_host.vcheckhostsdk.model.util.EntityUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;


public class Result {

    protected boolean success;

    public boolean isSuccess() {
        return success;
    }

    public static class Data<T> extends Result {

        private T data;

        public T getData() {
            return data;
        }

    }

    public static class Error extends Result {

        @SerializedName("error_msg")
        public String errorMessage;

        public String getErrorMessage() {
            return errorMessage;
        }

    }

    public static <T extends Result> Error buildError(@NonNull Response<T> response) {
        try {
            if(!response.errorBody().string().equals(""))
            return EntityUtils.gson.fromJson(response.errorBody().string(), Error.class);
            else{
              return toastError(response);
            }
        } catch (IOException | JsonSyntaxException e) {
            return toastError(response);
        }
    }


    public static <T extends Result> Error toastError(@NonNull Response<T> response){
        Error error = new Error();
        error.success = false;
        switch (response.code()) {
            case 400:
                error.errorMessage = "Request parameter is incorrect";
                break;
            case 403:
                error.errorMessage = "The request was rejected";
                break;
            case 404:
                error.errorMessage = "Resources not found";
                break;
            case 405:
                error.errorMessage = "The request mode is not allowed";
                break;
            case 408:
                error.errorMessage = "Request timed out";
                break;
            case 422:
                error.errorMessage = "Request semantic error";
                break;
            case 500:
                error.errorMessage = "Server logic error";
                break;
            case 502:
                error.errorMessage = "Server gateway error";
                break;
            case 504:
                error.errorMessage = "The server gateway timed out";
                break;
            default:
                error.errorMessage = response.message();
                break;
        }
        return error;
    }

    public static Error buildError(@NonNull Throwable t) {
        Error error = new Error();
        error.success = false;
        if (t instanceof UnknownHostException || t instanceof ConnectException) {
            error.errorMessage = "The network can not connect";
        } else if (t instanceof NoRouteToHostException) {
            error.errorMessage = "Unable to access the network";
        } else if (t instanceof SocketTimeoutException) {
            error.errorMessage = "Network access timeout";
        } else if (t instanceof JsonSyntaxException) {
            error.errorMessage = "Response data is malformed";
        } else {
            error.errorMessage = "unknown mistake：" + t.getLocalizedMessage();
        }
        return error;
    }

    public class QRUploadResponse extends Result {

        @SerializedName("ResponseCode")
        private String mResponseCode;

        @SerializedName("Message")
        private String mMessage;

        public String getmMessage() {
            return mMessage;
        }

        public String getmResponseCode() {
            return mResponseCode;
        }

    }

}
