package com.example.kaparray.yandextranslator.fragments;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaparray.yandextranslator.Api.ApiTranslator;
import com.example.kaparray.yandextranslator.Data.Translation;
import com.example.kaparray.yandextranslator.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment{


    public static String KEY = "trnsl.1.1.20180409T052432Z.e3b97e09c6ffef81.59987244eeb719f770079df05e17ec6de711f049";


    TextView textFor, textIn;
    ImageView img_reverse;
    Animation anim;
    String langTo, langIn , lang;


    View rootView;



    SetLangFragment setLangActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_main, container, false);

        setLangActivity = new SetLangFragment();

        textFor = rootView.findViewById(R.id.textFor);
        textIn = rootView.findViewById(R.id.textIn);
        img_reverse = rootView.findViewById(R.id.img_reverse);
        final EditText trText = rootView.findViewById(R.id.tr_text);


        SharedPreferences preferences = getActivity().getSharedPreferences("lang1",MODE_PRIVATE);
        langTo = preferences.getString("LANG1"," ");

        SharedPreferences preferences1 = getActivity().getSharedPreferences("lang2",MODE_PRIVATE);
        langIn = preferences1.getString("LANG2"," ");

        Log.d("0000", langIn + "   " + langTo);

        if(langIn.equals(" ")) {
            langIn = "русский";
        }


        if(langTo.equals(" ")){
            langTo = "английский";
        }

        textFor.setText(langTo);
        textIn.setText(langIn);



        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_animaton);

        img_reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText text = rootView.findViewById(R.id.good);
                img_reverse.startAnimation(anim);
                String lang;
                lang = langIn;
                langIn = langTo;
                langTo = lang;
                textFor.setText(langTo);
                textIn.setText(langIn);


                if(trText.getText() + "" != "") {
                    text.setText("...");
                    translate(trText.getText() + "", language(langTo) + "-" + language(langIn));
                }

            }
        });


        textFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle1 = new Bundle();
                bundle1.putString("WHAT", "1");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, setLangActivity)
                        .addToBackStack(null)
                        .commit();

                setLangActivity.setArguments(bundle1);

            }
        });

        textIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                bundle2.putString("WHAT", "2");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .replace(R.id.container, setLangActivity)
                        .addToBackStack(null)
                        .commit();
                setLangActivity.setArguments(bundle2);
            }
        });






        trText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if(actionId == EditorInfo.IME_ACTION_SEND){
                    //do stuff
                    EditText text = rootView.findViewById(R.id.good);
                    text.setText("...");


                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textFor.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    translate(trText.getText()+"", language(langTo) +"-" + language(langIn));

                    Log.d("000", language(langTo) +"-" + language(langIn));
                    return true;
                }

                return false;
            }
        });

        return rootView;
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
                EditText text = rootView.findViewById(R.id.good);
                text.setText(translation.getText().get(0));
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Toast.makeText(getContext(), "Ooops! Error",  Toast.LENGTH_LONG).show();
                EditText text = rootView.findViewById(R.id.good);
                text.setText("");
            }
        });
    }


    String language(String lang){
        if(lang.equals("азербайджанский")){
            return "az";
        }else if (lang.equals("албанский")){
            return "sq";
        }else if (lang.equals("амхарский")){
            return "am";
        }else if (lang.equals("английский")){
            return "en";
        }else if (lang.equals("арабский")){
            return "ar";
        }else if (lang.equals("армянский")){
            return "hy";
        }else if (lang.equals("африкаанс")){
            return "af";
        }else if (lang.equals("баскский")){
            return "eu";
        }else if (lang.equals("башкирский")){
            return "ba";
        }else if (lang.equals("белорусский")){
            return "be";
        }else if (lang.equals("бенгальский")){
            return "bn";
        }else if (lang.equals("бирманский")){
            return "my";
        }else if (lang.equals("болгарский")){
            return "bg";
        }else if (lang.equals("боснийский")){
            return "bs";
        }else if (lang.equals("валлийский")){
            return "cy";
        }else if (lang.equals("венгерский")){
            return "hu";
        }else if (lang.equals("вьетнамский")){
            return "vi";
        }else if (lang.equals("гаитянский (креольский)")){
            return "ht";
        }else if (lang.equals("галисийский")){
            return "gl";
        }else if (lang.equals("голландский")){
            return "nl";
        }else if (lang.equals("горномарийский")){
            return "mrj";
        }else if (lang.equals("греческий")){
            return "el";
        }else if (lang.equals("грузинский")){
            return "ka";
        }else if (lang.equals("гуджарати")){
            return "gu";
        }else if (lang.equals("датский")){
            return "da";
        }else if (lang.equals("иврит")){
            return "he";
        }else if (lang.equals("идиш")){
            return "yi";
        }else if (lang.equals("индонезийский")){
            return "id";
        }else if (lang.equals("ирландский")){
            return "ga";
        }else if (lang.equals("итальянский")){
            return "it";
        }else if (lang.equals("исландский")){
            return "is";
        }else if (lang.equals("испанский")){
            return "es";
        }else if (lang.equals("казахский")){
            return "kk";
        }else if (lang.equals("каннада")){
            return "kn";
        }else if (lang.equals("каталанский")){
            return "ca";
        }else if (lang.equals("киргизский")){
            return "ky";
        }else if (lang.equals("китайский")){
            return "zh";
        }else if (lang.equals("корейский")){
            return "ko";
        }else if (lang.equals("коса")){
            return "xh";
        }else if (lang.equals("кхмерский")){
            return "km";
        }else if (lang.equals("лаосский")){
            return "lo";
        }else if (lang.equals("латынь")){
            return "la";
        }else if (lang.equals("латышский")){
            return "lv";
        }else if (lang.equals("литовский")){
            return "lt";
        }else if (lang.equals("люксембургский")){
            return "lb";
        }else if (lang.equals("малагасийский")){
            return "mg";
        }else if (lang.equals("малайский")){
            return "ms";
        }else if (lang.equals("малаялам")){
            return "ml";
        }else if (lang.equals("мальтийский")){
            return "mt";
        }else if (lang.equals("македонский")){
            return "mk";
        }else if (lang.equals("маори")){
            return "mi";
        }else if (lang.equals("маратхи")){
            return "mr";
        }else if (lang.equals("марийский")){
            return "mhr";
        }else if (lang.equals("монгольский")){
            return "mn";
        }else if (lang.equals("немецкий")){
            return "de";
        }else if (lang.equals("непальский")){
            return "ne";
        }else if (lang.equals("норвежский")){
            return "no";
        }else if (lang.equals("панджаби")){
            return "pa";
        }else if (lang.equals("папьяменто")){
            return "pap";
        }else if (lang.equals("персидский")){
            return "fa";
        }else if (lang.equals("польский")){
            return "pl";
        }else if (lang.equals("португальский")){
            return "pt";
        }else if (lang.equals("румынский")){
            return "ro";
        }else if (lang.equals("русский")){
            return "ru";
        }else if (lang.equals("себуанский")){
            return "ceb";
        }else if (lang.equals("сербский")){
            return "sr";
        }else if (lang.equals("сингальский")){
            return "si";
        }else if (lang.equals("словацкий")){
            return "sk";
        }else if (lang.equals("словенский")){
            return "sl";
        }else if (lang.equals("суахили")){
            return "sw";
        }else if (lang.equals("сунданский")){
            return "su";
        }else if (lang.equals("таджикский")){
            return "tg";
        }else if (lang.equals("тайский")){
            return "th";
        }else if (lang.equals("тагальский")){
            return "tl";
        }else if (lang.equals("тамильский")){
            return "ta";
        }else if (lang.equals("татарский")){
            return "tt";
        }else if (lang.equals("телугу")){
            return "te";
        }else if (lang.equals("турецкий")){
            return "tr";
        }else if (lang.equals("удмуртский")){
            return "udm";
        }else if (lang.equals("узбекский")){
            return "uz";
        }else if (lang.equals("украинский")){
            return "uk";
        }else if (lang.equals("урду")){
            return "ur";
        }else if (lang.equals("финский")){
            return "fi";
        }else if (lang.equals("французский")){
            return "fr";
        }else if (lang.equals("хинди")){
            return "hi";
        }else if (lang.equals("хорватский")){
            return "hr";
        }else if (lang.equals("чешский")){
            return "cs";
        }else if (lang.equals("шведский")){
            return "sv";
        }else if (lang.equals("шотландский")){
            return "gd";
        }else if (lang.equals("эстонский")){
            return "et";
        }else if (lang.equals("эсперанто")){
            return "eo";
        }else if (lang.equals("яванский")){
            return "jv";
        }else if (lang.equals("японский")){
            return "ja";
        }else{
            return "";
        }
    }
}
