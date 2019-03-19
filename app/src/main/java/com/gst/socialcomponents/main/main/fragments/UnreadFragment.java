package com.gst.socialcomponents.main.main.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.TicketAdapter;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class UnreadFragment extends Fragment {





    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private TicketAdapter adapter;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private DatabaseReference reference1;

    FirebaseStorage storage;
    StorageReference storageReference;
    String residence ;





    public UnreadFragment() {
    }


    public static UnreadFragment newInstance(String param1, String param2) {
        UnreadFragment fragment = new UnreadFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view =inflater.inflate(R.layout.fragment_unread, container, false);
        recyclerView =view.findViewById(R.id.recycler_view_unread);


        SharedPreferences prefs = getContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }


        ArrayList<TicketRetrieve> tickets = new ArrayList() ;

        reference1 = FirebaseDatabase.getInstance().getReference().child("Tickets").child(residence);
        reference1.keepSynced(true);
        reference1.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        long count = dataSnapshot.getChildrenCount();

                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot ds2 : ds.getChildren()){
                                TicketRetrieve ticket = ds2.getValue(TicketRetrieve.class);
                                if(ticket.getState().equals("envoyé")){
                                     tickets.add(ticket);
                                    retrivedata(tickets);
                                }

                            }

                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "error occured", Toast.LENGTH_SHORT).show();
                    }
                });








       return view;
    }

    void retrivedata(ArrayList tickets){


        recyclerView.setHasFixedSize(true);
        TicketAdapter adapter;
        adapter=new TicketAdapter(tickets);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
