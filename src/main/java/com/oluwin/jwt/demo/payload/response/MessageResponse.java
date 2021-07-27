/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oluwin.jwt.demo.payload.response;

/**
 *
 * @author user
 */
public class MessageResponse {
    private String message;
    private int responseCode;
    
    public MessageResponse(String message, int responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
