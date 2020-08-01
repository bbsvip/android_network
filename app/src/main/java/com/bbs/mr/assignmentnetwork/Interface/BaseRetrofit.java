package com.bbs.mr.assignmentnetwork.Interface;

import retrofit2.*;
import retrofit2.adapter.rxjava2.*;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/* Created by MrBBS @ 2020
Email: 0331999bbs@gmail.com
Phone: 034 707 9556 */
public abstract class BaseRetrofit {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(mURL.BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
