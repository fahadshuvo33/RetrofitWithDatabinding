package com.uylab.retrofitanddatabinding.ui.seminar_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.uylab.retrofitanddatabinding.R;
import com.uylab.retrofitanddatabinding.databinding.ItemSeminarBinding;
import com.uylab.retrofitanddatabinding.util.model.seminar_list.Datum;
import com.uylab.retrofitanddatabinding.util.model.seminar_list.Seminar;

import java.util.List;

public class Seminar_Adapter extends RecyclerView.Adapter<Seminar_Adapter.ViewHolder>{
    private ItemSeminarBinding sBinding;
    private List<Seminar> seminarList;
    private Seminar_AdapterClickListener adapterClickListener;

    public void setAdapterClickListener(Seminar_AdapterClickListener adapterClickListener) {
        this.adapterClickListener = adapterClickListener;
    }

    public Seminar_Adapter(List<Seminar> seminarList) {
        this.seminarList = seminarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from ( parent.getContext () );
        sBinding= DataBindingUtil.inflate ( layoutInflater, R.layout.item_seminar,parent,false );
        View view = sBinding.getRoot ();
        Seminar_Adapter.ViewHolder holder= new ViewHolder ( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        sBinding.title.setText ( seminarList.get ( position ).getTitle () );
        sBinding.date.setText ( seminarList.get ( position ).getStartDate () );
        sBinding.durationText.setText ( seminarList.get ( position ).getDuration () );
       holder.itemView.setOnClickListener ( new View.OnClickListener () {
           @Override
           public void onClick(View view) {
               if(adapterClickListener!=null)
               {
                   adapterClickListener.itemClickListener ( seminarList.get ( position ),position );
               }
           }
       } );

    }

    @Override
    public int getItemCount() {
        return seminarList.size ();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );
        }
    }
    public interface Seminar_AdapterClickListener{
        public void itemClickListener(Seminar datum ,int position);
    }
}
