package LLDBookMyShow.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import LLDBookMyShow.Constants.City;
import LLDBookMyShow.Model.Movie;
import LLDBookMyShow.Model.Show;
import LLDBookMyShow.Model.Theathre;

public class TheathreController {
    Map<City, List<Theathre>> cityVsTheathre;
    List<Theathre> allTheathre;

    public TheathreController(){
        cityVsTheathre = new HashMap<>();
        allTheathre = new ArrayList<>();

    }

    public void addTheathre(Theathre theatre, City city){
        allTheathre.add(theatre);
        cityVsTheathre.computeIfAbsent(city, k ->new ArrayList<>()).add(theatre);

    }

    public Map<Theathre, List<Show>> getAllShow(Movie movie, City city) {
        return cityVsTheathre.getOrDefault(city, Collections.emptyList()).stream()
        .map(theatre -> Map.entry(theatre, 
            theatre.getShows().stream()
                .filter(show -> show.getMovie().getMovieId() == movie.getMovieId())
                .collect(Collectors.toList())))
        .filter(entry -> !entry.getValue().isEmpty()) // Remove empty lists
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
