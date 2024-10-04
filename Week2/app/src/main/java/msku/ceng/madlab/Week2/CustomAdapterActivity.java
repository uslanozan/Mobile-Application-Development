package msku.ceng.madlab.Week2;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        final List<Animal> animals = new ArrayList<Animal>();
        animals.add(new Animal("Cat",R.mipmap.wayne));
        animals.add(new Animal("Butterfly",R.mipmap.mert));


        setContentView(R.layout.activity_custom_adapter);

        final ListView listView = findViewById(R.id.listview);
        AnimalAdapter animalAdapter = new AnimalAdapter(this, animals);
        listView.setAdapter(animalAdapter);
    }
}