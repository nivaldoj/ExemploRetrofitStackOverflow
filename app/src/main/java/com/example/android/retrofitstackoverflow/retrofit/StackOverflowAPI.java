package com.example.android.retrofitstackoverflow.retrofit;

import com.example.android.retrofitstackoverflow.models.StackOverflowQuestions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Código copiado/modificado do tutorial "Using Retrofit 2.x as REST client - Tutorial"
 * Obtido em: http://www.vogella.com/tutorials/Retrofit/article.html - 10/02/2017
 */
public interface StackOverflowAPI {

    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);

}
