package com.gst.socialcomponents.main.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.UserAdapterFacture;
import com.gst.socialcomponents.main.main.FacturationActivity;
import com.gst.socialcomponents.main.main.ReunionActivity;
import com.gst.socialcomponents.model.Profilefire;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;


public class ReunionFragment extends Fragment {




    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    String residence ;
    private DatabaseReference reference1,reference2;
    FirebaseUser firebaseUser;
    Button inviter;
    ToggleButton ajoutalltoreunion;
    ArrayList<Profilefire> profilestoinvite= new ArrayList() ;




    public ReunionFragment() {
    }

    public static ReunionFragment newInstance(String param1, String param2) {
        ReunionFragment fragment = new ReunionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reunion, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_reunion);
        ajoutalltoreunion=view.findViewById(R.id.buttonajoutreunion);
        inviter =view.findViewById(R.id.buttoninviter);
        inviter.setAlpha(.5f);


        SharedPreferences prefs = getContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);


        getdata("");


        ajoutalltoreunion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    getdata("getall");
                    inviter.setEnabled(true);
                    inviter.setAlpha(1f);

                } else {
                    getdata("hideall");
                    inviter.setEnabled(false);
                    inviter.setAlpha(.5f);

                }
            }
        });

        inviter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(), ReunionActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(profilestoinvite);
                intent.putExtra("mylist",json);
                startActivity(intent);


            }
        });


        return  view ;
    }




    private void getdata(String value) {

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
                        retrivedata(profiles,value);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "error occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    void retrivedata(ArrayList profiles,String value){

        // recyclerView.setHasFixedSize(true);
        UserAdapterFacture adapter;
        adapter=new UserAdapterFacture(profiles);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        if(value.equals("getall")){

            adapter.selectAll();
            profilestoinvite=profiles;


        }

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
