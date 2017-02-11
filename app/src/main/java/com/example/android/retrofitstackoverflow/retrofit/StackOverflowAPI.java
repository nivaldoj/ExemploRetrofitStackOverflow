package com.example.android.retrofitstackoverflow.retrofit;

import com.example.android.retrofitstackoverflow.models.StackOverflowQuestions;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StackOverflowAPI {

    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);

}
