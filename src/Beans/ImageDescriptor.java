package Beans;

import Types.ImageScope;

/**
 * Created by andre on 22.04.2017.
 */
public class ImageDescriptor extends Common {
    private Long id;
    private Long humanID;
    private ImageScope scope;
    private String contentType;
    private Boolean enabled;
    private String note;
    private Integer index;

    public ImageDescriptor() {
    }

    public ImageDescriptor(Long id, Long humanID, ImageScope scope, String contentType, Boolean enabled, String note, Integer index) {
        this.id = id;
        this.humanID = humanID;
        this.scope = scope;
        this.contentType = contentType;
        this.enabled = enabled;
        this.note = note;
        this.index = index;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHumanID() {
        return humanID;
    }

    public void setHumanID(Long humanID) {
        this.humanID = humanID;
    }

    public ImageScope getScope() {
        return scope;
    }

    public void setScope(ImageScope scope) {
        this.scope = scope;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public String toString(){
        String s = "";
        s += scope.toString();
        s += s != "" ? " " : "";
        s += contentType != null ? (", content type: " + contentType) : "";
        s += s != "" ? " " : "";
        s += id != null ? (", id:" + id) : "";
        s += s != "" ? " " : "";
        s += note != null ? (" " + note) : "";
        return s;
    }
}
