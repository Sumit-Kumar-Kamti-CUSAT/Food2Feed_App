package com.rsn.food2feedo.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.rsn.food2feedo.AboutUs;
import com.rsn.food2feedo.DonorDonate;
import com.rsn.food2feedo.Feedback;
import com.rsn.food2feedo.Promote1;
import com.rsn.food2feedo.R;
import com.rsn.food2feedo.Terms;
import com.rsn.food2feedo.ui.about.AboutFragment;
import com.rsn.food2feedo.ui.history.HistoryFragment;


public class HomeFragment extends Fragment {
    GridView gv;
    View view;
    String[] name={"Donation","History","FeedBack","About Us","Terms & condition","Promote Us"};
    int[] numImage={R.drawable.donate,R.drawable.ic_history,R.drawable.feedback,R.drawable.about,R.drawable.terms,R.drawable.promote};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_home, container, false);

        gv=view.findViewById(R.id.gridView);
        MainAdapter adapter=new MainAdapter(getContext(),name,numImage);
        gv.setAdapter(adapter);
        gv.setClickable(true);


        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        startActivity(new Intent(getContext(), DonorDonate.class));
                        break;
                    case 1:
                        loadFragment(new HistoryFragment(),true);
                        break;
                    case 2:
                        startActivity(new Intent(getContext(), Feedback.class));
                        break;
                    case 3:
                        startActivity(new Intent(getContext(), AboutUs.class));
                        break;
                    case 4:
                        startActivity(new Intent(getContext(), Terms.class));
                        break;
                    case 5:
                        startActivity(new Intent(getContext(), Promote1.class));
                        break;
                    default:
                        Toast.makeText(getContext(), "Error occured", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        return view;
    }
    private void loadFragment(Fragment fragment,boolean flag) {
        FragmentManager fm=getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(flag)
            ft.add(R.id.container,fragment);
        else
            ft.replace(R.id.container,fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}