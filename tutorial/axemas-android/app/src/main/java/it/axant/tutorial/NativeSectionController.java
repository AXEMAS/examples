package it.axant.tutorial;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.format.Time;

import org.json.JSONException;
import org.json.JSONObject;

import it.axant.axemas.JavascriptBridge;
import it.axant.axemas.NavigationSectionsManager;
import it.axant.axemas.SectionFragment;


public class NativeSectionController extends BaseSectionController {

    public NativeSectionController(SectionFragment section) {
        super(section);
    }

    private Handler handler;
    private Runnable runnable;

    @Override
    public void sectionWillLoad() {
        super.sectionWillLoad();

        this.section.getJSBridge().registerHandler("get-native-info", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {

                int stackSize = NavigationSectionsManager.stackSize(section.getActivity());

                JSONObject datum = new JSONObject();
                try {
                    datum.put("items", stackSize);
                    datum.put("device_name", android.os.Build.MODEL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.call(datum);
            }
        });

        this.section.getJSBridge().registerHandler("call-number", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {
                try {
                    JSONObject receivedData = (JSONObject) data;
                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse("tel:" + receivedData.getString("number")));
                    section.startActivity(phoneIntent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        this.section.getJSBridge().registerHandler("get-native-info", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {

                int stackSize = NavigationSectionsManager.stackSize(section.getActivity());

                JSONObject datum = new JSONObject();
                try {
                    datum.put("items", stackSize);
                    datum.put("device_name", android.os.Build.MODEL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.call(datum);
            }
        });

    }

    private void sendCurrentTime() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        JSONObject datum = new JSONObject();
        try {
            datum.put("current_time", today.format("%k:%M:%S"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        section.getJSBridge().callJS("time-from-native", datum, new JavascriptBridge.AndroidCallback() {
            @Override
            public void call(JSONObject data) {
                //send data
            }
        });

    }

    @Override
    public void sectionFragmentWillResume() {
        super.sectionFragmentWillResume();

        if (handler == null) {
            handler = new Handler();
            runnable = new Runnable() {
                public void run() {
                    sendCurrentTime();
                    handler.postDelayed(this, 1000);
                }
            };
            handler.postDelayed(runnable, 1000);
        }
    }

    @Override
    public void sectionFragmentWillPause() {
        super.sectionFragmentWillPause();

        if(handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }
    }
}
