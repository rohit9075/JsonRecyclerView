package com.rohit.jsonrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>
{
    private List<Model> mUserList;
    private Context context;

    RecyclerAdapter(Context context, List<Model> mUserList) {
        this.mUserList=mUserList;
        this.context=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_items,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model user=mUserList.get(position);
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.gender.setText(user.getGender());
        holder.id.setText(user.getId());

    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView email;
        private TextView gender;
        private TextView id;

        MyViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            email=(TextView)itemView.findViewById(R.id.email);
            gender=(TextView)itemView.findViewById(R.id.gender);
            id=(TextView)itemView.findViewById(R.id.id);



        }
    }
}
