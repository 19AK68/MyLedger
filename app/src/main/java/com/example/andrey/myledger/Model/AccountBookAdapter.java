package com.example.andrey.myledger.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.myledger.AddAccountBookActivity;
import com.example.andrey.myledger.R;

import java.util.List;

public class AccountBookAdapter extends RecyclerView.Adapter<AccountBookAdapter.ViewHolder> {

    private List<AccountBook> mAccountBookList;
    private Context mContext;
    private RecyclerView mRecyclerView;

    public  class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAccountName;
        private TextView tvBalanceSum;

        public  View layout;


        public ViewHolder(View v) {
            super(v);

            layout=v;
            tvAccountName = v.findViewById(R.id.nameAccount);
            tvBalanceSum =  v.findViewById(R.id.tvBalancSumm);
        }
    }

    public AccountBookAdapter (List<AccountBook> myDataset,Context context,RecyclerView  recyclerView){
        mAccountBookList = myDataset;
        mContext = context;
        mRecyclerView = recyclerView;


    }


    @NonNull
    @Override
    public AccountBookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.account_card,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountBookAdapter.ViewHolder holder, int position) {

        final AccountBook accountBook = mAccountBookList.get(position);

        holder.tvAccountName.setText(" "+accountBook.getAccountName());
        holder.tvBalanceSum.setText(" "+accountBook.getAccountBalance());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Choose option");
                builder.setMessage("Add Update or Delete Account?");
                builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gotoAddActivity(accountBook.getAccountID());
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext,"В разроботке",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }

    private void gotoAddActivity (long accountID ){

        Intent gotoAdd = new Intent(mContext,AddAccountBookActivity.class);
        gotoAdd.putExtra("USER_ID",accountID);
        mContext.startActivity(gotoAdd);
    }

    @Override
    public int getItemCount() {
        return mAccountBookList.size();
    }
}

