package com.gst.socialcomponents.data.remote;

import com.gst.socialcomponents.data.GetFacture;
import com.gst.socialcomponents.data.PostProfile;
import com.gst.socialcomponents.model.Appartements;
import com.gst.socialcomponents.model.Chantiers;
import com.gst.socialcomponents.model.InfoSyndic;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.SoldeAppartement;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

   //@POST("/servicesMAC/posts.php")
   @POST("/posts.php")
    @FormUrlEncoded
    Call<String> savePost(@Field("active") Boolean active,
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


    @POST("/getChantiers.php")
    Call<List<Chantiers>> getListOfChantiers();


    @POST("/getAppartements.php")
    @FormUrlEncoded
    Call<List<Appartements>> getListOfAppartements(@Field("cbmarq") Integer cbmarq);

   @POST("/getNumAppart.php")
   @FormUrlEncoded
   Call<NumAppart> getNumOfAppartements(@Field("a_intitule") String  a_intitule);

    @POST("/getNumChantier.php")
    @FormUrlEncoded
    Call<NumChantier> getNumChantier(@Field("a_intitule") String  a_intitule);

    @POST("/getInfoSyndic.php")
    @FormUrlEncoded
    Call<InfoSyndic> getInfoSyndic(@Field("iAppartement") int  iAppartment);

    @POST("/getSoldeAppartement.php")
    @FormUrlEncoded
    Call<SoldeAppartement> getSoldeappartement(@Field("sAppartement") int  sAppartment);

}
