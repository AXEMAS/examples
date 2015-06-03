package it.axant.application;

import android.os.Build;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import it.axant.axemas.JavascriptBridge;
import it.axant.axemas.NavigationSectionsManager;
import it.axant.axemas.SectionFragment;


public class IndexSectionController extends BaseSectionController {

    public IndexSectionController(SectionFragment section) {
        super(section);
    }

    @Override
    public void sectionWillLoad() {

        this.section.getJSBridge().registerHandler("open-sidebar-from-native", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {
                NavigationSectionsManager.toggleSidebar(section.getActivity(), true);
            }
        });


        this.section.getJSBridge().registerHandler("start-facebook-login", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {
                ((MainActivity) section.getActivity()).openFacebookSession("1574920036057690",
                        Arrays.asList("public_profile", "email", "user_birthday", "user_hometown", "user_location"),
                        new Session.StatusCallback() {
                            @Override
                            public void call(Session session, SessionState sessionState, Exception e) {
                                if (e != null) {
                                    Log.d("Facebook", e.getMessage());
                                }
                                Log.d("Facebook", "Session State: " + session.getState() + "token "+ session.getAccessToken());
                                // you can make request to the /me API or do other stuff like post, etc. here

                            }
                        });
            }
        });

        this.section.getJSBridge().registerHandler("send-device-name-from-native-to-js", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {
                JSONObject deviceInfo = new JSONObject();
                try {
                    deviceInfo.put("name", Build.MODEL);
                    deviceInfo.put("other", Build.SERIAL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                section.getJSBridge().callJS("display-device-model", deviceInfo, new JavascriptBridge.AndroidCallback() {
                    @Override
                    public void call(JSONObject data) {
                        //empty
                    }
                });
            }
        });

        this.section.getJSBridge().registerHandler("open-native-controller", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {
                JSONObject datum = new JSONObject();
                try {
                    datum.put("url", "www/index.html");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                NavigationSectionsManager.goTo(section.getActivity(), datum);
            }
        });


        this.section.getJSBridge().registerHandler("push-native-section", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {
                NavigationSectionsManager.pushFragment(section.getActivity(), new NativeFragment());
            }
        });
    }
}
