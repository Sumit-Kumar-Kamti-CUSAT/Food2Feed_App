package com.rsn.food2feedo.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.rsn.food2feedo.R;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {
    ListView lv;
    FirebaseDatabase fdb;
    FirebaseStorage fs;
    DatabaseReference dref;
    RecyclerView rv;
    FoodAdapter fd;
    List<FoodModel> flist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_history, container, false);
        fdb=FirebaseDatabase.getInstance();
        dref=fdb.getReference().child("donor");
        fs=FirebaseStorage.getInstance();

        rv=view.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        flist=new ArrayList<>();
        fd=new FoodAdapter(getContext(),flist);
        rv.setAdapter(fd);

        dref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                int i=0;

                for(DataSnapshot sd:snapshot.getChildren()) {
                        FoodModel fmd = snapshot.getValue(FoodModel.class);
                        flist.add(fmd);
                        fd.notifyDataSetChanged();
                        Log.d("eror",""+previousChildName);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}