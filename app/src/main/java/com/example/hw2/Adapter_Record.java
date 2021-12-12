package com.example.hw2;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class Adapter_Record<recordItemClickListener> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private Activity activity;
    private ArrayList<Record> records = new ArrayList<>();
    private RecordItemClickListener recordItemClickListener;

    public Adapter_Record(Activity activity, ArrayList<Record> records) {
        this.activity = activity;
        this.records = records;
    }


    public Adapter_Record setRecordItemClickListener(RecordItemClickListener recordItemClickListener) {
        this.recordItemClickListener = recordItemClickListener;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_record, viewGroup, false);
        return new RecordViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        RecordViewHolder recordViewHolder = (RecordViewHolder) holder;
        Record record = getItem(position);
        recordViewHolder.record_LBL_score.setText("" + record.getScore());
        recordViewHolder.record_LBL_lag.setText(record.getLat());
        recordViewHolder.record_LBL_log.setText(record.getLot());


    }

    @Override
    public int getItemCount() {
        if (records==null){
            return 0;
        }
        return records.size();
    }

    private Record getItem(int position) {
        return records.get(position);
    }

    public interface RecordItemClickListener {
        void recordItemClicked(Record item, String lat, String lot);
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView record_LBL_score;
        public MaterialTextView record_LBL_lag;
        public MaterialTextView record_LBL_log;


        public RecordViewHolder(final View itemView) {
            super(itemView);
            this.record_LBL_log = itemView.findViewById(R.id.record_LBL_log);
            this.record_LBL_lag = itemView.findViewById(R.id.record_LBL_lag);
            this.record_LBL_score = itemView.findViewById(R.id.record_LBL_score);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("pttt", "position= " + getItem(getAdapterPosition()).getLot()+getItem(getAdapterPosition()).getLot());

                    recordItemClickListener.recordItemClicked(getItem(getAdapterPosition()), getItem(getAdapterPosition()).getLat(),getItem(getAdapterPosition()).getLot());
                }
            });


        }
    }
}

