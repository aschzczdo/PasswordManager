package Notes;

import javafx.scene.control.ListCell;
import Notes.Note;

public class    NoteListCell extends ListCell<Note> {
    @Override
    protected void updateItem(Note item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
        } else {
            setText(item.getNote());
        }
    }
}
