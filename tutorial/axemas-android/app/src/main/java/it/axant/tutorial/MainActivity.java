package it.axant.tutorial;

import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import it.axant.axemas.AXMActivity;
import it.axant.axemas.NavigationSectionsManager;

public class MainActivity extends AXMActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NavigationSectionsManager.registerController(this, NativeSectionController.class, "www/native.html");
        NavigationSectionsManager.registerController(this, BaseSectionController.class, "www/sidebar.html");

        JSONObject data = new JSONObject();
        try {
            data.put("url", "www/index.html");
            data.put("title", "Home");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        NavigationSectionsManager
                .makeApplicationRootController(this, data, "www/sidebar.html");

    }

}
