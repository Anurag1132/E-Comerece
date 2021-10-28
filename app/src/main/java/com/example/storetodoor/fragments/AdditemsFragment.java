package com.example.storetodoor.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.storetodoor.Adminactivity;
import com.example.storetodoor.Pojo;
import com.example.storetodoor.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

public class AdditemsFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    EditText price, itemname, description, itemcategory;
    Button cancel, additem;
    StorageReference stref;
    StorageReference storageReference;
    FirebaseStorage storage;
    String fuser, nameofgrocery, category, price1, stdescription, imagepath,email;

    Pojo pojoObj;


    private ImageView Itempicture;
    public Uri imageUri;

    public AdditemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_additems, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        price = view.findViewById(R.id.itemprice);
        Itempicture = view.findViewById(R.id.imageItem);
        itemname = view.findViewById(R.id.itemname);
        description = view.findViewById(R.id.itemdescription);
        itemcategory = view.findViewById(R.id.itemcategory);
        additem = view.findViewById(R.id.Additem);
        cancel = view.findViewById(R.id.cancel2);
        mAuth = FirebaseAuth.getInstance();

        fuser = mAuth.getCurrentUser().getEmail();
        pojoObj = new Pojo();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Adminactivity.class);
                startActivity(i);
            }
        });


        //================================================
        storage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        //=================================================

        stref = FirebaseStorage.getInstance().getReference("carrot/");

        try
        {
            File localfile = File.createTempFile("tempfile",".jpg");
            stref.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            Itempicture.setImageBitmap(bitmap);

                        }
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Failed to retrive image", Toast.LENGTH_SHORT).show();
        }


        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameofgrocery = itemname.getText().toString();
                category = itemcategory.getText().toString();
                price1 = price.getText().toString();
                stdescription = description.getText().toString();
                imagepath = pojoObj.getImageUrl();
                email="";



                if (TextUtils.isEmpty(nameofgrocery)) {
                    itemname.setError("Item Name cannot be empty");
                    return;
                } else if (TextUtils.isEmpty(category)) {
                    itemcategory.setError("Category is required");
                    return;
                } else if (TextUtils.isEmpty(price1)) {
                    price.setError("Category is required");
                    return;
                } else if (TextUtils.isEmpty(stdescription)) {
                    description.setError("Category is required");
                    return;
                }


                insertUserData(nameofgrocery,category,price1,stdescription,imagepath,email);

                Log.d("url:", imagepath );

                Toast.makeText(getActivity(), "Item added.", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getContext(), Adminactivity.class);
                startActivity(i);
            }
        });

        Itempicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

    }
    public void insertUserData( String nameofgrocery, String category,String price,String Description,String imageUrl,String email) {

        Map<String, Object> ItemDetails = new HashMap<>();

        ItemDetails.put("Description", Description);
        ItemDetails.put("nameofgrocery", nameofgrocery);
        ItemDetails.put("category", category);
        ItemDetails.put("price",price);
        ItemDetails.put("imageUrl",imageUrl);


        db.collection("Items").document().set(ItemDetails)
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
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {
            imageUri = data.getData();
            Itempicture.setImageURI(imageUri);

            uploadPicture(data.getData());

            pojoObj.setImageUrl(imageUri.toString());
        }
    }

    private void uploadPicture(Uri data) {

        final ProgressDialog pd = new ProgressDialog(getActivity());
        pd.setTitle("Uploading Image....");
        pd.show();
        double rdkey = Math.random();
        String randomKey = String.valueOf(rdkey);
        StorageReference imagesRef = storageReference.child("carrot/"+randomKey);

        imagesRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri uri = uriTask.getResult();

                pd.dismiss();

//                db.collection("Items").document().set(model).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        pd.dismiss();
//                        Toast.makeText(getActivity(), "Image Upload", Toast.LENGTH_LONG).show();
//
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        pd.dismiss();
//                        Toast.makeText(getActivity().getApplicationContext(), "failed to upload", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                pd.setMessage("Percentage:" + (int) progressPercent + "%");

            }
        });


    }
}
