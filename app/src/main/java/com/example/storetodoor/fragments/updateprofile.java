package com.example.storetodoor.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.storetodoor.Pojo;
import com.example.storetodoor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class updateprofile extends Fragment {


    EditText et_firstname, et_lastname, edt_mobile, edt_city, edt_aboutme;
    Button btn_save;
    String efuser;
    NavController navController;
    StorageReference stref;
    StorageReference storageReference;

    private ImageView profilePic;
    public Uri imageUri;
    FirebaseStorage storage;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    DocumentReference documentReference;
    String fuser;
    FirebaseUser user;
    String userEmail;
    String firstname, lastname, mobilenumber, city, aboutme;


    public updateprofile() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_firstname = view.findViewById(R.id.firstName);
        et_lastname = view.findViewById(R.id.lastName);
        edt_mobile = view.findViewById(R.id.mobile);
        edt_city = view.findViewById(R.id.city);
        edt_aboutme = view.findViewById(R.id.edt_about_me);
        btn_save = view.findViewById(R.id.btn_save);
        profilePic = view.findViewById(R.id.profilePic);

        navController = Navigation.findNavController(getActivity(), R.id.user_dashboard_fragment);

        //================================================
        storage=FirebaseStorage.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();
        db=FirebaseFirestore.getInstance();
        //=================================================
        user = fAuth.getCurrentUser();
        userEmail = fAuth.getCurrentUser().getEmail();
        fuser = fAuth.getCurrentUser().getUid();

        //------------Getting and Setting image on the image view-------------//


        stref = FirebaseStorage.getInstance().getReference("images/"+userEmail);

        try
        {
            File localfile = File.createTempFile("tempfile",".jpg");
            stref.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            profilePic.setImageBitmap(bitmap);

                        }
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to retrive image", Toast.LENGTH_SHORT).show();
        }



        //----------------------------------------------------------------------------------------------------//


        documentReference = db.collection("ProfileUpdate").document(user.getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    String firstnameResult = task.getResult().getString("firstName");
                    String lastnameResult = task.getResult().getString("lastName");
                    String mobilenumber = task.getResult().getString("mobilenumber");
                    String city = task.getResult().getString("City");
                    String aboutme = task.getResult().getString("Aboutme");

                    et_firstname.setText(firstnameResult);
                    et_lastname.setText(lastnameResult);
                    edt_mobile.setText(mobilenumber);
                    edt_city.setText(city);
                    edt_aboutme.setText(aboutme);


                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No Profile !!!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstname = et_firstname.getText().toString();
                lastname = et_lastname.getText().toString();
                mobilenumber = edt_mobile.getText().toString();
                city = edt_city.getText().toString();
                aboutme = edt_aboutme.getText().toString();

                insertUserData(firstname, lastname, mobilenumber, city, aboutme,userEmail);

                navController.navigate(R.id.userDashboardFragment);


            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_updateprofile, container, false);
    }

    public void insertUserData(String firstname, String lastname, String mobileNumber, String Ci, String ab,String email) {

        Map<String, Object> UserDetails = new HashMap<>();

        UserDetails.put("firstName", firstname);
        UserDetails.put("lastName", lastname);
        UserDetails.put("mobilenumber", mobileNumber);
        UserDetails.put("City", Ci);
        UserDetails.put("Aboutme", ab);
        UserDetails.put("Email",email);

        db.collection("ProfileUpdate").document(fuser).set(UserDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext().getApplicationContext(), "Data inserted successfully", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Error adding data" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void choosePicture() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {

            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture(data.getData());
        }
    }

    private void uploadPicture(Uri data) {

        final ProgressDialog pd=new ProgressDialog(getActivity());
        user=FirebaseAuth.getInstance().getCurrentUser();
        pd.setTitle("Uploading Image....");
        pd.show();
        String randomKey= user.getEmail();
        StorageReference imagesRef = storageReference.child("images/"+randomKey);

        imagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();
                Pojo model = new Pojo( imageUri.toString());

                db.collection("Upload image").document(user.getEmail()).set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        pd.dismiss();
                        Toast.makeText(getActivity(), "Image Upload", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(),"failed to upload",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent=(100.00* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("Percentage:"+(int)progressPercent+"%");
            }
        });
    }
}