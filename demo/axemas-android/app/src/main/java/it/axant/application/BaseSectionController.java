package it.axant.application;

import it.axant.axemas.AXMSectionController;
import it.axant.axemas.SectionFragment;


public class BaseSectionController extends AXMSectionController {

    public BaseSectionController(SectionFragment section) {
        super(section);

        // handlers registered here be called from any point of the application's JS code base

    }

}
