package com.gst.socialcomponents.main.main.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.gst.socialcomponents.adapters.UserAdapterdl;
import com.gst.socialcomponents.model.Profilefire;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ActivationFragment extends Fragment {




    private OnFragmentInteractionListener mListener;


    RecyclerView recyclerView;
    String residence ;
    private DatabaseReference reference1,reference2;
    FirebaseUser firebaseUser;



    public ActivationFragment() {

    }


    public static ActivationFragment newInstance(String param1, String param2) {
        ActivationFragment fragment = new ActivationFragment();
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

        View view = inflater.inflate(R.layout.fragment_activation, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_toolmod);

        SharedPreferences prefs = getContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        getdata();







        return view;
    }


    private void getdata() {

        ArrayList<Profilefire> profiles = new ArrayList() ;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference1 = FirebaseDatabase.getInstance().getReference().child("profiles");
        reference1.keepSynced(true);
        reference1.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        profiles.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Profilefire profile = ds.getValue(Profilefire.class);
                            if(profile.getResidence().equals(residence)){
                                if(!profile.getId().equals(firebaseUser.getUid())){
                                    profiles.add(profile);

                                }
                            }
                        }
                        retrivedata(profiles);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "error occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void changestate(String userid,Boolean state,View view){


        changeactivationstate(userid,state);



        String message = "";
        if(state){
            message="Compte activé";
        }else {
            message="Compte Désactivé";
        }
        int duration = Snackbar.LENGTH_SHORT;
        showSnackbar(view, message, duration);
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }

    void retrivedata(ArrayList profiles){

         recyclerView.setHasFixedSize(true);
        UserAdapterdl adapter;
        adapter=new UserAdapterdl(profiles);
        recyclerView.setAdapter(adapter);
      LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
      layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
      recyclerView.setLayoutManager(layoutManager);
    }

    void changeactivationstate(String userid,Boolean state){

        reference2 = FirebaseDatabase.getInstance().getReference().child("profiles");
        reference2.keepSynced(true);
        reference2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {

                            Profilefire profilefire = ds.getValue(Profilefire.class);
                            if((profilefire.getId().equals(userid))){
                                reference2.child(userid).child("active").setValue(state);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error connexion", Toast.LENGTH_SHORT).show();
                    }
                });


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
