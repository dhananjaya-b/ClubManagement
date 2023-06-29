package com.example.clubmanagement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddEventFragment extends Fragment {
    private TextInputLayout eventNameTextInputLayout,guestTextInputLayout, dateTextInputLayout,venueTextInputLayout;
    private EditText eventNameEditText,guestEditText, dateEditText,venueEditText;

    private Button registerButton;

    private FirebaseFirestore db;

    public AddEventFragment() {
        // Required empty public constructor
    }

    public static AddEventFragment newInstance() {
        return new AddEventFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventNameTextInputLayout = view.findViewById(R.id.eventname);
        guestTextInputLayout = view.findViewById(R.id.guest);
        dateTextInputLayout = view.findViewById(R.id.date);
        venueTextInputLayout = view.findViewById(R.id.venue);

        eventNameEditText=eventNameTextInputLayout.getEditText();
        guestEditText=guestTextInputLayout.getEditText();
        dateEditText=dateTextInputLayout.getEditText();
        venueEditText=venueTextInputLayout.getEditText();

        registerButton = view.findViewById(R.id.addevent);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventToFirestore();
            }
        });
    }

    private void addEventToFirestore() {
        String eventName = eventNameEditText.getText().toString();
        String guest = guestEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String venue = venueEditText.getText().toString();


        // Create a new event object with the input values
        Map<String, Object> event = new HashMap<>();
        event.put("title", eventName);
        event.put("guest", guest);
        event.put("date", date);
        event.put("venue", venue);

        // Add the event to Firestore
        db.collection("events")
                .add(event)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getActivity(), "Event added successfully!", Toast.LENGTH_SHORT).show();
                    // Clear the input fields after successful addition
                    eventNameEditText.setText("");
                    guestEditText.setText("");
                    dateEditText.setText("");
                    venueEditText.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Error adding event: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
