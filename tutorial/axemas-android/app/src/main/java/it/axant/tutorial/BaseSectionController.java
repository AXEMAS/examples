package it.axant.tutorial;

import org.json.JSONException;
import org.json.JSONObject;

import it.axant.axemas.AXMSectionController;
import it.axant.axemas.JavascriptBridge;
import it.axant.axemas.NavigationSectionsManager;
import it.axant.axemas.SectionFragment;


public class BaseSectionController extends AXMSectionController {

    public BaseSectionController(SectionFragment section) {
        super(section);
    }

    @Override
    public void sectionWillLoad() {
        super.sectionWillLoad();

        this.section.getJSBridge().registerHandler("push-native-view", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {
                JSONObject receivedData = (JSONObject) data;
                try {
                    if(receivedData.getBoolean("close")== true)
                        NavigationSectionsManager.toggleSidebar(section.getActivity(),false);
                    NavigationSectionsManager.pushFragment(section.getActivity(), new ExampleFragment());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
