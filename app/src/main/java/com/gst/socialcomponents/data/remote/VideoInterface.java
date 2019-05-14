package com.gst.socialcomponents.data.remote;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface VideoInterface {
    @Multipart
    @POST("/VideoUpload/uploadvid.php")
    Call<ResultObject> uploadVideoToServer(@Part MultipartBody.Part video);
}