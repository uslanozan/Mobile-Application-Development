package msku.ceng.madlab.Week2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Buton tanımladık
    Button btnArrayAdapter;
    Button btnCustomArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Butonu xml deki butonla eşleştirdik
        btnArrayAdapter = findViewById(R.id.btnArrayAdapter);
        // Butona bastığımız zaman çalışacak fonksiyonlar
        btnArrayAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // intent objesine 2 parametre gireriz.
                // ilki hangi pencereden açıldığı ikincisi hangi pencere açılacağı
                Intent intent = new Intent(MainActivity.this,
                        ArrayAdapterActivity.class);
                startActivity(intent);
            }
        });

        btnCustomArrayAdapter = findViewById(R.id.btnCustomArrayAdapter);
        btnCustomArrayAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        CustomAdapterActivity.class);
                startActivity(intent);
            }
        });


    }
}