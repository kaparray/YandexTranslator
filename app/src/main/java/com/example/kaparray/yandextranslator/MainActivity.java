package com.example.kaparray.yandextranslator;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {


    public static String KEY = "trnsl.1.1.20180409T052432Z.e3b97e09c6ffef81.59987244eeb719f770079df05e17ec6de711f049";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);


        final EditText trText = findViewById(R.id.tr_text);






        trText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    //do stuff

                    return true;
                }
                translate(trText.getText()+"", "en-ru");
                return false;
            }
        });

    }


    void translate(String text, String lang){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://translate.yandex.net/api/v1.5/tr.json/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiTranslator api = retrofit.create(ApiTranslator.class);
        Call<Translation> call = api.getTranslation(KEY, text, lang);
        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                Translation translation = response.body();
                TextView text = findViewById(R.id.good);
                text.setText(translation.getText().get(0));
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getStackTrace()+"",  Toast.LENGTH_LONG).show();
            }
        });
    }
}
