package com.example.clubmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {

    private EventAdapter eventAdapter;
    private List<Events> eventList;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.eventList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize event list
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

        // Fetch events from Firestore
        fetchEvents();

        return view;
    }

    private void fetchEvents() {
        Query query = firestore.collection("events").orderBy("date");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                eventList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String date = document.getString("date");
                    String guest = document.getString("guest");
                    String title = document.getString("title");
                    String venue = document.getString("venue");

                    Events event = new Events(date, guest, title, venue);
                    eventList.add(event);
                }
                eventAdapter.notifyDataSetChanged();
            } else {
                // Handle error
            }
        });
    }
}
