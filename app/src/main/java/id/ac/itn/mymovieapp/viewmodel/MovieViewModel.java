package id.ac.itn.mymovieapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import id.ac.itn.mymovieapp.BuildConfig;
import id.ac.itn.mymovieapp.adapter.MovieAdapter;
import id.ac.itn.mymovieapp.api.ApiClient;
import id.ac.itn.mymovieapp.model.Movie;
import id.ac.itn.mymovieapp.model.MovieList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    private static final String TAG = "MovieViewModel";
    MutableLiveData<MovieList> movieList;

    public LiveData<MovieList> getMovieList() {
        if (movieList == null) {
            movieList = new MutableLiveData<>();
            setMovieList();
        }
        return movieList;
    }

    private void setMovieList() {
        ApiClient.getInstance().getApi().getMovieList(BuildConfig.MovieAPIKey, "en-US")
                .enqueue(new Callback<MovieList>() {
                    @Override
                    public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                        if (response.isSuccessful()) {
                            movieList.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieList> call, Throwable t) {
                        movieList.postValue(new MovieList(t));
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    public void Refresh() {
        if (movieList != null) {
            setMovieList();
        }
    }
}
