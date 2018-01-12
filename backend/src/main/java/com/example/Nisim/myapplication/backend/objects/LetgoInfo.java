package com.example.Nisim.myapplication.backend.objects;

/**
 * Created by Nisim on 09/01/2018.
 */

public class LetgoInfo {

    private String id;
    private String password;

    public LetgoInfo(String id) {
        this.id = id;
    }

    public LetgoInfo(String id, String password) {
        this.id = id;
        this.password = password;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
