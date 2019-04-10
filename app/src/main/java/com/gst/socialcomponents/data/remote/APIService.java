package com.gst.socialcomponents.data.remote;

import com.gst.socialcomponents.data.GetFacture;
import com.gst.socialcomponents.data.PostProfile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @POST("/servicesMAC/posts.php")
    @FormUrlEncoded
    Call<PostProfile> savePost(@Field("active") Boolean active,
                               @Field("email") String email,
                               @Field("id") String id,
                               @Field("likescount") Long likescount ,
                               @Field("mobile") String mobile,
                               @Field("numresidence") String numresidence,
                               @Field("photoUrl") String photoUrl,
                               @Field("residence") String residence,
                               @Field("token") String token,
                               @Field("type") Boolean type,
                               @Field("username") String username);

    @GET("/todos/{userId}")
    Call<GetFacture> getFacture(@Path(value = "userId", encoded = true) int numcall);

}
