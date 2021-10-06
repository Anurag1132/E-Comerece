package com.example.storetodoor.fragments;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.storetodoor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class signUp extends Fragment {

    EditText et_firstname, et_lastname, et_email, et_mobile, et_password, et_cpassword, et_dob, et_city;
    DatePickerDialog.OnDateSetListener setListener;
    Button btn_login, btn_signup;
    FirebaseAuth firebaseAuth;
    String fuser;
    FirebaseFirestore db;

    NavController navController;

    String fname, dateofbirth, city, lname, eml, mobilenumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_firstname =view.findViewById(R.id.firstName);
        et_lastname =view.findViewById(R.id.lastName);
        et_email =view.findViewById(R.id.edt_email);
        et_mobile =view.findViewById(R.id.mobile);
        et_password =view.findViewById(R.id.edt_password);
        et_cpassword =view.findViewById(R.id.Cpassword);
        et_city =view.findViewById(R.id.City);
        et_dob =view.findViewById(R.id.dob);
        btn_login =view.findViewById(R.id.btn_login_txt);
        btn_signup =view.findViewById(R.id.signup);
        firebaseAuth = FirebaseAuth.getInstance();

        navController = Navigation.findNavController(view);

        Calendar calendar= Calendar.getInstance();
        final int year= calendar.get(Calendar.YEAR);
        final int month= calendar.get(Calendar.MONTH);
        final int day= calendar.get(Calendar.DAY_OF_MONTH);



        setListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date= day+"/"+month+"/"+year;
                et_dob.setText(date);

            }
        };

        et_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        et_dob.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });





        btn_signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String firstName = et_firstname.getText().toString().trim();
                String lastName = et_lastname.getText().toString().trim();
                String Email= et_email.getText().toString().trim();
                String Password= et_password.getText().toString().trim();
                String conpassword= et_cpassword.getText().toString().trim();
                String Mobile= et_mobile.getText().toString().trim();
                String C= et_city.getText().toString().trim();
                String date= et_dob.getText().toString().trim();

                if(TextUtils.isEmpty(firstName))
                {
                    et_firstname.setError("First name is required");
                    return;
                }
                if(TextUtils.isEmpty(lastName))
                {
                    et_lastname.setError("Last name is required");
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    et_email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    et_password.setError("password is required.");
                    return;
                }
                if(TextUtils.isEmpty(Mobile)){
                    et_mobile.setError("mobile number is required.");
                    return;
                }
                if(TextUtils.isEmpty(conpassword)){
                    et_cpassword.setError("confirm password is required.");
                    return;
                }
                if(TextUtils.isEmpty(C)){
                    et_city.setError("Please select a city.");
                    return;
                }
                if(TextUtils.isEmpty(date)){
                    et_dob.setError("Please enter the date of birth.");
                    return;
                }

                if(et_password.length()<6){
                    et_password.setError("Password must be>=6 Characters");
                    return;
                }
                if(!(et_cpassword.getText().toString().equals(et_password.getText().toString()))){
                    et_cpassword.setError("confirm password and password must be same");
                    return;
                }

                if(et_mobile.length()<10){
                    et_mobile.setError("Invalid number");
                    return;
                }
                //-----------------------------------------------------------------
                fname = et_firstname.getText().toString();
                lname = et_lastname.getText().toString();
                eml = et_email.getText().toString();
                mobilenumber = et_mobile.getText().toString();
                dateofbirth = et_dob.getText().toString();
                city = et_city.getText().toString();
//                    pswd = password.getText().toString();
                //-----------------------------------------------------------------




                firebaseAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity().getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show() ;

                            fuser = firebaseAuth.getCurrentUser().getUid();
                            /*Send verification link*/
                            FirebaseUser user= firebaseAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void avoid) {
                                    Toast.makeText(getActivity().getApplicationContext(),"Verification Email Has been Sent",Toast.LENGTH_SHORT);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure: Email not sent " +e.getMessage());
                                }
                            });

                            insertUserData(fname,lname,mobilenumber, dateofbirth, city);
                            navController.navigate(R.id.userLogin);


                        }



                        else{
                            Toast.makeText(getActivity().getApplicationContext(),"registration error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }



        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.userLogin);
            }
        });

    }

    public void insertUserData(String firstname, String lastname, String mobileNumber,String d,String Ci) {

        Map<String, Object> UserDetails = new HashMap<>();

        UserDetails.put("firstName", firstname);
        UserDetails.put("lastName", lastname);
        UserDetails.put("mobilenumber", mobileNumber);
        UserDetails.put("dob",d);
        UserDetails.put("City",Ci);

        db.collection("ProfileUpdate").document(fuser).set(UserDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(getContext().getApplicationContext(),"Data inserted successfully",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}