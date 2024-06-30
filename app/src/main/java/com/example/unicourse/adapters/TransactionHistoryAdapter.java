package com.example.unicourse.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.models.user.TransactionHistory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder> {

    private final  Context mContext;
    private final ArrayList<TransactionHistory> transactionHistories;
    private final String userProfileName;
    private final String userProfileImg;

    public TransactionHistoryAdapter(Context mContext, ArrayList<TransactionHistory> transactionHistories, String userProfileName, String userProfileImg) {
        this.mContext = mContext;
        this.transactionHistories = transactionHistories;
        this.userProfileName = userProfileName;
        this.userProfileImg = userProfileImg;
    }

    @NonNull
    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_transaction_history_item, parent, false);
        return new TransactionHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {
        TransactionHistory transactionHistory = transactionHistories.get(position);
        holder.bind(transactionHistory);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class TransactionHistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemTitle;
        private final TextView itemFinalPrice;
        private final TextView itemOldPrice;
        private final TextView dateTV;
        private final TextView totalItemTV;
        private final TextView itemTotalPrice;
        private final Button detailBtn;
        private final TextView usernameTxt;
        private final ImageView userImage;
        private final ImageView itemThumnail;

        public TransactionHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemTitle);
            itemFinalPrice = itemView.findViewById(R.id.itemFinalPrice);
            itemOldPrice = itemView.findViewById(R.id.itemOldPrice);
            dateTV = itemView.findViewById(R.id.dateTV);
            totalItemTV = itemView.findViewById(R.id.totalItemTV);
            itemTotalPrice = itemView.findViewById(R.id.itemTotalPrice);
            detailBtn = itemView.findViewById(R.id.detailButton);
            usernameTxt = itemView.findViewById(R.id.usernameTxt);
            userImage = itemView.findViewById(R.id.userImage);
            itemThumnail = itemView.findViewById(R.id.itemThumnail);
        }

        public void bind(TransactionHistory record) {
//            itemTitle.setText(record.getItemsCheckout().get(0).getItem().getTitle());
//            Glide.with(itemThumnail.getContext())
//                    .load(record.getItemsCheckout().get(0).getItem().getThumbnail())
//                    .into(itemThumnail);
            itemFinalPrice.setText(formatNumber(record.getTotalNewAmount()));
            itemOldPrice.setText(formatNumber(record.getTotalOldAmount()));
            dateTV.setText(record.getProcessDate().toString());
            totalItemTV.setText(String.valueOf(record.getItemsCheckout().size()));
            itemTotalPrice.setText(String.valueOf(record.getTotalNewAmount()));
            Glide.with(userImage.getContext())
                    .load(userProfileImg)
                    .into(userImage);
            usernameTxt.setText(userProfileName);

            itemOldPrice.setPaintFlags(itemOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            detailBtn.setOnClickListener(v -> {
//                detail logic here.
            });
        }

        private String formatNumber(int number) {
            return NumberFormat.getNumberInstance(Locale.GERMAN).format(number) + " VNĐ";
        }
    }
}
