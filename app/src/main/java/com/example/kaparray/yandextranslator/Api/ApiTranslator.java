package com.example.kaparray.yandextranslator.Api;

import com.example.kaparray.yandextranslator.Data.Translation;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiTranslator {

    @GET("translate")
    Call<Translation> getTranslation(@Query("key") String key, @Query("text") String text, @Query("lang") String lang);
}
