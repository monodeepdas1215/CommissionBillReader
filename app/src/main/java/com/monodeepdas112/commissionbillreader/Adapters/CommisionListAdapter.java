package com.monodeepdas112.commissionbillreader.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.monodeepdas112.commissionbillreader.R;
import com.monodeepdas112.commissionbillreader.Models.CommissionEntity;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by monodeep on 19-01-2018.
 */

public class CommisionListAdapter extends RecyclerView.Adapter<CommisionListAdapter.CommisionEntityHolder> {

    public static final int ASCENDING=0;
    public static final int DESCENDING=1;

    public static final int PREMIUM=1;
    public static final int COMMISSION=2;

    private List<CommissionEntity> list;
    private LayoutInflater mInflater;
    public CommisionListAdapter(List<CommissionEntity> commisionList, Context context){
        list=commisionList;
        mInflater=LayoutInflater.from(context);
    }

    public static class CommisionEntityHolder extends RecyclerView.ViewHolder {

        public TextView policyNum,name,prem,commission,dueDate;

        public CommisionEntityHolder(View itemView) {
            super(itemView);
            policyNum=itemView.findViewById(R.id.policyNum);
            name=itemView.findViewById(R.id.name);
            prem=itemView.findViewById(R.id.premium);
            commission=itemView.findViewById(R.id.commission);
            dueDate=itemView.findViewById(R.id.premiumDue);
        }
    }

    @Override
    public CommisionListAdapter.CommisionEntityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=mInflater.inflate(R.layout.row_item,parent,false);
        CommisionEntityHolder holder=new CommisionEntityHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(CommisionListAdapter.CommisionEntityHolder holder, int position) {
        holder.policyNum.setText("PN : "+list.get(position).getPolicyNum());
        if(list.get(position).getName()!=null)
            holder.name.setText("Name : "+list.get(position).getName());
        else holder.name.setText("Name : Unknown");
        holder.prem.setText("Premium : Rs "+list.get(position).getPremium().toString());
        holder.commission.setText("Commission : Rs "+list.get(position).getCommission().toString());
        holder.dueDate.setText("Due Date : "+list.get(position).getPremDueDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void sortTheDataset(int field,int sortOrder){
        if (field==PREMIUM){
            //PREMIUM sort

            if (sortOrder==ASCENDING) {
                Collections.sort(list, new Comparator<CommissionEntity>() {
                    @Override
                    public int compare(CommissionEntity o1, CommissionEntity o2) {
                        return Double.compare(o1.getPremium(), o2.getPremium());
                    }
                });
            }else {
                Collections.sort(list, new Comparator<CommissionEntity>() {
                    @Override
                    public int compare(CommissionEntity o1, CommissionEntity o2) {
                        return Double.compare(o2.getPremium(), o1.getPremium());
                    }
                });
            }
        }else {
            //COMMISSION sort
            if (sortOrder==ASCENDING){
                Collections.sort(list, new Comparator<CommissionEntity>() {
                    @Override
                    public int compare(CommissionEntity o1, CommissionEntity o2) {
                        return Double.compare(o1.getCommission(), o2.getCommission());
                    }
                });
            }else {
                Collections.sort(list, new Comparator<CommissionEntity>() {
                    @Override
                    public int compare(CommissionEntity o1, CommissionEntity o2) {
                        return Double.compare(o2.getCommission(), o1.getCommission());
                    }
                });
            }
        }

        notifyDataSetChanged();
    }
}
