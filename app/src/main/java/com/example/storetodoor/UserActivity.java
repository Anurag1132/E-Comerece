package com.example.storetodoor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavController navController;
    NavigationView navigationView;
    FirebaseAuth firebaseAuth;
    StorageReference storageRef;

    View headview;
    ImageView headerImage;
    String ueml;
    TextView header_textview_email;
    /*  InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        ueml = firebaseAuth.getCurrentUser().getEmail();

        setupNavigation();
        /*imm.toggleSoftInput (InputMethodManager.SHOW_FORCED, InputMethodManager.RESULT_HIDDEN);*/

        header_textview_email.setText(ueml);

        storageRef = FirebaseStorage.getInstance().getReference("images/"+ueml);
        try
        {
            File localfile = File.createTempFile("tempfile",".jpg");
            storageRef.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            headerImage.setImageBitmap(bitmap);
                        }
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Toast.makeText(this, "Failed to retrive image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Cart_icon:

                Intent account = new Intent(UserActivity.this, AddToCart.class);
                startActivity(account);
                break;

            case R.id.logout_Action:

                firebaseAuth.signOut();
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setCheckable(true);

        drawerLayout.closeDrawers();

        int id = item.getItemId();

        if (id == R.id.home_drawer) {
            navController.navigate(R.id.userDashboardFragment);
        }
        else if(id == R.id.about)
        {
            navController.navigate(R.id.aboutus);
        }
        else if (id == R.id.updateAccountDetails)
        {
            navController.navigate(R.id.updateprofile);
        }
        else if(id == R.id.orderstatusUser)
        {
            navController.navigate(R.id.orderstatusUser);
        }
        else if(id==R.id.myOrder1)
        {
            navController.navigate(R.id.myOrder);
        }
        else if(id==R.id.chat_user){
            navController.navigate(R.id.chat_frag_user);
        }
        return true;
    }

    public void setupNavigation() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerLayout = findViewById(R.id.drawerUser);
        navigationView = findViewById(R.id.navigationView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        navController = Navigation.findNavController(this, R.id.user_dashboard_fragment);

        headview = navigationView.getHeaderView(0);
        headerImage = headview.findViewById(R.id.userHeadImage);
        header_textview_email = headview.findViewById(R.id.txt_header_email);

        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserActivity.this, "Clicked performed on Imageview", Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                navController.navigate(R.id.updateprofile);

            }
        });
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}