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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.FacesAdapter;
import com.gst.socialcomponents.adapters.FacturesAdapter;
import com.gst.socialcomponents.adapters.UserAdapterFacture;
import com.gst.socialcomponents.model.Facture;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class FactureslistFragment extends Fragment {



    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    FirebaseUser firebaseUser;
    private DatabaseReference reference,reference2;
    String residence ;

    public FactureslistFragment() {
    }


    public static FactureslistFragment newInstance(String param1, String param2) {
        FactureslistFragment fragment = new FactureslistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_factureslist, container, false);
        recyclerView =view.findViewById(R.id.rvfactuefilter);

        filterbycoprop();




        return  view ;

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
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.facturefiltre_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.filtre1:
                filterbycoprop();
                return true;

            case R.id.filtre2:
                filterbyfacture();

                return true;

            case R.id.filtre3:
                filterbynopay();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterbynopay() {
    }

    private void filterbyfacture() {

        ArrayList<Facture> factures = new ArrayList() ;

        SharedPreferences prefs = getContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        reference2 = FirebaseDatabase.getInstance().getReference().child("Frais").child(residence);
        reference2.keepSynced(true);
        reference2.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        factures.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            for(DataSnapshot td : ds.getChildren()) {
                                Log.v("testingvalue",td.getValue().toString()+"   "+ ds.getKey());
                                Facture facture = ds.getValue(Facture.class);
                                factures.add(facture);
                            }
                            
                        }

                        recyclerView.setHasFixedSize(true);
                      //  FacturesAdapter adapter;
                        Collections.reverse(factures);
                        //adapter = new FacturesAdapter(factures);
                        //recyclerView.setAdapter(adapter);

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void filterbycoprop() {

        SharedPreferences prefs = getContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        ArrayList<Profilefire> profiles = new ArrayList() ;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference().child("Frais").child(residence);
        reference.keepSynced(true);
        reference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        profiles.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Query query = FirebaseDatabase.getInstance().getReference().child("profiles");
                            query.addValueEventListener(
                                    new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot2) {
                                            for (DataSnapshot snapshot: dataSnapshot2.getChildren()) {
                                                if(snapshot.getKey().equals( ds.getKey())){
                                                    Profilefire profile = snapshot.getValue(Profilefire.class);
                                                    profiles.add(profile);
                                                }
                                            }
                                            recyclerView.setHasFixedSize(true);
                                            UserAdapterFacture adapter;
                                            adapter=new UserAdapterFacture(profiles);
                                            recyclerView.setAdapter(adapter);
                                            LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
                                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                                            recyclerView.setLayoutManager(layoutManager);
                                        }


                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "error occured", Toast.LENGTH_SHORT).show();
                    }
                });



    }
}
