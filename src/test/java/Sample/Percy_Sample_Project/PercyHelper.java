package Sample.Percy_Sample_Project;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import io.percy.selenium.Percy;

public class PercyHelper {
    private Percy percy;
    private WebDriver driver;

    PercyHelper(WebDriver driver) {
        this.percy = new Percy(driver);
        this.driver = driver;
    }

    // Overlay generation to handle dynamic elements
    public void generateOverlays(List<PageElement> elements) {
        String overlayGenerationScript;
        for (PageElement element : elements) {
            System.out.println(element.getId());
            switch (element.getLocatorType()) {
                case "id":
                    overlayGenerationScript = "var targetElement = document.getElementById('"+element.getId()+"');\n" +
                            "                var targetElementPosition = getComputedStyle(targetElement).position;\n" +
                            "                targetElement.dataset.position = targetElementPosition;\n" +
                            "                if (targetElementPosition !== 'absolute') {\n" +
                            "                    targetElement.style = 'position: relative';\n" +
                            "                }\n" +
                            "                var overlay = document.createElement('div');\n" +
                            "                overlay.style = 'z-index: 10000; position: absolute; ; background: black; top: 0; left: 0; right: 0; bottom: 0;'\n" +
                            "                overlay.classList.add('bstack-overlay');\n" +
                            "                targetElement.insertBefore(overlay, targetElement.firstChild);";
                    break;

                case "xpath":
                    overlayGenerationScript = "var elementList = document.evaluate(\""+element.getXpath()+"\", document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);\n" +
                            "                for (let i = 0; i < elementList.snapshotLength; i++) {\n" +
                            "                    var targetElement = elementList.snapshotItem(i);\n" +
                            "                    var targetElementPosition = getComputedStyle(targetElement).position;\n" +
                            "                    targetElement.dataset.position = targetElementPosition;\n" +
                            "                    if (targetElementPosition !== 'absolute') {\n" +
                            "                        targetElement.style = 'position: relative';\n" +
                            "                    }\n" +
                            "                    var overlay = document.createElement('div');\n" +
                            "                    overlay.style = 'z-index: 10000; position: absolute; ; background: black; top: 0; left: 0; right: 0; bottom: 0;'\n" +
                            "                    overlay.classList.add('bstack-overlay');\n" +
                            "                    targetElement.insertBefore(overlay, targetElement.firstChild);\n" +
                            "                }";
                    break;

                case "classes":
                    String classString = "";
                    for (String classValue : element.getClassNames()) {
                        classString = classString + "." + classValue;
                    }
                    overlayGenerationScript = "document.querySelectorAll('"+classString+"').forEach(function(targetElement) {\n" +
                            "                    var targetElementPosition = getComputedStyle(targetElement).position;\n" +
                            "                    targetElement.dataset.position = targetElementPosition;\n" +
                            "                    if (targetElementPosition !== 'absolute') {\n" +
                            "                        targetElement.style = 'position: relative';\n" +
                            "                    }\n" +
                            "                    var overlay = document.createElement('div');\n" +
                            "                    overlay.style = 'z-index: 10000; position: absolute; background: black; top: 0; left: 0; right: 0; bottom: 0;'\n" +
                            "                    overlay.classList.add('bstack-overlay');\n" +
                            "                    targetElement.insertBefore(overlay, targetElement.firstChild);\n" +
                            "                })";
                    break;

                default: overlayGenerationScript = "";
            }
            ((JavascriptExecutor)this.driver).executeScript(overlayGenerationScript);
        }
    }

    public void cleanUpOverlays() {
        String cleanUpScript = "document.querySelectorAll('.bstack-overlay').forEach(function(overlay) {" +
                "            var overlayParent = overlay.parentElement;" +
                "            overlayParent.style.position = overlayParent.dataset.position;" +
                "            delete overlayParent.dataset.position;" +
                "            overlay.remove();" +
                "        });";
        ((JavascriptExecutor)driver).executeScript(cleanUpScript);

    }

    public void percySnapshotWithOverlay(String name, List<PageElement> elements) {
        generateOverlays(elements);
        percy.snapshot(name);
        cleanUpOverlays();
    }

    public void percySnapshotWithOverlay(String name, List<PageElement> elements, List<Integer> widths) {
        generateOverlays(elements);
        percy.snapshot(name, widths);
        cleanUpOverlays();
    }

    public void percySnapshotWithOverlay(String name, List<PageElement> elements, List<Integer> widths, Integer minHeight) {
        generateOverlays(elements);
        percy.snapshot(name, widths, minHeight);
        cleanUpOverlays();
    }

    public void percySnapshotWithOverlay(String name, List<PageElement> elements, List<Integer> widths, Integer minHeight, boolean enableJavaScript) {
        generateOverlays(elements);
        percy.snapshot(name, widths, minHeight, enableJavaScript);
        cleanUpOverlays();
    }

    public void percySnapshotWithOverlay(String name, List<PageElement> elements, List<Integer> widths, Integer minHeight, boolean enableJavaScript, String percyCSS) {
        generateOverlays(elements);
        percy.snapshot(name, widths, minHeight, enableJavaScript, percyCSS);
        cleanUpOverlays();
    }


    // Specific element captures
    public void hideAllElementsFromPage() {
        String hideAllElementsScript = "document.querySelectorAll('*').forEach(function(node) {\n" +
                "            if (node.visibility) {\n" +
                "                node.dataset.visibility = node.visibility;\n" +
                "            }\n" +
                "            node.style.visibility = 'hidden';\n" +
                "        });";
        ((JavascriptExecutor)driver).executeScript(hideAllElementsScript);
    }


    public void displayOnlySelectedElements(List<PageElement> elements) {
        String displaySelectedElementsScript;
        for (PageElement element : elements) {
            System.out.println(element.getId());
            switch (element.getLocatorType()) {
                case "id":
                    displaySelectedElementsScript = "var targetElement = document.getElementById('"+element.getId()+"');\n" +
                            "                let queue = [targetElement];\n" +
                            "                let elements = [];\n" +
                            "\n" +
                            "                while (queue.length > 0) {\n" +
                            "                    let element = queue.shift()\n" +
                            "                    elements.push(element);\n" +
                            "                    if (element.childNodes.length === 0) {\n" +
                            "                        continue;\n" +
                            "                    }\n" +
                            "                    element.childNodes.forEach((child) => {\n" +
                            "                        if (child.style) { \n" +
                            "                            queue.push(child);\n" +
                            "                        }\n" +
                            "                    });\n" +
                            "                }\n" +
                            "                // Display all elements nested inside target\n" +
                            "                elements.forEach((element) => {\n" +
                            "                    element.style ? element.style.visibility = 'visible' : null;\n" +
                            "                });";
                    break;

                case "xpath":
                    displaySelectedElementsScript = "var elementList = document.evaluate(\""+element.getXpath()+"\", document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);\n" +
                            "                for (let i = 0; i < elementList.snapshotLength; i++) {\n" +
                            "                    var targetElement = elementList.snapshotItem(i);\n" +
                            "                    // Perform BFS here\n" +
                            "                    let queue = [targetElement];\n" +
                            "                    let elements = [];\n" +
                            "\n" +
                            "                    while (queue.length > 0) {\n" +
                            "                        let element = queue.shift()\n" +
                            "                        elements.push(element);\n" +
                            "                        if (element.childNodes.length === 0) {\n" +
                            "                            continue;\n" +
                            "                        }\n" +
                            "                        element.childNodes.forEach((child) => {\n" +
                            "                            if (child.style) { \n" +
                            "                                queue.push(child);\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                    }\n" +
                            "                    // Display all elements nested inside target\n" +
                            "                    elements.forEach((element) => {\n" +
                            "                        element.style ? element.style.visibility = 'visible' : null;\n" +
                            "                    });\n" +
                            "                }";
                    break;

                case "classes":
                    String classString = "";
                    for (String classValue : element.getClassNames()) {
                        classString = classString + "." + classValue;
                    }
                    displaySelectedElementsScript = "document.querySelectorAll('"+classString+"').forEach(function(targetElement) {\n" +
                            "                    let queue = [targetElement];\n" +
                            "                    let elements = [];\n" +
                            "\n" +
                            "                    while (queue.length > 0) {\n" +
                            "                        let element = queue.shift()\n" +
                            "                        elements.push(element);\n" +
                            "                        if (element.childNodes.length === 0) {\n" +
                            "                            continue;\n" +
                            "                        }\n" +
                            "                        element.childNodes.forEach((child) => {\n" +
                            "                            if (child.style) { \n" +
                            "                                queue.push(child);\n" +
                            "                            }\n" +
                            "                        });\n" +
                            "                    }\n" +
                            "                    // Display all elements nested inside target\n" +
                            "                    elements.forEach((element) => {\n" +
                            "                        element.style ? element.style.visibility = 'visible' : null;\n" +
                            "                    });\n" +
                            "                });";
                    break;

                default: displaySelectedElementsScript = "";
            }
            ((JavascriptExecutor)this.driver).executeScript(displaySelectedElementsScript);
        }
    }


    public void cleanUpVisibility() {
        String cleanUpScript = "document.querySelectorAll('*').forEach(function(node) {\n" +
                "            if (node.dataset.visibility) {\n" +
                "                node.style.visibility = node.dataset.visibility;\n" +
                "            } else {\n" +
                "                node.style.visibility = 'visible';\n" +
                "            }\n" +
                "            delete node.dataset.visibility;\n" +
                "        });";
        ((JavascriptExecutor)driver).executeScript(cleanUpScript);
    }


    public void percySnapshotForSelectedElements(String name, List<PageElement> elements) {
        hideAllElementsFromPage();
        displayOnlySelectedElements(elements);
        percy.snapshot(name);
        cleanUpVisibility();
    }

    public void percySnapshotForSelectedElements(String name, List<PageElement> elements, List<Integer> widths) {
        hideAllElementsFromPage();
        displayOnlySelectedElements(elements);
        percy.snapshot(name, widths);
        cleanUpVisibility();
    }

    public void percySnapshotForSelectedElements(String name, List<PageElement> elements, List<Integer> widths, Integer minHeight) {
        hideAllElementsFromPage();
        displayOnlySelectedElements(elements);
        percy.snapshot(name, widths, minHeight);
        cleanUpVisibility();
    }

    public void percySnapshotForSelectedElements(String name, List<PageElement> elements, List<Integer> widths, Integer minHeight, boolean enableJavaScript) {
        hideAllElementsFromPage();
        displayOnlySelectedElements(elements);
        percy.snapshot(name, widths, minHeight, enableJavaScript);
        cleanUpVisibility();
    }

    public void percySnapshotForSelectedElements(String name, List<PageElement> elements, List<Integer> widths, Integer minHeight, boolean enableJavaScript, String percyCSS) {
        hideAllElementsFromPage();
        displayOnlySelectedElements(elements);
        percy.snapshot(name, widths, minHeight, enableJavaScript, percyCSS);
        cleanUpVisibility();
    }
}