package com.example.demo.modal;

public class GoogleUser {
    private String sub;
    private String name;
    private String email;
    private String picture;
    private String phoneNumber;  // Thêm trường số điện thoại
   
    // Getters and Setters
    public String getSub() {
        return sub;
    }

  

	public void setSub(String sub) {
        this.sub = sub;
    }

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPhoneNumber() {  // Getter cho số điện thoại
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {  // Setter cho số điện thoại
        this.phoneNumber = phoneNumber;
    }
}
