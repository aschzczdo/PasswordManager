package Notes;

import java.security.Timestamp;

public class Note {
    private int id;
    private int credential_id;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredential_id() {
        return credential_id;
    }

    public void setCredential_id(int credential_id) {
        this.credential_id = credential_id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }



}
