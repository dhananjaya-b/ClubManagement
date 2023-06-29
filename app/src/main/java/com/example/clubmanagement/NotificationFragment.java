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

public class NotificationFragment extends Fragment {

    private NotificationAdapter notificationAdapter;
    private List<Notification> eventList;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.notificationList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize event list
        eventList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(eventList);
        recyclerView.setAdapter(notificationAdapter);
        // Fetch events from Firestore
        fetchEvents();

        return view;
    }

    private void fetchEvents() {
        Query query = firestore.collection("notifications").orderBy("timestamp");

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                eventList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String title = document.getString("title");
                    String description = document.getString("description");

                    Notification event = new Notification(title, description," ");
                    eventList.add(event);
                }
                notificationAdapter.notifyDataSetChanged();
            } else {
                // Handle error
            }
        });
    }
}
