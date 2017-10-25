package com.example.ctorres.superagentemovil3.utils;

import android.app.Application;

/**
 * Created by CTORRES on 15/08/2017.
 */

public class Global extends Application {
    private String glb_id_user;

    public String getGlb_id_user() {
        return glb_id_user;
    }

    public void setGlb_id_user(String glb_id_user) {
        this.glb_id_user = glb_id_user;
    }
}
