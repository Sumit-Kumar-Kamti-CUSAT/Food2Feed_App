package com.rsn.food2feedo.ui.share;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rsn.food2feedo.R;


public class ShareFragment extends Fragment {

Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_share, container, false);
        btn=v.findViewById(R.id.btnshare);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent=new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Download This APP\n\n https://play.google.com/store/apps/details?id ''=com.instagram.android''"+getActivity().getPackageName());
                startActivity(Intent.createChooser(sendIntent,"Choose One"));
            }
        });
        return v;
    }
}