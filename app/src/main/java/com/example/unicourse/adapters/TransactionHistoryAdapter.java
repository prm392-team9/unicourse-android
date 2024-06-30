package com.example.unicourse.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.models.user.TransactionHistory;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionHistoryViewHolder> {

    private final  Context mContext;
    private final ArrayList<TransactionHistory> transactionHistories;

    public TransactionHistoryAdapter(Context mContext, ArrayList<TransactionHistory> transactionHistories) {
        this.mContext = mContext;
        this.transactionHistories = transactionHistories;
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
        return transactionHistories.size();
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
            itemTitle.setText(record.getItemsCheckout().get(0).getTitle());
            Glide.with(itemThumnail.getContext())
                    .load(record.getItemsCheckout().get(0).getThumbnail())
                    .into(itemThumnail);
            itemFinalPrice.setText(formatNumber(record.getTotalNewAmount()));
            itemOldPrice.setText(formatNumber(record.getTotalOldAmount()));
            dateTV.setText(formatDateToVietnamese(record.getProcessDate()));
            totalItemTV.setText(String.valueOf(record.getItemsCheckout().size()) + " Khoá học");
            itemTotalPrice.setText(formatNumber(record.getTotalNewAmount()));
            Glide.with(userImage.getContext())
                    .load(record.getUser().getProfileImage())
                    .into(userImage);
            usernameTxt.setText(record.getUser().getFullName());

            itemOldPrice.setPaintFlags(itemOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            detailBtn.setOnClickListener(v -> {
                Toast.makeText(mContext, "We're working on it!", Toast.LENGTH_SHORT).show();
            });
        }

        private String formatNumber(int number) {
            return NumberFormat.getNumberInstance(Locale.GERMAN).format(number) + " VNĐ";
        }

        private String formatDateToVietnamese(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi", "VN"));
            String formattedDate = sdf.format(date);
            String[] parts = formattedDate.split("/");
            String month = parts[1];
            String vietnameseDate =parts[0] + " T." + month + " " + parts[2];
            return vietnameseDate;
        }
    }
}
