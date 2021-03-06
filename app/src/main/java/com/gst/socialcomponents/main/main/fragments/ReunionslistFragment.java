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
import com.gst.socialcomponents.adapters.PostsAdapter;
import com.gst.socialcomponents.adapters.PostsAdapterReclam;
import com.gst.socialcomponents.adapters.ReunionsAdapter;
import com.gst.socialcomponents.adapters.ReunionsAdaptermod;
import com.gst.socialcomponents.main.main.CalendarActivity;
import com.gst.socialcomponents.model.Coming;
import com.gst.socialcomponents.model.Post;
import com.gst.socialcomponents.model.ReunionRetrieve;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

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


        ArrayList<Post> posts = new ArrayList() ;
        ArrayList<String> ids = new ArrayList() ;
        posts.clear();


        reference = FirebaseDatabase.getInstance().getReference().child("posts");//.child(residence);
        reference.keepSynced(true);
        reference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Log.v("checkingresult",ds.getValue().toString());
                            Post post = ds.getValue(Post.class);
                            if(post.isHasComplain()){
                                posts.add(post);
                                ids.add(ds.getKey());

                            }
                         }
                         setupRecyclerview(posts,ids);

                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "Connection error", Toast.LENGTH_SHORT).show();
                    }
                });
        return  view;
    }

    private void setupRecyclerview(ArrayList<Post> posts,ArrayList<String> ids) {

        recyclerView.setHasFixedSize(false);
        PostsAdapterReclam adapter;
        adapter=new PostsAdapterReclam(posts,getContext(),ids);
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
