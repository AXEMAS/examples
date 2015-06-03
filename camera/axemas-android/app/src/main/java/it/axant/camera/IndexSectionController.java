package it.axant.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import it.axant.axemas.AXMSectionController;
import it.axant.axemas.JavascriptBridge;
import it.axant.axemas.SectionFragment;


public class IndexSectionController extends AXMSectionController {

    public IndexSectionController(SectionFragment section) {
        super(section);
    }

    private File photoFile = null;

    @Override
    public void sectionWillLoad() {
        super.sectionWillLoad();


        this.section.getJSBridge().registerHandler("open-camera", new JavascriptBridge.Handler() {
            @Override
            public void call(Object data, JavascriptBridge.Callback callback) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(section.getActivity().getPackageManager()) != null) {
                    try {
                        photoFile = Helper.createImageFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    if (photoFile != null) {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(photoFile));
                        section.startActivityForResult(takePictureIntent, 1231231231);
                    }
                }
            }
        });


    }

    @Override
    public void fragmentOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.fragmentOnActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1231231231) {
            JSONObject jsData = new JSONObject();
            try {
                jsData.put("path", "file://" + photoFile.getAbsolutePath());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            section.getJSBridge().callJS("send-path-to-js", jsData, new JavascriptBridge.AndroidCallback() {
                @Override
                public void call(JSONObject data) {
                    // empty
                }
            });
        }

    }

}
