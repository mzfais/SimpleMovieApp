package id.ac.itn.mymovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import id.ac.itn.mymovieapp.adapter.MovieAdapter;
import id.ac.itn.mymovieapp.model.Movie;
import id.ac.itn.mymovieapp.model.MovieList;
import id.ac.itn.mymovieapp.viewmodel.MovieViewModel;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView rvMovie;
    MovieAdapter adapter;
    MovieViewModel viewModel;
    ProgressBar progressBar;
    SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMovie = findViewById(R.id.rvMovie);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        srl = findViewById(R.id.swipe);
        srl.setOnRefreshListener(this);
        final int orientation = getResources().getConfiguration().orientation;
        int kolom;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            kolom = 2;
        } else {
            kolom = 4;
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, kolom);
        rvMovie.setLayoutManager(gridLayoutManager);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        viewModel.getMovieList().observe(this, new Observer<MovieList>() {
            @Override
            public void onChanged(MovieList movies) {
                if (movies.getResults() != null) {
                    adapter = new MovieAdapter(MainActivity.this, movies.getResults());
                    adapter.notifyDataSetChanged();
                    rvMovie.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Error: " + movies.getError().getMessage(), Toast.LENGTH_LONG).show();
                }

                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onRefresh() {
        srl.setRefreshing(true);
        viewModel.Refresh();
        srl.setRefreshing(false);
    }
}
