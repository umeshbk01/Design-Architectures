package LLDBookMyShow.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import LLDBookMyShow.Constants.City;
import LLDBookMyShow.Model.Movie;


public class MovieController {

    Map<City, List<Movie>> cityVsMovies;
    List<Movie> allMovies;

    public MovieController(){
        cityVsMovies = new HashMap<>();
        allMovies = new ArrayList<>();
    }

    public void addMovie(Movie movie, City city){
        allMovies.add(movie);
        cityVsMovies.computeIfAbsent(city, k -> new ArrayList<>()).add(movie);

    }

    public Movie getMovieByName(String movieName){
        Movie movie = allMovies.stream().filter(m->movieName.equalsIgnoreCase(m.getMovieName())).findFirst().orElse(null);
        return movie;
    }

    public List<Movie> getMovieByCity(City city){
        return cityVsMovies.get(city);
    }

        //REMOVE movie from a particular city, make use of cityVsMovies map

    //UPDATE movie of a particular city, make use of cityVsMovies map

    //CRUD operation based on Movie ID, make use of allMovies list


    
}
