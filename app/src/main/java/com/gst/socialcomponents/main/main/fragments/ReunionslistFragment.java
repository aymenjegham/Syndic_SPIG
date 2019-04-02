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
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.ReunionsAdapter;
import com.gst.socialcomponents.adapters.ReunionsAdaptermod;
import com.gst.socialcomponents.main.main.CalendarActivity;
import com.gst.socialcomponents.model.Coming;
import com.gst.socialcomponents.model.ReunionRetrieve;

import java.text.ParseException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ReunionslistFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    String residence;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    RecyclerView recyclerView;

    public ReunionslistFragment() {

    }


    public static ReunionslistFragment newInstance(String param1, String param2) {
        ReunionslistFragment fragment = new ReunionslistFragment();
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
        View view = inflater.inflate(R.layout.fragment_reunionslist, container, false);
       recyclerView =view.findViewById(R.id.rv_reunionlist);

        SharedPreferences prefs = getContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }


        ArrayList<ReunionRetrieve> reunions = new ArrayList() ;

        reference = FirebaseDatabase.getInstance().getReference().child("Reunions").child(residence);
        reference.keepSynced(true);
        reference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reunions.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            ReunionRetrieve reunion = ds.getValue(ReunionRetrieve.class);
                          //  ArrayList<Coming> members=new ArrayList<>();
                           // members=reunion.getUserids();

                            //for(int i=0;i<members.size();i++){

                              //  if(members.get(i).getUsersid() .equals(firebaseUser.getUid())){
                                  reunions.add(reunion);
                                //}
                            //}

                            setupRecyclerview(reunions,view);
                         }
                        //try {
                          // setupCalendar(reunions);
                      //  } catch (ParseException e) {
                        //    e.printStackTrace();
                        //}


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                });
        return  view;
    }

    private void setupRecyclerview(ArrayList<ReunionRetrieve> reunions,View v) {
        recyclerView.setHasFixedSize(false);
        ReunionsAdaptermod adapter;
        adapter=new ReunionsAdaptermod(reunions,getContext());
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
        void onFragmentInteraction(Uri uri);
    }
}
