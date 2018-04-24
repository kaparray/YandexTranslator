package com.example.kaparray.yandextranslator.fragments;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kaparray.yandextranslator.MainActivity;
import com.example.kaparray.yandextranslator.R;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SetLangFragment extends Fragment {


    ListView list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fr_set_lang, container, false);

        list = rootView.findViewById(R.id.listLang);

        final List<String> listLang = Arrays.asList(getResources().getStringArray(R.array.lang));

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.container_tv, getResources().getStringArray(R.array.lang));

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle bundleLang = getArguments();
                String LANG = bundleLang.getString("WHAT");


                if(LANG.equals("1")) {
                    SharedPreferences preferences = getActivity().getSharedPreferences("lang1", MODE_PRIVATE);
                    SharedPreferences.Editor editorView = preferences.edit();
                    editorView.putString("LANG1", listLang.get(position));
                    editorView.apply();
                } else if(LANG.equals("2")){
                    SharedPreferences preferences = getActivity().getSharedPreferences("lang2", MODE_PRIVATE);
                    SharedPreferences.Editor editorView = preferences.edit();
                    editorView.putString("LANG2", listLang.get(position));
                    editorView.apply();
                    Log.d("0000", "");
                }
                MainFragment mainFragment = new MainFragment();


                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, mainFragment)
                        .commit();


            }
        });

        return rootView;
    }


}
