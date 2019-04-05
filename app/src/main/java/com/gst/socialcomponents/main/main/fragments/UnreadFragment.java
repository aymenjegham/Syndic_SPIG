package com.gst.socialcomponents.main.main.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.TicketAdapter;
import com.gst.socialcomponents.adapters.TicketAdapterMod;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.listeners.SwipeControllerActions;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class UnreadFragment extends Fragment {





    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private TicketAdapter adapter;


    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private DatabaseReference reference1,reference2;

    FirebaseStorage storage;
    StorageReference storageReference;
    String residence ;
    SwipeController swipeController = null;





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

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

        swipeController = new SwipeController(new SwipeControllerActions() {

            @Override
            public void onLeftClicked(int position) {
                super.onLeftClicked(position);
                changetoencours(position);
            }

            @Override
            public void onLeftClicked2(int position) {
                super.onLeftClicked2(position);
                changetocloture(position);


            }
        });


        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        SharedPreferences prefs = getContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }

        getdata();




       return view;
    }

    private void getdata() {

        ArrayList<TicketRetrieve> tickets = new ArrayList() ;
        ArrayList<String> ticketscreators = new ArrayList() ;

        reference1 = FirebaseDatabase.getInstance().getReference().child("Tickets").child(residence);
        reference1.keepSynced(true);
        reference1.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            tickets.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot ds2 : ds.getChildren()){
                                TicketRetrieve ticket = ds2.getValue(TicketRetrieve.class);
                                 if(ticket.getState().equals("envoyé")){

                                     tickets.add(ticket);
                                    ticketscreators.add(ds.getKey());
                                }
                                retrivedata(tickets,ticketscreators);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "error occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void changetoencours(int position){
        ArrayList<TicketRetrieve> ticketstoencours = new ArrayList() ;

        reference2 = FirebaseDatabase.getInstance().getReference().child("Tickets").child(residence);
        reference2.keepSynced(true);
        reference2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot ds2 : ds.getChildren()){
                                TicketRetrieve ticket = ds2.getValue(TicketRetrieve.class);
                                if((ticket.getState().equals("envoyé"))){
                            ticketstoencours.add(ticket);
                        }
                    }
                }
                        TicketRetrieve newticket =ticketstoencours.get(position);
                         reference2.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                            for (DataSnapshot ds2 : ds.getChildren()){
                                                TicketRetrieve ticket = ds2.getValue(TicketRetrieve.class);

                                                if(((ticket.getTimestamp().toString()).equals((newticket.getTimestamp()).toString()) )){

                                                     reference2.child(ds.getKey()).child(ds2.getKey()).child("state").setValue("en cours");
                                                    getdata();
                                                }
                                            }

                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                      }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Error connexion", Toast.LENGTH_SHORT).show();
                     }
                });


    }

    void changetocloture(int position){
        ArrayList<TicketRetrieve> ticketstoencours = new ArrayList() ;

        reference2 = FirebaseDatabase.getInstance().getReference().child("Tickets").child(residence);
        reference2.keepSynced(true);
        reference2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            for (DataSnapshot ds2 : ds.getChildren()){
                                TicketRetrieve ticket = ds2.getValue(TicketRetrieve.class);
                                if((ticket.getState().equals("envoyé"))){
                                    ticketstoencours.add(ticket);
                                }
                            }
                        }
                        TicketRetrieve newticket =ticketstoencours.get(position);
                        reference2.addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                            for (DataSnapshot ds2 : ds.getChildren()){
                                                TicketRetrieve ticket = ds2.getValue(TicketRetrieve.class);

                                                if(((ticket.getTimestamp().toString()).equals((newticket.getTimestamp()).toString()) )){

                                                    reference2.child(ds.getKey()).child(ds2.getKey()).child("state").setValue("cloturé");
                                                    getdata();
                                                }
                                            }

                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Error connexion", Toast.LENGTH_SHORT).show();
                    }
                });


    }



    void retrivedata(ArrayList tickets, ArrayList<String> ticketscreators){

        recyclerView.setHasFixedSize(true);
        TicketAdapterMod adapter;
        Collections.reverse(tickets);
        Collections.reverse(ticketscreators);

        adapter=new TicketAdapterMod(tickets,ticketscreators);
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
