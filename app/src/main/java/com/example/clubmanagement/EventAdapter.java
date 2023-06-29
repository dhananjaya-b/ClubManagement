package com.example.clubmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Events> eventList;

    public EventAdapter(List<Events> eventList) {
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Events event = eventList.get(position);
        holder.dateTextView.setText("Date : "+event.getDate());
        holder.guestTextView.setText(event.getGuest());
        holder.titleTextView.setText("Event : "+event.getTitle());
        holder.venueTextView.setText("Venue : "+event.getVenue());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView guestTextView;
        TextView dateTextView;
        TextView venueTextView;


        public EventViewHolder(@NonNull View itemView) {
            super(itemView);


            titleTextView = itemView.findViewById(R.id.title);
            guestTextView = itemView.findViewById(R.id.guest);
            dateTextView = itemView.findViewById(R.id.date);
            venueTextView = itemView.findViewById(R.id.venue);

        }
    }
}

