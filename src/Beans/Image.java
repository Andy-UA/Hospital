package Beans;

import Types.ImageScope;

/**
 * Created by Andrew on 15.04.2017.
 */
public class Image extends Common {
    private Long id;
    private Long humanID;
    private ImageScope scope;
    private String contentType;
    private byte[] data;
    private Boolean enabled;
    private String note;

    public Image(Long id, Long humanID, ImageScope scope, String contentType, byte[] data, Boolean enabled, String note) {
        this.id = id;
        this.humanID = humanID;
        this.scope = scope;
        this.contentType = contentType;
        this.data = data;
        this.enabled = enabled;
        this.note = note;
    }

    public Image() {
    }

    public String getContentType() {
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getData() {
        if (data == null)
            data = new byte[0];
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
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
}
