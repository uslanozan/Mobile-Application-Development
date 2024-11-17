package msku.ceng.madlab.week6;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    Movie movie;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private Movie movie1;

    public DetailsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie",movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtName = (TextView)view.findViewById(R.id.txtMovieName);

        txtName.setText(movie.getName());
        TextView txtYear = (TextView)view.findViewById(R.id.txtYear);
        txtYear.setText(Integer.toString(movie.
                getYear()));
        TextView txtDirector = (TextView)view.findViewById(R.id.txtDirector);
        txtDirector.setText(movie.getDirector());
        TextView txtDescription = (EditText)view.findViewById(R.id.txtDescription);
        txtDescription.setText(movie.getDescription());
        txtDescription.setEnabled(false);
        ListView listView = (ListView) view.findViewById(R.id.lstStars);
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.array_adapter,
                movie.getStars().toArray(new String[1])));
    }

    public void setMovie(Movie movie, View viewById) {

    }
}