package com.example.mr_dankwah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {

    private ApiInterface apiInterface;

    private EditText usernameEditText, passwordEditText;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        errorMessageTextView = findViewById(R.id.error_message);
    }

    public void logIn(View view) {

        //getting and verifying the various values from out fields
        String username_text = usernameEditText.getText().toString().trim();
        String password_text = passwordEditText.getText().toString().trim();


        if (username_text.isEmpty() ) {
            errorMessageTextView.setText("username is required to login!");
            errorMessageTextView.setTextColor(getResources().getColor(R.color.danger));
            return;
        }

        if (password_text.isEmpty() ) {
            errorMessageTextView.setText("password is required to login!");
            errorMessageTextView.setTextColor(getResources().getColor(R.color.danger));
            return;
        }

        Call<ApiResponse> call = apiInterface.loginUser(username_text, password_text);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();

                    if (apiResponse != null) {
                        if (apiResponse.getStatus().equals("success")) {
                            errorMessageTextView.setText("Login Successful");
                            errorMessageTextView.setTextColor(getResources().getColor(R.color.success));

                            Intent intent = new Intent(getApplicationContext(), Home.class);
                            intent.putExtra("name", username_text);
                            startActivity(intent);

                        } else if (apiResponse.getMessage().equals("username does not exist")) {
                            errorMessageTextView.setText("Username does not exist");
                            errorMessageTextView.setTextColor(getResources().getColor(R.color.danger));
                        } else {
                            errorMessageTextView.setText("Wrong password");
                            errorMessageTextView.setTextColor(getResources().getColor(R.color.danger));
                        }
                    } else {
                        errorMessageTextView.setText("Invalid response from server");
                        errorMessageTextView.setTextColor(getResources().getColor(R.color.danger));
                    }
                } else {
                    errorMessageTextView.setText("Server Error");
                    errorMessageTextView.setTextColor(getResources().getColor(R.color.danger));
                }
            }


            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                errorMessageTextView.setText("Network Error. Please check your internet connection!");
                errorMessageTextView.setTextColor(getResources().getColor(R.color.danger));
            }
        });

    }

    public void goToSignUp(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}