package tools.xiaona.bookTxt2Excel.bean;

import java.util.List;

public class Version {
    private String versionNumber;
    private List<Copy> copies;

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    public void setCopies(List<Copy> copies) {
        this.copies = copies;
    }
}
