package org.murugappan.service;
import org.murugappan.model.*;

public class HashService {
    UserCredentials userCredentials;
    public HashService(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }
    public void hashPassword(){
        userCredentials.userCredentials.put("Password", Integer.toString((userCredentials.userCredentials.get("Password")).hashCode()));//Hash The Incoming Plain Text Password
    }


}