package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.Faceholder;
import com.gst.socialcomponents.adapters.holders.Meetingholder;
import com.gst.socialcomponents.model.DataReunion;

import java.util.ArrayList;

import retrofit2.Response;

public class ReunionAdapter extends RecyclerView.Adapter<Meetingholder> {

    ArrayList<Response<DataReunion>> reunions;
    ArrayList<Integer> intuser;
    Context cxt;





    public ReunionAdapter(ArrayList<Response<DataReunion>> reunions, Context applicationContext, ArrayList<Integer> intuser,Context cxt) {
        this.reunions=reunions;
        this.intuser=intuser;
        this.cxt=cxt;
    }

    @Override
    public Meetingholder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View profilecard= LayoutInflater.from(parent.getContext()).inflate(R.layout.meet_item,parent,false);
        return new Meetingholder(profilecard);
    }

    @Override
    public void onBindViewHolder(@NonNull Meetingholder meetingholder, int i) {
        final Response<DataReunion> reunionResponse =reunions.get(i);
        final Integer iduser=intuser.get(i);
        meetingholder.updateUI(reunionResponse,iduser,cxt);

    }



    @Override
    public int getItemCount() {
        return reunions.size();
    }
}
