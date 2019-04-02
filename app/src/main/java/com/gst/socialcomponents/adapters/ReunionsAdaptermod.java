package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.ReunionHolder;
import com.gst.socialcomponents.main.main.DetailReunionActivity;
import com.gst.socialcomponents.model.ReunionRetrieve;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ReunionsAdaptermod extends RecyclerView.Adapter<ReunionHolder> {


    private ArrayList<ReunionRetrieve> reunions;
    private String residence;
    private Context cxt;
    DatabaseReference reference,reference2,reference3 ;
    private FirebaseUser firebaseUser;
    String user="";


    public ReunionsAdaptermod(ArrayList<ReunionRetrieve> reunions, Context applicationContext) {
        this.cxt=applicationContext;
        this.reunions=reunions;
     }

    @Override
    public ReunionHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

            View reunionCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.reuniontcardmod,parent,false);
            return new ReunionHolder(reunionCard);
    }

    @Override
    public void onBindViewHolder(@NonNull ReunionHolder reunionHolder, int i) {
        final ReunionRetrieve reunionRetrieve =reunions.get(i);
        reunionHolder.updateUI(reunionRetrieve);

        reunionHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(cxt, DetailReunionActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(reunionRetrieve);
                intent.putExtra("myarray",json);
                cxt.startActivity(intent);

            }
        });




    }





    @Override
    public int getItemCount() {



        return reunions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
         return position;
    }
}
