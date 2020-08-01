package com.bbs.mr.assignmentnetwork.Model;



/* Created by MrBBS @ 2020
Email: 0331999bbs@gmail.com
Phone: 034 707 9556 */
public class Account {
    public String username,name,email,address,birthday;
    public int phone,account_type;

    public Account(String username, String name, String email, String address, String birthday, int phone, int account_type) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.phone = phone;
        this.account_type = account_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getAccount_type() {
        return account_type;
    }

    public void setAccount_type(int account_type) {
        this.account_type = account_type;
    }
}
