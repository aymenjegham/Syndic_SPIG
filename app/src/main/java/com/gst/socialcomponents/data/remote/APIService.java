package com.gst.socialcomponents.data.remote;

import com.gst.socialcomponents.data.GetFacture;
import com.gst.socialcomponents.data.PostProfile;
import com.gst.socialcomponents.model.AcceptInfo;
import com.gst.socialcomponents.model.Appartements;
import com.gst.socialcomponents.model.Blocs;
import com.gst.socialcomponents.model.Chantiers;
import com.gst.socialcomponents.model.DataReunion;
import com.gst.socialcomponents.model.InfoSyndic;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumBloc;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.NumPrincipal;
import com.gst.socialcomponents.model.NumReunion;
import com.gst.socialcomponents.model.NumUser;
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

    @POST("/posts.php")
    @FormUrlEncoded
    Call<String> savePost(  @Field("photoUrl") String photoUrl,
                            @Field("active") Boolean active,
                            @Field("email") String email,
                            @Field("id") String id,
                            @Field("mobile") String mobile,
                            @Field("username") String username,
                            @Field("residence") String residence,
                            @Field("numresidence") Integer numresidence,
                            @Field("type") boolean type);




    @GET("/todos/{userId}")
    Call<GetFacture> getFacture(@Path(value = "userId", encoded = true) int numcall);


    @POST("/getChantiers.php")
    Call<List<Chantiers>> getListOfChantiers();


    @POST("/getAppartementsbloc.php")
    @FormUrlEncoded
    Call<List<Blocs>> getListOfAppartementsbloc(@Field("cbmarq") Integer cbmarq);

    @POST("/getAppartements.php")
    @FormUrlEncoded
    Call<List<Appartements>> getListOfAppartements(@Field("cbmarq") Integer cbmarq);

   @POST("/getNumAppart.php")
   @FormUrlEncoded
   Call<NumAppart> getNumOfAppartements(@Field("a_intitule") String  a_intitule,
                                        @Field("cbmarq") Integer  cbmarq);

    @POST("/getNumChantier.php")
    @FormUrlEncoded
    Call<NumChantier> getNumChantier(@Field("a_intitule") String  a_intitule);

    @POST("/getbloc.php")
    @FormUrlEncoded
    Call<NumBloc> getbloc(@Field("b_intitule") String  b_intitule,
                          @Field("cbmarq") Integer  cbmarq);

    @POST("/getInfoSyndic.php")
    @FormUrlEncoded
    Call<InfoSyndic> getInfoSyndic(@Field("iAppartement") int  iAppartment);

    @POST("/getSoldeAppartement.php")
    @FormUrlEncoded
    Call<SoldeAppartement> getSoldeappartement(@Field("sAppartement") int  sAppartment);

    @POST("/insetReglement.php")
    @FormUrlEncoded
    Call<String> insertReglement(@Field("rMontant") Integer rMontant,
                                 @Field("rAppartement") Integer rAppartement,
                                 @Field("rDescription") String rDescription,
                                 @Field("rStatut") Integer rStatut,
                                 @Field("rCategorie") Integer rCategorie);



    @POST("/getIdUser.php")
    @FormUrlEncoded
    Call<NumUser> getIdUser(@Field("email") String  email);

    @POST("/getIdReunion.php")
    @FormUrlEncoded
    Call<List<NumReunion>> getIdReunion(@Field("profile-id") Integer  profileid);

    @POST("/getIdReunionBlocs.php")
    @FormUrlEncoded
    Call<List<NumReunion>> getIdReunionBlocs(@Field("profile-id") Integer  profileid);

    @POST("/getReunion.php")
    @FormUrlEncoded
    Call<DataReunion> getReunion(@Field("cbmarq") Integer  cbmarq);


    @POST("/insetReponseReunion.php")
    @FormUrlEncoded
    Call<String> insertReponseReunion(@Field("profile_id") Integer rMontant,
                                      @Field("reunion_id") Integer rAppartement);


    @POST("/getReunionAcceptInfo.php")
    @FormUrlEncoded
    Call<AcceptInfo> getReunionAcceptInfo(@Field("profile_id") Integer rMontant,
                                          @Field("reunion_id") Integer rAppartement);


    @POST("/getnumChantiers.php")
    @FormUrlEncoded
    Call<NumChantier> getnumChantiers(@Field("C_intitule") String  C_intitule);


    @POST("/getPrincipal.php")
    @FormUrlEncoded
    Call<NumPrincipal> getPrincipal(@Field("email") String  email);

}
