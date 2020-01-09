package com.uylab.retrofitanddatabinding.ui.seminar_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.uylab.retrofitanddatabinding.R;
import com.uylab.retrofitanddatabinding.databinding.ItemSeminarTitleBinding;
import com.uylab.retrofitanddatabinding.util.model.seminar_list.Datum;

import java.util.List;

public class Seminar_title_Adapter extends RecyclerView.Adapter<Seminar_title_Adapter.ViewHolder> {
    private ItemSeminarTitleBinding iBinding;
    private List<Datum> dataList;
    private Seminar_Title_Listener title_listener;

    public void setTitle_listener(Seminar_Title_Listener title_listener) {
        this.title_listener = title_listener;
    }

    public Seminar_title_Adapter(List<Datum> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from ( parent.getContext () );
        iBinding= DataBindingUtil.inflate ( layoutInflater, R.layout.item_seminar_title,parent,false );
        View view = iBinding.getRoot ();
        Seminar_title_Adapter.ViewHolder holder= new ViewHolder ( view );
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        iBinding.textView2.setText ( dataList.get ( position ).getName () );
        iBinding.textView2.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                if(title_listener != null){
                    title_listener.OnItemClick ( dataList.get ( position ),position );
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return dataList.size ();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super ( itemView );
        }
    }
    public interface Seminar_Title_Listener{
        public void OnItemClick(Datum datum, int position);
    }
}
