package msku.ceng.madlab.mynotes;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NoteFragment.OnNoteListInteractionListener{

    boolean displayingEditor = false;
    Note editingNote;
    ArrayList<Note> notes = new ArrayList<>();
    ListenerRegistration listenerRegistration;
    private static final String TAG = "Firebase Demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        if (!displayingEditor){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container,NoteFragment.newInstance(),"fragment_note");
            ft.commit();
        }else{
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,EditNoteFragment.newInstance(editingNote.getContent()));
            ft.addToBackStack(null);
            ft.commit();
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        listenerRegistration = db.collection("notes")
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e(TAG,"Error retrieving notes",error);
                            return;
                        }
                        notes.clear();
                        for (QueryDocumentSnapshot doc : value){
                            Log.d(TAG,doc.getData().toString());
                            Note note = doc.toObject(Note.class);
                            notes.add(note);
                        }
                        NoteFragment listFragment = (NoteFragment)
                            getSupportFragmentManager().findFragmentByTag("fragment_note");
                        listFragment.updateNotes(notes);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", item.getTitle().toString());
        displayingEditor = !displayingEditor;
        invalidateOptionsMenu();

        if (item.getItemId()==R.id.action_new){
            editingNote = createNote();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container,EditNoteFragment.newInstance(""),"edit_note");
            ft.addToBackStack(null);
            ft.commit();
            return true;
        } else if (item.getItemId()==R.id.action_close) {
            onBackPressed();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }

    }
    public boolean onPrepareOptionsMenu(Menu menu){
        Log.d("onPrepareOptionsMenu new visible",
                menu.findItem(R.id.action_new).isVisible() + "");
        menu.findItem(R.id.action_new).setVisible(!displayingEditor);
        menu.findItem(R.id.action_close).setVisible(displayingEditor);
        return super.onPrepareOptionsMenu(menu);
    }
    @Override
    public void onBackPressed() {
        EditNoteFragment editFragment = (EditNoteFragment)
                getSupportFragmentManager().findFragmentByTag("edit_note");
        String content = null;
        if (editFragment != null){
            content = editFragment.getContent();
        }
        super.onBackPressed();
        if (content !=null) {
            saveContent(editingNote, content);
        }
    }

    @Override
    public void onNoteSelected(Note note) {
        editingNote =note;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,EditNoteFragment.newInstance(editingNote.getContent()),"edit_note");
                ft.addToBackStack(null);
        ft.commit();
        displayingEditor = !displayingEditor;
        invalidateOptionsMenu();
    }

    private Note createNote(){
        Note note = new Note();
        String id = FirebaseFirestore.getInstance()
                .collection("notes")
                .document()
                .getId();

        note.setId(id);
        return note;
    }
    
    public void saveContent(Note editingNote, String content){
        if (editingNote.getContent() == null || !editingNote.getContent().equals(content)){
            editingNote.setContent(content);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            editingNote.setDate(new Timestamp(new Date()));
            db.collection("notes")
                    .document(editingNote.getId())
                    .set(editingNote);

        }
    }
}