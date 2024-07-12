package com.example.unicourse.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.models.common.CommonResponse;
import com.example.unicourse.models.user.Cart;
import com.example.unicourse.models.user.DeleteCartResponse;
import com.example.unicourse.services.UserApiService;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private final Context mContext;
    private final ArrayList<Cart.Item> carts;
    private final String BASE_URL = ApiConstants.BASE_URL;
    private boolean inEditMode = false;
    private String cartId;

    public CartAdapter(Context mContext, ArrayList<Cart.Item> carts, boolean inEditMode, String cartId) {
        this.mContext = mContext;
        if (carts != null) {
            this.carts = carts;
        } else {
            this.carts = new ArrayList<>();
        }
        this.cartId = cartId;
        this.inEditMode = inEditMode;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_cart_course_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart.Item cart = carts.get(position);
        holder.bind(cart);
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView cartItemImage;
        private TextView cartItemTitle;
        private TextView cartItemFinalPrice;
        private TextView cartItemOldPrice;
        private CheckBox cartCheckBox;
        private ImageView cartCheckBoxStatus;
        private ImageButton deleteItemBtn;
        private LinearLayout detailContainer;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemImage = itemView.findViewById(R.id.cartItemImage);
            cartItemTitle = itemView.findViewById(R.id.cartItemTitle);
            cartItemFinalPrice = itemView.findViewById(R.id.cartItemFinalPrice);
            cartItemOldPrice = itemView.findViewById(R.id.cartItemOldPrice);
            cartCheckBox = itemView.findViewById(R.id.cartCheckBox);
            cartCheckBoxStatus = itemView.findViewById(R.id.cartCheckBoxStatus);
            deleteItemBtn = itemView.findViewById(R.id.deleteItemButton);
            detailContainer = itemView.findViewById(R.id.detailContainer);
        }

        public void bind(Cart.Item cart) {
            deleteItemBtn.setTag(cart.getId());
            Glide.with(cartItemImage.getContext())
                    .load(cart.getThumbnail())
                    .into(cartItemImage);
            cartItemTitle.setText(cart.getTitle());
            cartItemFinalPrice.setText(formatNumber(cart.getAmount()));
            cartItemOldPrice.setText(formatNumber(cart.getAmount()));
            cartCheckBox.setChecked(cart.isSelected());

//            Init basic value
            cartItemOldPrice.setPaintFlags(cartItemOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (inEditMode) {
                deleteItemBtn.setVisibility(View.VISIBLE);
                cartCheckBox.setVisibility(View.GONE);
            } else {
                deleteItemBtn.setVisibility(View.GONE);
            }
            if (!cart.isSelected()) {
                cartCheckBoxStatus.setVisibility(View.GONE);
            }

             cartCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    cart.setSelected(isChecked);
                    cartCheckBoxStatus.setVisibility(View.VISIBLE);
                }
            });

        }

        private String formatNumber(int number) {
            return NumberFormat.getNumberInstance(Locale.GERMAN).format(number) + " VNĐ";
        }

        private int dpToPx(Context context, int dp) {
            float density = context.getResources().getDisplayMetrics().density;
            return Math.round((float) dp * density);
        }
    }
}
