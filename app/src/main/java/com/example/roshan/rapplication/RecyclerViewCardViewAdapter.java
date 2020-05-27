package com.example.roshan.rapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import java.util.List;

public class RecyclerViewCardViewAdapter extends RecyclerView.Adapter<RecyclerViewCardViewAdapter.ViewHolder> {

    Context context;

    List<subjects> subjects;

    public RecyclerViewCardViewAdapter(List<subjects> getDataAdapter, Context context){

        super();

        this.subjects = getDataAdapter;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        subjects getDataAdapter1 =  subjects.get(position);

        holder.SubjectName.setText(getDataAdapter1.getName());
        holder.desc.setText(getDataAdapter1.getDesc());
        holder.img.setImageDrawable(context.getResources().getDrawable(getDataAdapter1.getImg()));
        holder.price.setText(getDataAdapter1.getPrice());

    }

    @Override
    public int getItemCount() {

        return subjects.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView SubjectName;
        public TextView desc;
        public ImageView img;
        public TextView price;


        public ViewHolder(View itemView) {

            super(itemView);

            SubjectName = (TextView) itemView.findViewById(R.id.TextViewCard) ;
            desc=(TextView)itemView.findViewById(R.id.textViewShortDesc);
            img=(ImageView) itemView.findViewById(R.id.imageView);
            price=(TextView)itemView.findViewById(R.id.textViewPrice);


        }
    }
}