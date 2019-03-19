package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.MainActivity;
import com.gst.socialcomponents.room.Notif;
import com.gst.socialcomponents.utils.FormatterUtil;

import java.util.List;


public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifViewHolder> {


    private Context mCtx;
    private List<Notif> notifList;

    public NotifAdapter(Context mCtx, List<Notif> notifList) {
        this.mCtx = mCtx;
        this.notifList = notifList;
    }

    @Override
    public NotifViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.recyclerview_notifs, parent, false);
        return new NotifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotifViewHolder holder, int position) {
        Notif n = notifList.get(position);
        holder.textViewTask.setText(n.getTitle());
        holder.textViewDesc.setText(n.getDesc());

        CharSequence date = FormatterUtil.getRelativeTimeSpanString(mCtx, (long) n.getCurrenttime());


        holder.textViewFinishBy.setText(String.valueOf(date));

       // if (n.isFinished())


    }

    @Override
    public int getItemCount() {
        return notifList.size();
    }

    class NotifViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  textViewTask, textViewDesc, textViewFinishBy;

        public NotifViewHolder(View itemView) {
            super(itemView);

            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewtime);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Notif notif = notifList.get(getAdapterPosition());

            Intent intent = new Intent(mCtx, MainActivity.class);
            intent.putExtra("notif", notif);

            mCtx.startActivity(intent);
        }
    }
}