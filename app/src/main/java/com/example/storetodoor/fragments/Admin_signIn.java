package com.example.storetodoor.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.storetodoor.Adminactivity;
import com.example.storetodoor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



public class Admin_signIn extends Fragment {

    Button user_btn, btn_adminLogIn;
    NavController navController;
    EditText admin_email, admin_password;

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String a, e, Pass;
    FirebaseUser user;
    boolean valid = true;


    public Admin_signIn() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_sign_in, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        user_btn = view.findViewById(R.id.user_btn);
        navController = Navigation.findNavController(view);
        btn_adminLogIn = view.findViewById(R.id.btn_admin_login);
        admin_email = view.findViewById(R.id.admin_email_txt);
        admin_password = view.findViewById(R.id.admin_password_txt);
        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        //--------------------------Firebase components-----------------//


        user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.userLogin);
            }
        });

        btn_adminLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(admin_email);
                checkField(admin_password);
                if (valid) {
                    firebaseAuth.signInWithEmailAndPassword(admin_email.getText().toString(), admin_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            checkUserAcessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {
                            Toast.makeText(getActivity(), "Authentication Failed! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }


        });
    }

    private void checkUserAcessLevel (String uid){
        DocumentReference df = db.collection("ProfileUpdate").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("Tag", "onsuccess" + documentSnapshot.getData());
                e=admin_email.getText().toString();
                if (documentSnapshot.getString("isAdmin")!=null) {
                    Intent i= new Intent(getActivity(), Adminactivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public boolean checkField (EditText textField){
        if (textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }

        return valid;
    }
}