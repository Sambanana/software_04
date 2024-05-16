package com.example.mr_dankwah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, choosePasswordEditText, confirmPasswordEditText;

    private TextView errorMessageTextView;

    private ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        choosePasswordEditText = findViewById(R.id.choose_password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);

        errorMessageTextView = findViewById(R.id.error_message);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);


    }

    public void create_account(View view) {

        //getting the various values from the edittext and saving them in variables
        String username_text = usernameEditText.getText().toString().trim();
        String email_text = emailEditText.getText().toString().trim();
        String password_text = choosePasswordEditText.getText().toString().trim();
        String confirm_password_text = confirmPasswordEditText.getText().toString().trim();


        if (username_text.isEmpty() || email_text.isEmpty() || password_text.isEmpty() || confirm_password_text.isEmpty()) {

            errorMessageTextView.setText("All fields are required!");
            errorMessageTextView.setTextColor(getResources().getColor(R.color.danger));

            return;
        }

        Call<ApiResponse> call = apiInterface.registerUser(username_text, email_text, password_text);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();

                    if (apiResponse != null) {
                        if (apiResponse.getStatus().equals("success")) {
                            errorMessageTextView.setText("Registration Successful");
                            errorMessageTextView.setTextColor(getResources().getColor(R.color.success));

                            Intent intent = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intent);

                        } else {
                            // Check if the message indicates username or email already taken
                            String message = apiResponse.getMessage();
                            if (message != null && (message.contains("username") || message.contains("email"))) {
                                errorMessageTextView.setText("Username or Email already taken");
                            } else {
                                errorMessageTextView.setText("Registration failed");
                            }
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

    public void goToSignIn(View view) {
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
}