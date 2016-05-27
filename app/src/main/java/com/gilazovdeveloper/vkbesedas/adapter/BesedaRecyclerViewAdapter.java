package com.gilazovdeveloper.vkbesedas.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gilazovdeveloper.vkbesedas.R;
import com.gilazovdeveloper.vkbesedas.custom_view.TileImageView;
import com.gilazovdeveloper.vkbesedas.model.vo.Beseda;

import java.util.List;


public class BesedaRecyclerViewAdapter extends RecyclerView.Adapter<BesedaRecyclerViewAdapter.ViewHolder> {

    private final List<Beseda> mValues;
    Context ctx;

    public BesedaRecyclerViewAdapter(Context ctx, List<Beseda> items) {
        mValues = items;
        this.ctx = ctx;
    }

    public void addItems(List<Beseda> additionItems){
        mValues.addAll(additionItems);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_beseda_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public boolean onFailedToRecycleView(ViewHolder holder) {
        return true;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Beseda beseda = mValues.get(position);

        holder.besedaTitle.setText(beseda.title);
        holder.besedaBody.setText(beseda.body);
        holder.besedaDate.setText(beseda.date);
        holder.besedaPhoto.setImage(beseda.UsersPhotoSrcList, 100);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView besedaTitle;
        public final TextView besedaDate;
        public final TextView besedaBody;
        public final TileImageView besedaPhoto;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            besedaTitle =(TextView) view.findViewById(R.id.besedaTitle);
            besedaDate =(TextView) view.findViewById(R.id.besedaDate);
            besedaBody =(TextView) view.findViewById(R.id.besedaBody);
            besedaPhoto = (TileImageView) view.findViewById(R.id.tileBesedaPhoto);
        }

    }
}
