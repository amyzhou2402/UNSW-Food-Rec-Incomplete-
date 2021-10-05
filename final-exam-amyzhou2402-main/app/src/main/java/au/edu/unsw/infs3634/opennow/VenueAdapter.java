package au.edu.unsw.infs3634.opennow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.VenueViewHolder> implements Filterable {
    public static final int SORT_METHOD_NAME = 1;
    public static final int SORT_METHOD_LOCATION = 2; //changed from "= 1" to "= 2" to differentiate between the 2 sorting functions
    private ArrayList<Venue> mVenues;
    private ArrayList<Venue> mVenueFiltered;
    private VenueClickListener mListener;

    public VenueAdapter(ArrayList<Venue> venues, VenueClickListener listener) {
        this.mVenues = venues;
        this.mVenueFiltered = venues;
        this.mListener = listener;
    }

    @NonNull
    @Override
    //added venueadapter.
    public VenueAdapter.VenueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //changed activity_detail to venue_item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.venue_item, parent, false);
        return new VenueViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VenueViewHolder holder, int position) {
        Venue venue = mVenueFiltered.get(position); //changed 0 to position so it isnt a static position - actually takes in what list item was selected
        holder.name.setText(venue.getName());
        holder.location.setText(venue.getLocation());
        holder.openHours.setText(venue.getOpens() + ":00-" + venue.getCloses() + ":00");
        holder.image.setImageResource(
                holder.itemView.getContext().getResources().getIdentifier(
                        "@drawable/image_" + venue.getId(),
                        null,
                        holder.itemView.getContext().getPackageName()));
        holder.itemView.setTag(venue.getName()); // changed from getName to getId because tag needs to be an int. //changed back to getName
    }

    @Override
    public int getItemCount() {
        return mVenueFiltered.size(); // changed return 0 to return mVenueFiltered.size so it knows how many items there are in the list and can adjust the size of the recyclerView accordingly
    }

    public void setData(ArrayList<Venue> venues) {
        mVenueFiltered.clear();
        mVenueFiltered.addAll(venues);
        notifyDataSetChanged(); //changed from notify to notifyDataSetChanged
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                ArrayList<Venue> filteredList = new ArrayList<>();
                for(Venue venue : mVenues) {
                    if(venue.getOpens() > currentHour || venue.getCloses() < currentHour) {
                        filteredList.add(venue);
                    }
                }
                mVenueFiltered = filteredList;
                FilterResults filterResults = new FilterResults();
                filterResults.values = mVenueFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mVenueFiltered = (ArrayList<Venue>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class VenueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener { //added implements View.OnClickListener
        ImageView image;
        TextView name, location, openHours;
        VenueClickListener listener;

        public VenueViewHolder(@NonNull View itemView, VenueClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.listener = listener;
            image = itemView.findViewById(R.id.picture);
            name = itemView.findViewById(R.id.name); //changed r.id.location to name
            location = itemView.findViewById(R.id.location); //changed r.id.name to location
            openHours = itemView.findViewById(R.id.open_hours);
        }

        @Override
        public void onClick(View v) {
            listener.onClick((String) v.getTag()); //needs to be int unless 114 onclick is string
        }
    }

    public interface VenueClickListener {
        void onClick(String id); //changed to string
    }

    public void sort(final int sortMethod) {
        if(mVenueFiltered.size() > 0) {
            Collections.sort(mVenues, new Comparator<Venue>() {
                @Override
                public int compare(Venue o1, Venue o2) {
                    if(sortMethod == SORT_METHOD_NAME) {
                        return o1.getName().compareTo(o2.getName());
                    } else if(sortMethod == SORT_METHOD_LOCATION) {
                        return o1.getLocation().compareTo(o2.getLocation());
                    }
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
        notifyDataSetChanged();
    }
}
