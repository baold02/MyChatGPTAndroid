package com.example.mychatgptandroid.retrofit;

import com.example.mychatgptandroid.model.MessParamPost;
import com.example.mychatgptandroid.model.MessRespone;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiGpt {
    @Headers(
            {
                    "Content-Type: application/json",
                    "Authorization: Bearer api-key"
            }
    )

    @POST("completions")
    Observable<MessRespone> postQues(
            @Body MessParamPost messParamPost
            );
}
