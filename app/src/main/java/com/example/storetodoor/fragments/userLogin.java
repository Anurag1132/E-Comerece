package com.example.storetodoor.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storetodoor.R;
import com.example.storetodoor.UserActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class userLogin extends Fragment {

    EditText et_email, et_password;
    Button btn_login,btn1,btn2;
    FirebaseAuth fAuth;
    TextView btnVar_signUp,txt_forgotpassword;
    NavController navController;
    FirebaseUser currentUser;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_user_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_email = view.findViewById(R.id.email_txt);
        et_password = view.findViewById(R.id.password_txt);
        btn_login = view.findViewById(R.id.btn_login_user);
        fAuth = FirebaseAuth.getInstance();
        btnVar_signUp = view.findViewById(R.id.signup_txt_user);
        btn1 = view.findViewById(R.id.button);
        btn2 = view.findViewById(R.id.admin_btn);
        navController = Navigation.findNavController(view);
        txt_forgotpassword = view.findViewById(R.id.forgotpass_txt_user);


        btnVar_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.signUp);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.admin_signIn);
            }
        });

      /*  currentUser = fAuth.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(getActivity().getApplicationContext(), "User Already Signing", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), UserActivity.class);
            startActivity(intent);
          //  updateUI(currentUser);

        }*/

        txt_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetmail= new EditText(view.getContext());
                AlertDialog.Builder forgotpassword= new AlertDialog.Builder(view.getContext());
                forgotpassword.setTitle("Reset Password?");
                forgotpassword.setMessage("Enter Your Email To Receive Reset Link.");
                forgotpassword.setView(resetmail);

                forgotpassword.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String mail = resetmail.getText().toString();

                        if (TextUtils.isEmpty(resetmail.getText().toString())) {
                            resetmail.setError("Email cannot be empty!");
                            resetmail.requestFocus();
                            Toast.makeText(getContext(), "Please enter your email !", Toast.LENGTH_SHORT).show();

                        } else {
                            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getActivity(), "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Error: Reset Link is Not Sent." + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                forgotpassword.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                forgotpassword.create().show();
            }

        });

        btn_login.setOnClickListener(view2 ->{
            if (!checkEmptyFields())
            {

                fAuth.signInWithEmailAndPassword(et_email.getText().toString(),et_password.getText().toString())

                        .addOnCompleteListener(getActivity(), task -> {

                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity().getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(getActivity().getApplicationContext(), UserActivity.class);
                               startActivity(intent);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "Authenticate Failed!" + task.getException().getMessage()   , Toast.LENGTH_SHORT).show();
                            }

                        });
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("LoginFragment", "onStart Called!");


    }


    public boolean checkEmptyFields()
    {
        if(TextUtils.isEmpty(et_email.getText().toString()))
        {
            et_email.setError("Email cannot be empty!");
            et_email.requestFocus();
            return true;
        }else if (TextUtils.isEmpty(et_password.getText().toString()))
        {
            et_password.setError("Password cannot be empty!");
            et_password.requestFocus();
            return true;
        }
        return false;
    }

}