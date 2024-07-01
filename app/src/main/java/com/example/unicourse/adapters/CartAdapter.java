package com.example.unicourse.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.example.unicourse.ui.activities.CartActivity;

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
    private boolean inEditMode = false;
    private String cartId;
    private final String BASE_URL = ApiConstants.BASE_URL;

    public CartAdapter(Context mContext, ArrayList<Cart.Item> carts, boolean inEditMode, String cartId) {
        this.mContext = mContext;
        this.carts = carts;
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
            Glide.with(cartItemImage.getContext())
                    .load(cart.getThumbnail())
                    .into(cartItemImage);
            cartItemTitle.setText(cart.getTitle());
            cartItemFinalPrice.setText(formatNumber(cart.getAmount()));
            cartItemOldPrice.setText(formatNumber(cart.getAmount()));
            cartCheckBox.setChecked(cart.isSelected());

//            Init basic value
            cartItemOldPrice.setPaintFlags(cartItemOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            deleteItemBtn.setOnClickListener(v -> {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                String accessToken = sharedPreferences.getString("access_token", null);

                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Request originalRequest = chain.request();
                                Request.Builder builder = originalRequest.newBuilder()
                                        .header("Authorization", "Bearer " + accessToken);
                                Request newRequest = builder.build();
                                return chain.proceed(newRequest);
                            }
                        })
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                UserApiService userApiService = retrofit.create(UserApiService.class);
                Call<CommonResponse<DeleteCartResponse>> call = userApiService.deleteCourseFromCart(cartId, cart.getId());

                call.enqueue(new Callback<CommonResponse<DeleteCartResponse>>() {
                    @Override
                    public void onResponse(Call<CommonResponse<DeleteCartResponse>> call, Response<CommonResponse<DeleteCartResponse>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            CommonResponse<DeleteCartResponse> deleteCartResponse = response.body();
                            if (String.valueOf(deleteCartResponse.getStatus()).equals("200")) {
//                                carts.remove(cart);
                                ((Activity) mContext).recreate();
//                                CartAdapter newAdapter = new CartAdapter(mContext, carts, inEditMode, cartId);
//                                ((RecyclerView) itemView.getParent()).setAdapter(newAdapter);
//                                notifyItemRemoved(getAdapterPosition());
//                                notifyItemRangeChanged(getAdapterPosition(), carts.size());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse<DeleteCartResponse>> call, Throwable throwable) {
                        Toast.makeText(mContext, "Can not perform action: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            });

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
            return Math.round((float)dp * density);
        }
    }
}
