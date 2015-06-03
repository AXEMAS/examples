package it.axant.application;

import android.os.Bundle;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import it.axant.axemas.AXMActivity;
import it.axant.axemas.NavigationSectionsManager;

public class MainActivity extends AXMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationSectionsManager
                .registerController(this, IndexSectionController.class, "www/index.html");

        JSONObject data = new JSONObject();
        try {
            data.put("url", "www/index.html");
            data.put("toggleSidebarIcon", "slide_icon");
            data.put("title", "Home");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(savedInstanceState == null)
            NavigationSectionsManager
                    .makeApplicationRootController(this, data, "www/sidebar.html");


    }


    //


}
