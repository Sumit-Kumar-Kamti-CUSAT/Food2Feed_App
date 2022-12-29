package com.rsn.food2feedo.ui.home;



import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rsn.food2feedo.R;


public class Donate extends Fragment {

int SELECT_IMAGE_CODE=1;
    ImageView img;
    Button btn,save;
    Uri imgUri;
    FirebaseDatabase fdb;
    DatabaseReference dref;
    StorageReference stf;
    FirebaseStorage fs;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_donate, container, false);
        btn=v.findViewById(R.id.choose);
        img=v.findViewById(R.id.img);

        fdb=FirebaseDatabase.getInstance();
        dref=fdb.getReference().child("donor");
        fs=FirebaseStorage.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        save=v.findViewById(R.id.save);
        RadioGroup ftype=v.findViewById(R.id.type);
        EditText ptime=v.findViewById(R.id.ptime);
        EditText fname=v.findViewById(R.id.namef);
        RadioGroup ltype=v.findViewById(R.id.ltype);
        EditText address=v.findViewById(R.id.address);
        EditText qty=v.findViewById(R.id.qty);
        EditText mobile=v.findViewById(R.id.phone);
        EditText desc=v.findViewById(R.id.desc);
        pd=new ProgressDialog(getContext());
        pd.setCancelable(false);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId=ftype.getCheckedRadioButtonId();
                RadioButton rdftype =(RadioButton)v.findViewById(selectedId);
                String type=rdftype.getText().toString();

                int selectedId1=ftype.getCheckedRadioButtonId();
                RadioButton rdltype =(RadioButton)v.findViewById(selectedId1);
                String ltype=rdltype.getText().toString();

                String food=fname.getText().toString();
                String pt=ptime.getText().toString();
                String ads=address.getText().toString();
                String qt=qty.getText().toString();
                String ph=mobile.getText().toString();
                String des=desc.getText().toString();

                if(!food.isEmpty()&&!pt.isEmpty()&&!ads.isEmpty()&&!qt.isEmpty()&&!ph.isEmpty()&&!des.isEmpty()&&!type.isEmpty()&&!ltype.isEmpty()&&imgUri!=null){
                    {
                        pd.setTitle("Loading...");
                        pd.show();
                        StorageReference filepath=fs.getReference().child("imagePost").child(imgUri.getLastPathSegment());
                        filepath.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> downloadUrl=taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        String t=task.getResult().toString();
                                        DatabaseReference newPost=dref.push();
                                        newPost.child("FoodType").setValue(type);
                                        newPost.child("FoodName").setValue(food);
                                        newPost.child("PrepareTime").setValue(pt);
                                        newPost.child("LogisticType").setValue(ltype);
                                        newPost.child("Address").setValue(ads);
                                        newPost.child("Quantity").setValue(qt);
                                        newPost.child("MobileNO").setValue(ph);
                                        newPost.child("Description").setValue(des);
                                        newPost.child("image").setValue(task.getResult().toString());
                                        pd.dismiss();
                                        clear();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }

            }

            private void clear() {
                ftype.clearCheck();
                ltype.clearCheck();
                fname.setText("");
                ptime.setText("");
                address.setText("");
                qty.setText("");
                mobile.setText("");
                desc.setText("");
                img.setImageURI(null);
            }
        });
        return v;
    }


    private void imageChooser() {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Image"),SELECT_IMAGE_CODE );
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1)
        {
            imgUri= data.getData();
            img.setImageURI(imgUri);
            btn.setText("Done");
        }
    }

}