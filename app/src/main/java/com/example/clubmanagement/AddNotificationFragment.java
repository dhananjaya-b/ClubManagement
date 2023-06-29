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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNotificationFragment extends Fragment {
    private TextInputLayout eventNameTextInputLayout,guestTextInputLayout, dateTextInputLayout,venueTextInputLayout;
    private EditText eventNameEditText,guestEditText, dateEditText,venueEditText;

    private Button registerButton;

    private FirebaseFirestore db;

    public AddNotificationFragment() {
        // Required empty public constructor
    }

    public static AddNotificationFragment newInstance() {
        return new AddNotificationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventNameTextInputLayout = view.findViewById(R.id.notification_title);
        guestTextInputLayout = view.findViewById(R.id.description);


        eventNameEditText=eventNameTextInputLayout.getEditText();
        guestEditText=guestTextInputLayout.getEditText();


        registerButton = view.findViewById(R.id.sendNotification);

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
        String timestamp = TimestampGenerator();
        System.out.println("Timestamp: " + timestamp);
        Map<String, Object> event = new HashMap<>();
        event.put("title", eventName);
        event.put("description", guest);
        event.put("timestamp", timestamp);


        db.collection("notifications")
                .add(event)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getActivity(), "Notification sent!", Toast.LENGTH_SHORT).show();
                    eventNameEditText.setText("");
                    guestEditText.setText("");
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getActivity(), "Error sending notification: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
    public String TimestampGenerator() {
            // Create a Date object
            Date date = new Date();

            // Create a SimpleDateFormat object to define the desired timestamp format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // Format the Date object to a String containing the timestamp
            String timestamp = dateFormat.format(date);

            return timestamp;
        }

}
