package dev18.noobs.com.noobsdev18;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev18.noobs.com.noobsdev18.databinding.ActivityLoginBinding;
import dev18.noobs.com.noobsdev18.model.UserModel;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
WelcomeFragment.OnFragmentInteractionListener{

    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    DatabaseReference mRef;

    final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("767294955581-pq4s8k53nftnl55ruuqutdqsccasr59d.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        setupListeners();
    }

    private void setupListeners() {
        binding.login.setOnClickListener(this);
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                Log.w("TAG", "Google sign in failed", e);

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {

                            Snackbar.make(findViewById(android.R.id.content), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }


                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {

            mRef = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid());

            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    if (dataSnapshot.exists()) {

                        UserModel model = dataSnapshot.getValue(UserModel.class);

                        if (model.getScreenName() == null) {

                            mRef.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    binding.login.setVisibility(View.GONE);

                                    getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.container, new WelcomeFragment())
                                            .commit();
                                }
                            });

                        } else {

                            //go to GO nets
                        }

                    } else {

                        UserModel model = new UserModel(FirebaseAuth.getInstance().getUid(), null, null, null);

                        mRef.setValue(model);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, new WelcomeFragment())
                                .commit();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }


    @Override
    public void onClick(View view) {
        if (view == binding.login) {
            signIn();
        }
    }

    @Override
    public void onWelcomeFragmentInteraction(int i) {

    }
}
