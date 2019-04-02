package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.ReunionHolder;
import com.gst.socialcomponents.adapters.holders.TicketHolder;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.ReunionRetrieve;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ReunionsAdapter extends RecyclerView.Adapter<ReunionHolder> {


    private ArrayList<ReunionRetrieve> reunions;
    private String residence;
    private Context cxt;
    DatabaseReference reference,reference2,reference3 ;
    private FirebaseUser firebaseUser;
    String user="";


    public ReunionsAdapter(ArrayList<ReunionRetrieve> reunions, Context applicationContext) {
        this.cxt=applicationContext;
        this.reunions=reunions;
     }

    @Override
    public ReunionHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

            View reunionCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.reuniontcard,parent,false);
            return new ReunionHolder(reunionCard);


    }

    @Override
    public void onBindViewHolder(@NonNull ReunionHolder reunionHolder, int i) {
        final ReunionRetrieve reunionRetrieve =reunions.get(i);
        reunionHolder.updateUI(reunionRetrieve);

        SharedPreferences prefs = cxt.getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        reunionHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long iduniq =reunionRetrieve.getTimestamp();
              updateattending(reunionRetrieve,residence,iduniq,reunionHolder,true);

             }
        });

        reunionHolder.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long iduniq =reunionRetrieve.getTimestamp();
                updateattending(reunionRetrieve,residence,iduniq,reunionHolder,false);
            }
        });


    }


    private void updateattending(ReunionRetrieve reunionRetrieve, String residence,Long iduni,ReunionHolder reunionHolder,Boolean statetoup) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    reference= FirebaseDatabase.getInstance().getReference();
        reference2= FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Reunions").child(residence).orderByChild("timestamp").equalTo(iduni);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot issue : dataSnapshot.getChildren()) {
                        for (DataSnapshot users : issue.getChildren()) {
                            for (DataSnapshot levels : users.getChildren()) {
                                for (DataSnapshot state : levels.getChildren()) {
                                if(state.getValue().equals(firebaseUser.getUid())){
                                  reference2=levels.child("state").getRef();
                                    reference3=levels.child("seen").getRef();

                                  reference2.setValue(statetoup);
                                  reference3.setValue(true);




                                }
                                }

                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
