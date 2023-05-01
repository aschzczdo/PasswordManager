package ui;

import Credentials.Credentials;
import Credentials.CredentialsDB;
import Notes.Note;
import Notes.NoteListCell;
import javafx.scene.control.*;
import user.User;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class NotesUI extends VBox {
    private User user;
    private CredentialsDB dbConnection;
    private Credentials selectedCredential;
    private PasswordManagerDashboard dashboard;



    public NotesUI(User user, Credentials selectedCredentials, PasswordManagerDashboard dashboard){
        this.user = user;
        this.dbConnection = new CredentialsDB();
        this.selectedCredential = selectedCredentials;
        this.dashboard = dashboard;
        setSpacing(10);
        setPadding(new Insets(20));
        getStyleClass().add("vbox");

        Label label = new Label("Notes");
        label.getStyleClass().add("status-label");

        ListView<Note> notesListView = new ListView<>();
        notesListView.setCellFactory(param -> new NoteListCell());

        Button addButton = new Button("Add Note");
        addButton.setOnAction(e -> handleAddNote(notesListView));

        Button editButton = new Button("Edit Note");
        editButton.setOnAction(e -> handleEditNote(notesListView));

        Button deleteButton = new Button("Delete Note");
        deleteButton.setOnAction(e -> handleDeleteNote(notesListView));

        Button backButton = new Button("Back to Credentials");
        backButton.setOnAction(e -> {
            dashboard.showNotesForCredential(null);
            dashboard.getTabPane().getSelectionModel().select(dashboard.getCredentialsTab());
        });



        HBox buttons = new HBox(10, addButton, editButton, deleteButton, backButton);
        buttons.setAlignment(Pos.CENTER);

        getChildren().addAll(label, notesListView, buttons);
    }

    private void handleAddNote(ListView<Note> notesListView) {
        // Show an input dialog to get the note content
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Note");
        dialog.setHeaderText("Add a new note");
        dialog.setContentText("Note:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String noteContent = result.get();
            if (noteContent != null && !noteContent.trim().isEmpty()) {
                Note note = new Note();
                note.setCredential_id(selectedCredential.getCred_id());
                note.setNote(noteContent);

                if (dbConnection.insertNote(note)) {
                    notesListView.getItems().add(note);
                } else {
                    showError("Failed to add note.");
                }
            } else {
                showError("Please enter a note.");
            }
        }
    }

    private void handleEditNote(ListView<Note> notesListView) {
        // Get the selected note
        Note selectedNote = notesListView.getSelectionModel().getSelectedItem();
        if (selectedNote == null) {
            showError("Please select a note to update.");
            return;
        }

        // Show an input dialog to update the note content
        TextInputDialog dialog = new TextInputDialog(selectedNote.getNote());
        dialog.setTitle("Update Note");
        dialog.setHeaderText("Update the selected note");
        dialog.setContentText("Note:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String updatedNoteContent = result.get();
            selectedNote.setNote(updatedNoteContent);

            if (dbConnection.updateNote(selectedNote)) {
                notesListView.refresh();
            } else {
                showError("Failed to update note.");
            }
        }
    }

    private void handleDeleteNote(ListView<Note> notesListView) {
        // Get the selected note
        Note selectedNote = notesListView.getSelectionModel().getSelectedItem();
        if (selectedNote == null) {
            showError("Please select a note to delete.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Note");
        alert.setHeaderText("Are you sure you want to delete the selected note?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (dbConnection.deleteNote(selectedNote)) {
                notesListView.getItems().remove(selectedNote);
            } else {
                showError("Failed to delete note.");
            }
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
