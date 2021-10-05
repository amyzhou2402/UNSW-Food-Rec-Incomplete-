package au.edu.unsw.infs3634.opennow;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VenueDao {
    // Return a list of all venues
    @Query("SELECT * FROM venue")
    List<Venue> getVenues();
    // used in MainActivity updateListFromDatabase

    // TODO for Question 1a)
    // Implement code for a second SQL query that returns a single venue whose id matches an id parameter
    @Query("SELECT * FROM venue WHERE id == :id")
    Venue getVenuesByID(String id);


    // TODO for Question 1a)
    // Implement code for a third SQL query that returns all venues whose location matches a location parameter
    @Query("SELECT * FROM venue WHERE location == :location ")
    List<Venue> getVenuesByLocation(String location);



}
