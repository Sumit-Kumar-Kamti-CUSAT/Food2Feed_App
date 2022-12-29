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

public class DonorRegistration extends AppCompatActivity {
    DatabaseReference df= FirebaseDatabase.getInstance().getReferenceFromUrl("https://food2feed-9e578-default-rtdb.firebaseio.com/");
    Button btnAdminReg;
    EditText userTxt, pass1Txt, pass2Txt, MobileTxt, EmailTxt;
    String fullName, pass, mobile, email;
    TextView login;
    boolean valid=true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);
        btnAdminReg = findViewById(R.id.btn_donor_reg);

        userTxt = findViewById(R.id.user_name_donor);
        MobileTxt = findViewById(R.id.Mobile_no_donor);
        EmailTxt = findViewById(R.id.email_donor);
        pass1Txt = findViewById(R.id.pass1_donor);
        pass2Txt = findViewById(R.id.pass2_donor);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        login = findViewById(R.id.login_txt_donor);
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
                               Toast.makeText(DonorRegistration.this, "Account Created", Toast.LENGTH_SHORT).show();
                               DocumentReference df = fStore.collection("Donors").document(user.getUid());
                               Map<String, Object> userInfo = new HashMap<>();
                               userInfo.put("FullName", fullName);
                               userInfo.put("UserEmail", email);
                               userInfo.put("PhoneNumber", mobile);
                               //Access user is Admin
                               userInfo.put("isUser", "0");
                               df.set(userInfo);

                               startActivity(new Intent(DonorRegistration.this, AdminLogin.class));
                               userTxt.setText("");
                               pass1Txt.setText("");
                               pass2Txt.setText("");
                               MobileTxt.setText("");
                               EmailTxt.setText("");
                               finish();//after back they will not go to registration.
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(DonorRegistration.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                           }
                       });
                   } else {
                       Toast.makeText(DonorRegistration.this, "Not Failed Info ", Toast.LENGTH_SHORT).show();
                   }


               }
           });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), DonorLogin.class);
                startActivity(i);
            }
        });
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

    private void chackPass(EditText pass1Txt, EditText pass2Txt) {
        if (!pass1Txt.getText().toString().equals(pass2Txt.getText().toString())) {
            Toast.makeText(DonorRegistration.this, "Enter Same Password alteast 6 digit.", Toast.LENGTH_SHORT).show();
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

    private void checkField(EditText pass1Txt) {
        if(userTxt.getText().toString().isEmpty()){
            userTxt.setError("Error should not be Empty");
            valid=false;
        }
        else
            valid=true;
    }
}