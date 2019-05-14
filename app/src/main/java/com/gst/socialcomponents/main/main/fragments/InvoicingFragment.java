package com.gst.socialcomponents.main.main.fragments;

import android.app.AlertDialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.gst.socialcomponents.Application;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.Adapterappartments;
import com.gst.socialcomponents.adapters.UserAdapterFacture;
import com.gst.socialcomponents.adapters.holders.UserHolderAppartment;
import com.gst.socialcomponents.adapters.holders.UserHolderFactureCheck;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.main.editProfile.EditProfileActivity;
import com.gst.socialcomponents.main.main.FacturationActivity;
import com.gst.socialcomponents.main.main.ToolsActivityMod;
import com.gst.socialcomponents.model.Appartements;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.Profilefire;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.facebook.FacebookSdk.getApplicationContext;

public class InvoicingFragment extends Fragment {


     private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    String residence ;
    private DatabaseReference reference1,reference2;
    FirebaseUser firebaseUser;
    Button facture;
    ToggleButton ajoutall;
    ArrayList<Profilefire> profilestoinvoice= new ArrayList() ;
    ArrayList<String> residences,appartements;
    ArrayList<Integer> numresides;
    private APIService mAPIService;




    public InvoicingFragment() {
    }


    public static InvoicingFragment newInstance(String param1, String param2) {
        InvoicingFragment fragment = new InvoicingFragment();
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
        View view = inflater.inflate(R.layout.fragment_invoicing, container, false);
          recyclerView = view.findViewById(R.id.recycler_view_invoicing);
        ajoutall=view.findViewById(R.id.buttonajout);
        facture =view.findViewById(R.id.buttonfacture);
        facture.setEnabled(false);
        facture.setAlpha(.5f);


        SharedPreferences prefs = getContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getNumChantier(residence).enqueue(new Callback<NumChantier>() {
            @Override
            public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
               getAppartements(Integer.valueOf(response.body().getCbmarq()));
            }

            @Override
            public void onFailure(Call<NumChantier> call, Throwable t) {

            }
        });


       // getdata("");

/*
        ajoutall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

             }
        });

        facture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(getActivity(),FacturationActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(profilestoinvoice);
                intent.putExtra("mylist",json);
                startActivity(intent);




            }
        });


*/

        return view;
    }


    public ArrayList<String> getAppartements(Integer numreside) {
        appartements = new ArrayList() ;
        numresides = new ArrayList() ;



        mAPIService.getListOfAppartements(numreside).enqueue(new Callback<List<Appartements>>() {

            @Override
            public void onResponse(Call<List<Appartements>> call, Response<List<Appartements>> response) {

                for(int i=0;i<response.body().size();i++){
                    appartements.add(response.body().get(i).getA_intitule());
                    numresides.add(response.body().get(i).getCbmarq());
                 }
                recyclerView.setHasFixedSize(true);
                Adapterappartments adapter;
                adapter=new Adapterappartments(appartements,numresides,ajoutall,facture,false,getContext());
                recyclerView.setAdapter(adapter);
                LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);

                ajoutall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {

                            facture.setEnabled(true);
                            facture.setAlpha(1f);

                            recyclerView.setHasFixedSize(true);
                            Adapterappartments adapter;
                            adapter=new Adapterappartments(appartements,numresides,ajoutall,facture,true,getContext());
                            recyclerView.setAdapter(adapter);
                            LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);




                        } else {
                            facture.setEnabled(false);
                            facture.setAlpha(.5f);

                            recyclerView.setHasFixedSize(true);
                            Adapterappartments adapter;
                            adapter=new Adapterappartments(appartements,numresides,ajoutall,facture,false,getContext());
                            recyclerView.setAdapter(adapter);
                            LinearLayoutManager layoutManager =new LinearLayoutManager(getContext());
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            recyclerView.setLayoutManager(layoutManager);
                        }

                    }
                });


            }

            @Override
            public void onFailure(Call<List<Appartements>> call, Throwable t) {

            }
        });

        return appartements;
    }


/*
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
            profilestoinvoice=profiles;


        }

    }

    */

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

    public void gettolist( ArrayList<Profilefire> profilesingle) {



    }






    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
