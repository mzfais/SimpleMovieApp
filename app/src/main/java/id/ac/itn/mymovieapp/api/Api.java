package id.ac.itn.mymovieapp.api;

import id.ac.itn.mymovieapp.model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    //https://api.themoviedb.org/3/discover/movie?api_key=api_key&language=en-US

    @GET("discover/movie")
    Call<MovieList> getMovieList(@Query("api_key") String key,@Query("language") String lang);


}
