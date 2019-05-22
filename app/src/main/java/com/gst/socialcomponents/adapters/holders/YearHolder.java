package com.gst.socialcomponents.adapters.holders;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.CustomFacturesMonthsAdapter;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.main.main.ToolActivity;
import com.gst.socialcomponents.model.Facture;
import com.gst.socialcomponents.model.Factureitemdata;
import com.gst.socialcomponents.model.InfoSyndic;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumBloc;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.SoldeAppartement;
import com.gst.socialcomponents.utils.FormatterUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YearHolder extends RecyclerView.ViewHolder {

            ListView listview;
            private APIService mAPIService;
            private TextView balance,reste,yeartv,retenu,retenu_annuel;
            ArrayList<Factureitemdata> dataModels;
            private CustomFacturesMonthsAdapter listAdapter ;
            private ConstraintLayout rest,solde,redev,retenue;






    public YearHolder(View yearlist) {
        super(yearlist);
        this.listview=itemView.findViewById(R.id.listViewmonths);
         this.balance =itemView.findViewById(R.id.bAc);
        this.reste =itemView.findViewById(R.id.brest);
        this.yeartv =itemView.findViewById(R.id.textView21);
        this.retenu =itemView.findViewById(R.id.retenu);
        retenu_annuel=itemView.findViewById(R.id.retenueannuel);
        this.rest=itemView.findViewById(R.id.ConstraintLayoutrest);
        this.solde=itemView.findViewById(R.id.constraintLayoutrest);
        this.redev=itemView.findViewById(R.id.ConstraintLayoutsolde);
        this.retenue=itemView.findViewById(R.id.constraintLayout2);







    }

    public void updateUI(Integer year,String appart,Context cxt,String residence,String bloc){

         getcbmarq(appart,cxt,residence,bloc);

        }
    public void updateUI2(Integer year,String appart,Context cxt,String residence,Integer frai,String bloc){

        gethistoricpayement(appart,cxt,residence,frai,year,bloc);

    }

    private void gethistoricpayement(String appart, Context cxt, String residence, Integer frai,Integer year,String bloc) {
      ProgressDialog pd = new ProgressDialog(cxt);
        pd.setMessage("chargement des données");
        pd.show();
        pd.setCancelable(false);

        yeartv.setText(String.valueOf(year));
        rest.setVisibility(View.GONE);
        solde.setVisibility(View.GONE);
        redev.setVisibility(View.GONE);
        retenue.setVisibility(View.GONE);
        mAPIService = ApiUtils.getAPIService();
        mAPIService.getNumChantier(residence).enqueue(new Callback<NumChantier>() {
            @Override
            public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
                Integer numchantier=Integer.valueOf(response.body().getCbmarq());
                final String[] date = new String[1];
                final int[] frais = new int[1];
                final Date[] strtodate = new Date[1];

                mAPIService.getbloc(bloc,numchantier).enqueue(new Callback<NumBloc>() {
                    @Override
                    public void onResponse(Call<NumBloc> call, Response<NumBloc> response) {
                        Integer numbloc =Integer.valueOf(response.body().getCbmarq());
                mAPIService.getNumOfAppartements(appart,numbloc).enqueue(new Callback<NumAppart>() {
                    @Override
                    public void onResponse(Call<NumAppart> call, Response<NumAppart> response) {
                        pd.dismiss();
                        if(response.isSuccessful()) {
                            mAPIService.getInfoSyndic(Integer.valueOf(response.body().getCbmarq())).enqueue(new Callback<InfoSyndic>() {
                                int numAppart =Integer.valueOf(response.body().getCbmarq());

                                @Override
                                public void onResponse(Call<InfoSyndic> call, Response<InfoSyndic> response) {
                                    date[0] =response.body().getDateRemiseCle();
                                    frais[0] =response.body().getFraisupposed();
                                    mAPIService.getSoldeappartement(numAppart).enqueue(new Callback<SoldeAppartement>() {
                                        @Override
                                        public void onResponse(Call<SoldeAppartement> call, Response<SoldeAppartement> response) {
                                            Integer retenu_ann= response.body().getsRetenu();
                                            balance.setText(String.valueOf(response.body().getSolde()));
                                            dataModels= new ArrayList<>();
                                            dataModels.add(new Factureitemdata("Janvier", "", "",0,0));
                                            dataModels.add(new Factureitemdata("Fevrier", "", "",0,0));
                                            dataModels.add(new Factureitemdata("Mars","","",0,0));
                                            dataModels.add(new Factureitemdata("Avril", "", "",0,0));
                                            dataModels.add(new Factureitemdata("May", "", "",0,0));
                                            dataModels.add(new Factureitemdata("Juin", "", "",0,0));
                                            dataModels.add(new Factureitemdata("Juillet","","",0,0));
                                            dataModels.add(new Factureitemdata("Aout", "", "",0,0));
                                            dataModels.add(new Factureitemdata("Septembre", "", "",0,0));
                                            dataModels.add(new Factureitemdata("Octobre", "", "",0,0));
                                            dataModels.add(new Factureitemdata("Novembre","","",0,0));
                                            dataModels.add(new Factureitemdata("Decembre", "", "",0,0));
                                            listAdapter = new CustomFacturesMonthsAdapter(dataModels, cxt);
                                            listview.setAdapter( listAdapter );

                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                             try {
                                                strtodate[0] = format.parse ( date[0] );
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));

                                             cal.setTime(strtodate[0]);
                                            int yearremi = cal.get(Calendar.YEAR);
                                            int month =cal.get(Calendar.MONTH);
                                            if(yearremi == year){
                                                dataModels.get(month).setImgview2(R.drawable.remise_key);
                                            }


                                            Integer fraismensuelhistoriq=frai/12;
                                            Integer soldehistori=frai+retenu_ann;
                                            Integer monthspaid=soldehistori/fraismensuelhistoriq;
                                            for(int i =0;i<monthspaid;i++){
                                                dataModels.get(i).setImgview(R.drawable.ic_done);
                                                dataModels.get(i).setRemise_cle(String.valueOf(fraismensuelhistoriq)+" TND");
                                            }




                                        }

                                        @Override
                                        public void onFailure(Call<SoldeAppartement> call, Throwable t) {
                                            Toast.makeText(cxt, "Connexion au serveur échouée", Toast.LENGTH_SHORT).show();
                                            Log.v("errorcom",t.getMessage()+ "  1");

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Call<InfoSyndic> call, Throwable t) {
                                    Toast.makeText(cxt, "Données non trouvées", Toast.LENGTH_SHORT).show();
                                    Log.v("errorcom","21"+t.getMessage());

                                }
                            });

                        }
                    }
                    @Override
                    public void onFailure(Call<NumAppart> call, Throwable t) {
                        Toast.makeText(cxt, "Connexion au serveur échouée", Toast.LENGTH_SHORT).show();
                        Log.v("errorcom","3"+t.getMessage());

                    }

                });

                    }

                    @Override
                    public void onFailure(Call<NumBloc> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<NumChantier> call, Throwable t) {
                Log.v("errorcom","4"+t.getMessage());

            }
        });

    }



    public void getcbmarq(String appartId,Context cxt,String residence,String bloc) {
        final String[] date = new String[1];
        final int[] frais = new int[1];
        final Date[] strtodate = new Date[1];

        ProgressDialog pd = new ProgressDialog(cxt);
        pd.setMessage("chargement des données");
        pd.show();
        pd.setCancelable(false);

        FirebaseFunctions.getInstance().getHttpsCallable("getTime")
                .call().addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Long timestamp = (long) httpsCallableResult.getData();

                mAPIService = ApiUtils.getAPIService();
                mAPIService.getNumChantier(residence).enqueue(new Callback<NumChantier>() {
                    @Override
                    public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
                        Integer numchantier=Integer.valueOf(response.body().getCbmarq());

                        mAPIService.getbloc(bloc,numchantier).enqueue(new Callback<NumBloc>() {
                            @Override
                            public void onResponse(Call<NumBloc> call, Response<NumBloc> response) {
                                Integer numbloc =Integer.valueOf(response.body().getCbmarq());

                        mAPIService.getNumOfAppartements(appartId,numbloc).enqueue(new Callback<NumAppart>() {
                            @Override
                            public void onResponse(Call<NumAppart> call, Response<NumAppart> response) {
                                pd.dismiss();
                                if(response.isSuccessful()) {
                                    mAPIService.getInfoSyndic(Integer.valueOf(response.body().getCbmarq())).enqueue(new Callback<InfoSyndic>() {
                                        int numAppart =Integer.valueOf(response.body().getCbmarq());

                                        @Override
                                        public void onResponse(Call<InfoSyndic> call, Response<InfoSyndic> response) {
                                            date[0] =response.body().getDateRemiseCle();
                                            frais[0] =response.body().getFraisupposed();
                                            mAPIService.getSoldeappartement(numAppart).enqueue(new Callback<SoldeAppartement>() {
                                                @Override
                                                public void onResponse(Call<SoldeAppartement> call, Response<SoldeAppartement> response) {


                                                    retenu_annuel.setText(String.valueOf(response.body().getsRetenu()));
                                                    balance.setText(String.valueOf(response.body().getSolde()));
                                                    dataModels= new ArrayList<>();
                                                    dataModels.add(new Factureitemdata("Janvier", "", "",0,0));
                                                    dataModels.add(new Factureitemdata("Fevrier", "", "",0,0));
                                                    dataModels.add(new Factureitemdata("Mars","","",0,0));
                                                    dataModels.add(new Factureitemdata("Avril", "", "",0,0));
                                                    dataModels.add(new Factureitemdata("May", "", "",0,0));
                                                    dataModels.add(new Factureitemdata("Juin", "", "",0,0));
                                                    dataModels.add(new Factureitemdata("Juillet","","",0,0));
                                                    dataModels.add(new Factureitemdata("Aout", "", "",0,0));
                                                    dataModels.add(new Factureitemdata("Septembre", "", "",0,0));
                                                    dataModels.add(new Factureitemdata("Octobre", "", "",0,0));
                                                    dataModels.add(new Factureitemdata("Novembre","","",0,0));
                                                    dataModels.add(new Factureitemdata("Decembre", "", "",0,0));
                                                    listAdapter = new CustomFacturesMonthsAdapter(dataModels, cxt);
                                                    listview.setAdapter( listAdapter );

                                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                                    String dateString = format.format( new Date()   );
                                                    try {
                                                        strtodate[0] = format.parse ( date[0] );
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                                    Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));

                                                    Date datenow = new Date(timestamp);
                                                    cal2.setTime(strtodate[0]);
                                                    cal.setTime(datenow);
                                                    int year = cal2.get(Calendar.YEAR);
                                                    int month = cal2.get(Calendar.MONTH);
                                                    int day = cal2.get(Calendar.DAY_OF_MONTH);

                                                    int yearlocal = cal.get(Calendar.YEAR);

                                                    yeartv.setText(String.valueOf(yearlocal));


                                                    if(year==yearlocal){
                                                        int nummonthspayable=12-month;
                                                        int monthamount =(response.body().getSolde()+response.body().getsRetenu())/nummonthspayable;
                                                        int monthlyfrais=frais[0]/12;
                                                        int monthspayable=(response.body().getSolde()+response.body().getsRetenu())/monthlyfrais;
                                                        int totalpayed =nummonthspayable*monthamount;
                                                        int totalmustbepayed=nummonthspayable*monthlyfrais;
                                                        reste.setText(String.valueOf(totalmustbepayed-(response.body().getSolde()+response.body().getsRetenu())));
                                                        retenu.setText(String.valueOf(monthspayable*monthlyfrais));


                                                        dataModels.get(month).setImgview2(R.drawable.remise_key);
                                                        if((month+monthspayable)<12){
                                                            for(int i =month;i<month+monthspayable;i++){
                                                                dataModels.get(i).setImgview(R.drawable.ic_done);
                                                                dataModels.get(i).setRemise_cle(String.valueOf(monthlyfrais)+" TND");
                                                            }
                                                        }else{
                                                            for(int i =month;i<12;i++){
                                                                dataModels.get(i).setImgview(R.drawable.ic_done);
                                                                dataModels.get(i).setRemise_cle(String.valueOf(monthlyfrais)+" TND");
                                                            }
                                                        }


                                                    }else {


                                                        int nummonthspayable=12;
                                                        int monthamount =response.body().getSolde()/nummonthspayable;
                                                        int monthlyfrais=frais[0]/12;
                                                        int monthspayable=response.body().getSolde()/monthlyfrais;
                                                        int totalpayed =nummonthspayable*monthamount;
                                                        int totalmustbepayed=frais[0];
                                                        reste.setText(String.valueOf(totalmustbepayed-response.body().getSolde()-response.body().getsRetenu()));
                                                        retenu.setText(String.valueOf(monthspayable*monthlyfrais));


                                                        if(monthspayable<=12){
                                                            for(int i =0;i<monthspayable;i++){
                                                                dataModels.get(i).setImgview(R.drawable.ic_done);
                                                                dataModels.get(i).setRemise_cle(String.valueOf(monthlyfrais)+" TND");
                                                            }
                                                        }else{
                                                            for(int i =0;i<12;i++){
                                                                dataModels.get(i).setImgview(R.drawable.ic_done);
                                                                dataModels.get(i).setRemise_cle(String.valueOf(monthlyfrais)+" TND");
                                                            }
                                                        }


                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<SoldeAppartement> call, Throwable t) {
                                                    Toast.makeText(cxt, "Connexion au serveur échouée", Toast.LENGTH_SHORT).show();
                                                    Log.v("errorcom",t.getMessage()+ "  1");

                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailure(Call<InfoSyndic> call, Throwable t) {
                                            Toast.makeText(cxt, "Données non trouvées", Toast.LENGTH_SHORT).show();
                                            Log.v("errorcom","22"+t.getMessage());

                                        }
                                    });

                                }
                            }
                            @Override
                            public void onFailure(Call<NumAppart> call, Throwable t) {
                                Toast.makeText(cxt, "Connexion au serveur échouée", Toast.LENGTH_SHORT).show();
                                Log.v("errorcom","3"+t.getMessage());

                            }

                        });

                            }

                            @Override
                            public void onFailure(Call<NumBloc> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<NumChantier> call, Throwable t) {
                        Log.v("errorcom","4"+t.getMessage());

                    }
                });

            }
        });


    }
    }



