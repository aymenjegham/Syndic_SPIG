package com.gst.socialcomponents.data.remote;

import com.gst.socialcomponents.data.PostProfile;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
                               @Field("typeuser") Boolean typeuser,
                               @Field("typeuser") String username);

}
