package it.axant.axemas;


public class AXMSectionController {
    protected SectionFragment section;

    public AXMSectionController(SectionFragment section) {
        this.section = section;
    }

    public void sectionDidLoad() {
        // web page finished loading
    }

    public void sectionWillLoad() {
        // web page has not yet started to load
    }

    public void sectionFragmentWillPause(){

    }

    public void sectionFragmentWillResume(){

    }
}
