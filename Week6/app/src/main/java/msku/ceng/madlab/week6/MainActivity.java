package msku.ceng.madlab.week6;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity
        implements MovieFragment.OnMovieSelected{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void movieSelected(Movie movie) {
        int displayMode = getResources().getConfiguration().orientation;
        if (displayMode== Configuration.ORIENTATION_PORTRAIT){
            Intent intent = new Intent(this,DetailsActivity.class);
            intent.putExtra("movie",(Serializable) movie);
            startActivity(intent);
        }
        else {
            DetailsFragment df =
                    (DetailsFragment) getSupportFragmentManager().findFragmentByTag("details");
            if (df==null){
                FragmentTransaction fits = getSupportFragmentManager().beginTransaction();
                df = DetailsFragment.newInstance(movie);
                fits.add(R.id.container,df,"details");
                fits.commit();
            }
            else{
                df.setMovie(movie,findViewById(R.id.container));
            }
        }

    }
}