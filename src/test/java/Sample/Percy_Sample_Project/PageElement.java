package Sample.Percy_Sample_Project;

import java.util.List;

public class PageElement {
    private String locatorType;
    private String id;
    private String xpath;
    private List<String> classNames;

    public PageElement(String locatorType) {
        this.locatorType = locatorType;
    }

    public String getLocatorType() {
        return locatorType;
    }

    public void setLocatorType(String locatorType) {
        this.locatorType = locatorType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXpath() {
        return xpath;
    }

    public void setXpath(String xpath) {
        this.xpath = xpath;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> className) {
        this.classNames = className;
    }
}