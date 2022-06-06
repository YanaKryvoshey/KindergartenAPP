package com.classy.myapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.classy.myapplication.R;

import java.util.ArrayList;


public class ChoiceGardenNameFragment extends Fragment {

    Spinner choiceGardenName_SPN_name;



    public ChoiceGardenNameFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice_garden_name, container, false);
        int USER = getArguments().getInt("USERNUM",-1);
        ArrayList <String> names = getArguments().getStringArrayList("NAMES");
        findView(view);
        initSpinner(view,USER,names);

        return view;
    }



    private void initSpinner(View view,int user,ArrayList <String> names) {
        //String[] names = {"NONE","lily"};

        names.add(0,"NONE");
        //Creating the ArrayAdapter instance having the Gardens names list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choiceGardenName_SPN_name.setAdapter(adapter);
        choiceGardenName_SPN_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {

                if (!((String) parentView.getItemAtPosition(position)).matches("NONE")) {
                    Toast.makeText(getActivity(), (String) parentView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                   // Intent i = new Intent(getActivity().getBaseContext(),
                    //        UploadedIDPhotoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("USERNUM",user);
                    bundle.putString("GARDENNAME",(String) parentView.getItemAtPosition(position));

                    if(user == 3){
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_choiceGardenNameFragment_to_newAccountActivity,bundle);
                    }else {
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(R.id.action_choiceGardenNameFragment_to_uploadedIDPhotoActivity,bundle);
                    }
                   /* i.putExtra("USERNUM",user);
                    i.putExtra("GARDENNAME",(String) parentView.getItemAtPosition(position));
                    getActivity().startActivity(i);*/
                   // NavController navController = Navigation.findNavController(view);
                    //navController.navigate(R.id.action_choiceGardenNameFragment_to_uploadedIDPhotoActivity,bundle);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

    }



    private void findView(View view) {
        choiceGardenName_SPN_name = view.findViewById(R.id.choiceGardenName_SPN_name);
    }
}

