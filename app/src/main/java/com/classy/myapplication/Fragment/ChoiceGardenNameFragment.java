package com.classy.myapplication.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.classy.myapplication.R;


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
        int USER = getArguments().getInt("USER",-1);
        findView(view);

        initSpinner(view,USER);

        return view;
    }

    private void initSpinner(View view,int user) {
        String[] names = {"NONE","lily"};
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
                    Bundle bundle = new Bundle();
                    bundle.putInt("USER", user);
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_choiceGardenNameFragment_to_uploadedIDPhotoActivity,bundle);
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

