package com.example.unicourse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.unicourse.R;
import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.models.authentication.LoginRequest;
import com.example.unicourse.models.authentication.RegisterResponse;
import com.example.unicourse.models.authentication.User;
import com.example.unicourse.models.common.CommonResponse;
import com.example.unicourse.services.AuthApiService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    private final String BASE_URL = ApiConstants.BASE_URL;
    private EditText usernameTxt = null;
    private EditText passwordTxt = null;
    private EditText confirmPasswordTxt = null;
    private Button registerBtn = null;
    private Button loginBtn = null;
    private ImageView passwordToggle;
    private ImageView confirmPasswordToggle;
    private CheckBox rememberMeCheckbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        usernameTxt = findViewById(R.id.username);
        passwordTxt = findViewById(R.id.password);
        confirmPasswordTxt = findViewById(R.id.confirmPassword);
        registerBtn = findViewById(R.id.registerButton);
        loginBtn = findViewById(R.id.loginButton);
        passwordToggle = findViewById(R.id.password_toggle);
        confirmPasswordToggle = findViewById(R.id.confirm_password_toggle);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);

        registerBtn.setOnClickListener(v -> {
            String username = usernameTxt.getText().toString();
            String password = passwordTxt.getText().toString();
            String confirmPassword = confirmPasswordTxt.getText().toString();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please input all required fields!", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            } else {
                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(emailRegex);
                Matcher matcher = pattern.matcher(username);

                String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
                Pattern passwordPattern = Pattern.compile(passwordRegex);
                Matcher passwordMatcher = passwordPattern.matcher(password);

                if (!passwordMatcher.matches()) {
                    Toast.makeText(this, "Password must contain at least 8 characters, one uppercase, one lowercase, one number and one special character!", Toast.LENGTH_SHORT).show();
                } else if (!matcher.matches()) {
                    Toast.makeText(this, "Invalid email address!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean rememberMe = rememberMeCheckbox.isChecked();
                    registerUser(username, password, rememberMe);
                }
            }
        });

        loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        passwordToggle.setOnClickListener(v -> {
            toggleVisibility(passwordTxt, passwordToggle);
        });

        confirmPasswordToggle.setOnClickListener(v -> {
            toggleVisibility(confirmPasswordTxt, confirmPasswordToggle);
        });
    }

    private void toggleVisibility(EditText textInputter, ImageView toggleElement) {
        if (textInputter.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
            textInputter.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            toggleElement.setImageResource(R.drawable.ic_visibility_off);
        } else {
            textInputter.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            toggleElement.setImageResource(R.drawable.ic_visibility);
        }
        textInputter.setSelection(textInputter.length());
    }

    private void registerUser(String email, String password, boolean rememberMe) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AuthApiService authService = retrofit.create(AuthApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, password);
        Call<CommonResponse<RegisterResponse>> call = authService.registerUser(loginRequest);

        call.enqueue(new Callback<CommonResponse<RegisterResponse>>() {

            @Override
            public void onResponse(Call<CommonResponse<RegisterResponse>> call, Response<CommonResponse<RegisterResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CommonResponse<RegisterResponse> registerResponse = response.body();
                    if (String.valueOf(registerResponse.getStatus()).startsWith("200")) {
                        String accessToken = registerResponse.getData().getAccessToken().replace("Bearer ", "");
                        User user = parseJwtToken(accessToken);
                        saveUserInfo(user, accessToken, rememberMe);
                        Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, ControllerActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    assert response.body() != null;
                    Toast.makeText(RegisterActivity.this, "Register failed: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CommonResponse<RegisterResponse>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Register failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private User parseJwtToken(String token) {
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(token);

        String userId = decodedJWT.getClaim("_id").asString();
        String email = decodedJWT.getClaim("email").asString();
        String fullName = decodedJWT.getClaim("fullName").asString();
        String profileName = decodedJWT.getClaim("fullName").asString(); // Assuming profileName is same as fullName
        String profileImage = decodedJWT.getClaim("profile_image").asString();
        String role = decodedJWT.getClaim("role").asString();

        return new User(userId, email, fullName, profileName, profileImage, role);
    }

    private void saveUserInfo(User user, String accessToken, boolean rememberMe) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("access_token", accessToken);
        editor.putString("user_id", user.get_id());
        editor.putString("user_email", user.getEmail());
        editor.putString("user_full_name", user.getFullName());
        editor.putString("profile_name", user.getProfileName());
        editor.putString("profile_image", user.getProfileImage());
        editor.putString("role", user.getRole());
        editor.putBoolean("remember_me", rememberMe);

        editor.apply();
    }
}