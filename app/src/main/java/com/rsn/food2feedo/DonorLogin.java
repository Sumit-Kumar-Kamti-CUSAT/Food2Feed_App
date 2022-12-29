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

public class DonorLogin extends AppCompatActivity {
    DatabaseReference df= FirebaseDatabase.getInstance().getReferenceFromUrl("https://food2feed-9e578-default-rtdb.firebaseio.com/");
    Button btn_login;
    EditText userTxt,userPassTxt;
    TextView forget,newUser;
    ImageView inst,fb,ttr;
    String user,pass;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean valid=true;
    Dialog dialog;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        btn_login=findViewById(R.id.btn_donor_login);
        forget=findViewById(R.id.forget_donor);
        forget.setClickable(true);
        newUser=findViewById(R.id.new_user_donor);

        userTxt=findViewById(R.id.donor_username);
        userPassTxt=findViewById(R.id.donor_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(userTxt);
                checkField(userPassTxt);
                if(valid){
                    fAuth.signInWithEmailAndPassword(userTxt.getText().toString(),userPassTxt.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(DonorLogin.this,"Welcome",Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DonorLogin.this,"Access Denied username or password incorect",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DonorLogin.this,DonorRegistration.class);
                startActivity(i);
            }
        });
        pd=new ProgressDialog(DonorLogin.this);
        pd.setCancelable(false);
        pd.setMessage("Loading...");
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog=new Dialog(DonorLogin.this);
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
        inst=findViewById(R.id.inst_icon);
        if(inst!=null)
            inst.setClickable(true);
        inst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.instagram.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        fb=findViewById(R.id.fb_icon);
        if(fb!=null)
            fb.setClickable(true);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://www.fb.com"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        ttr=findViewById(R.id.twitter_icon);
        if(ttr!=null)
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
                    Toast.makeText(DonorLogin.this, "Plaese Check your Email", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(DonorLogin.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DonorLogin.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(DonorLogin.this,DonorMainActivity.class));
                finish();
            }
        });
    }

    private void checkField(EditText userTxt) {
        if(userTxt.getText().toString().isEmpty()){
            userTxt.setError("Error! ");
            valid=false;
        }
        else{
            valid=true;
        }
    }
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),DonorMainActivity.class));
            finish();
        }
    }
}