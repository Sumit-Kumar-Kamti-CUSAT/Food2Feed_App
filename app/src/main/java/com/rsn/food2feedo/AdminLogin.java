package com.rsn.food2feedo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminLogin extends AppCompatActivity {
    DatabaseReference df= FirebaseDatabase.getInstance().getReferenceFromUrl("https://food2feed-9e578-default-rtdb.firebaseio.com/");
    Button btn_login;
    EditText userTxt,userPassTxt;
    TextView forget,newUser;
    ImageView inst,fb,ttr;
    String user,pass;
    FirebaseAuth fAuth;
    boolean valid;
    FirebaseFirestore fStore;
    ProgressDialog pd;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        btn_login=findViewById(R.id.btn_admin_login);

        userTxt=findViewById(R.id.admin_username);
        userPassTxt=findViewById(R.id.admin_password);

        forget=findViewById(R.id.forget_admin);
        newUser=findViewById(R.id.new_user_admin);

        pd=new ProgressDialog(AdminLogin.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(userTxt);
                checkField(userPassTxt);
                if(valid){
                    fAuth.signInWithEmailAndPassword(userTxt.getText().toString(),userPassTxt.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(AdminLogin.this,"Welcome",Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminLogin.this,"Access Denied username or password incorect",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminLogin.this,AdminRegistration.class);
                startActivity(i);
            }
        });
        forget.setClickable(true);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(AdminLogin.this);
                dialog.setContentView(R.layout.forgetui);
                dialog.show();
                EditText email=dialog.findViewById(R.id.emailFrg);
                Button btnFrg=dialog.findViewById(R.id.frgBtn);
                btnFrg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        forgetPassword(email);
                    }
                });
            }
        });
        inst=findViewById(R.id.insta_icon);
        inst.setClickable(true);
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.instagram.com/food2feed123/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        fb=findViewById(R.id.fb_icon);
        fb.setClickable(true);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/profile.php?id=100089129340004&is_tour_completed=true"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        ttr=findViewById(R.id.twitter_icon);
        ttr.setClickable(true);
        ttr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.twitter.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void forgetPassword(EditText email) {
        if(!validateEmail(email)){
            return;
        }
        pd.show();
        fAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                if(task.isSuccessful()){
                    dialog.dismiss();
                    Toast.makeText(AdminLogin.this, "Plaese Check your Email", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AdminLogin.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminLogin.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
//
    private boolean validateEmail(EditText email) {
        String val=email.getText().toString();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            email.setError("Can't be empty");
            return false;
        }
        else if(!val.matches(emailPattern)){
            email.setError("invalid Email");
            return false;
        }
        else {
            email.setError(null);
            return true;
        }
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df=fStore.collection("Users").document(uid);//to extract or store data in firebase
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
               Log.d("Tag","onSuccess"+documentSnapshot.getData());
               startActivity(new Intent(AdminLogin.this,AdminMainActivity.class));
               finish();
            }
        });
    }

    private boolean checkField(EditText userTxt) {
        if(userTxt.getText().toString().isEmpty()){
            userTxt.setError("Error! ");
            valid=false;
        }
        else{
            valid=true;
        }
        return valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
            finish();
        }
    }
}