package au.edu.unsw.infs3634.opennow;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Venue.class}, version = 1)
public abstract class VenueDatabase extends RoomDatabase {
    public abstract VenueDao venueDao();
}
