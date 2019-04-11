package com.mohammedabdullah3296.googlelogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    TextView name, email;
    ImageView imageView;
    Button logout;
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int Reo_Code = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.textView);
        email = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
        logout = findViewById(R.id.button);
        signInButton = findViewById(R.id.ggg);

        logout.setOnClickListener(this);
        signInButton.setOnClickListener(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient =  new GoogleApiClient.Builder(this).enableAutoManage(this , this).addApi(Auth.GOOGLE_SIGN_IN_API ,googleSignInOptions ).build();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ggg :
                login();
            case R.id.button:
            siginout();
                break;
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void login (){
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient) ;
        startActivityForResult(intent , Reo_Code);
    }

    private void  siginout(){

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallbacks<Status>() {
            @Override
            public void onSuccess(@NonNull Status status) {

            }

            @Override
            public void onFailure(@NonNull Status status) {

            }
        });

    }

    private void handleReaslt(GoogleSignInResult googleSignInResult){

        if(googleSignInResult.isSuccess()){
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();

            String name1 = account.getDisplayName() ;
            String email1 = account.getEmail() ;
            String imgUrl = String.valueOf(account.getPhotoUrl());
            name.setText(name1);
            email.setText(email1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Reo_Code){

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleReaslt(result);
        }
    }
}
