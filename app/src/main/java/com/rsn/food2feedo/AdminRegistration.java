package com.rsn.food2feedo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminRegistration extends AppCompatActivity {
    DatabaseReference df=FirebaseDatabase.getInstance().getReferenceFromUrl("https://food2feed-9e578-default-rtdb.firebaseio.com/");
    Button btnAdminReg;
    EditText userTxt,pass1Txt,pass2Txt,MobileTxt,EmailTxt;
    String fullName,pass,mobile,email;
    TextView login;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    boolean valid=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);


        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        btnAdminReg=findViewById(R.id.btn_admin_reg);
        login=findViewById(R.id.login_txt_admin);

        userTxt=findViewById(R.id.user_name_admin);
        pass1Txt=findViewById(R.id.pass1_admin);
        MobileTxt=findViewById(R.id.Mobile_no_admin);
        EmailTxt=findViewById(R.id.email_admin);
        pass2Txt=findViewById(R.id.pass2_donor);





        btnAdminReg.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                checkField(userTxt);
                checkField(pass1Txt);
                checkField(pass2Txt);
                checkField(MobileTxt);
                checkField(EmailTxt);
                chackPass(pass1Txt, pass2Txt);

                pass = pass1Txt.getText().toString();
                fullName = userTxt.getText().toString();
                mobile = MobileTxt.getText().toString();
                email = EmailTxt.getText().toString();
                valid=validateEmail(EmailTxt);
                if (valid) {
                    fAuth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(AdminRegistration.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("Users").document(user.getUid());
                            Map<String, Object> userInfo = new HashMap<>();
                            userInfo.put("FullName", fullName);
                            userInfo.put("UserEmail", email);
                            userInfo.put("PhoneNumber", mobile);
                            //Access user is Admin
                            userInfo.put("isUser", "1");
                            df.set(userInfo);

                            startActivity(new Intent(AdminRegistration.this, AdminLogin.class));
                            finish();//after back they will not go to registration.
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminRegistration.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AdminRegistration.this, "Not Failed Info ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),AdminLogin.class);
                startActivity(i);
            }
        });



    }

    private void chackPass(EditText pass1Txt, EditText pass2Txt) {
        if (!pass1Txt.getText().toString().equals(pass2Txt.getText().toString())) {
            Toast.makeText(AdminRegistration.this, "Enter Same Password alteast 6 digit.", Toast.LENGTH_SHORT).show();
            valid=false;
        }
        else if(pass1Txt.getText().toString().length()<6){
            pass1Txt.setError("atleast 6 digit");
            valid=false;
        }
        else if(pass2Txt.getText().toString().length()<6){
            pass2Txt.setError("atleast 6 digit");
            valid=false;
        }

        else
            valid=true;
    }
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
    private void checkField(EditText userTxt) {
        if(userTxt.getText().toString().isEmpty()){
            userTxt.setError("Error should not be Empty");
            valid=false;
        }
        else
            valid=true;
    }
}