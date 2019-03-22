package com.gst.socialcomponents.main.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.gst.socialcomponents.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class GalleryActivity extends AppCompatActivity {

    SliderLayout sliderLayout;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        sliderLayout = findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.SWAP); //set indicator animation by using SliderLayout.Animations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderLayout.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderLayout.setScrollTimeInSec(5); //set scroll delay in seconds :

        setSliderViews();
    }

    private void setSliderViews() {

        for (int i = 0; i <= 17; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(getApplicationContext());

            switch (i) {
                case 0:
                   // sliderView.setImageUrl("http://vosges.immobilieregloulou.com/wp-content/uploads/2018/02/1.jpg");
                    sliderView.setImageDrawable(R.drawable.vosges);
                    description="Résidence les vosges";
                    break;
                case 1:
                    sliderView.setImageDrawable(R.drawable.sierra);
                    description="Résidence Sierra";
                    break;
                case 2:
                    sliderView.setImageDrawable(R.drawable.pyrenees);
                    description="Résidence les pyrénées";
                    break;
                case 3:
                    sliderView.setImageDrawable(R.drawable.prestige);
                    description="Résidence Prestige";
                    break;
                case 4:
                    sliderView.setImageDrawable(R.drawable.palms);
                    description="Résidence Palm's";
                    break;
                case 5:
                    sliderView.setImageDrawable(R.drawable.maarouf);
                    description="résidence maarouf";
                    break;
                case 6:
                    sliderView.setImageDrawable(R.drawable.lotissementmsaken);
                    description="Lotisement Msaken";
                    break;
                case 7:
                    sliderView.setImageDrawable(R.drawable.k2);
                    description="résidence K2";
                    break;
                case 8:
                    sliderView.setImageDrawable(R.drawable.jura);
                    description="résidence le jura";
                    break;
                case 9:
                    sliderView.setImageDrawable(R.drawable.jasmins);
                    description="résidence les jasmins 3";
                    break;
                case 10:
                    sliderView.setImageDrawable(R.drawable.jasmins1);
                    description="résidence les jasmins 1";
                    break;
                case 11:
                    sliderView.setImageDrawable(R.drawable.jasmins2);
                    description="résidence les jasmins 3";
                    break;
                case 12:
                    sliderView.setImageDrawable(R.drawable.houda2);
                    description="résidence el houda 2";
                    break;
                case 13:
                    sliderView.setImageDrawable(R.drawable.houda);
                    description="résidence el houda";
                    break;
                case 14:
                    sliderView.setImageDrawable(R.drawable.ennakhil);
                    description="résidence ennakhil";
                    break;
                case 15:
                    sliderView.setImageDrawable(R.drawable.chottmaryem);
                    description="résidence chott maryem";
                    break;
                case 16:
                    sliderView.setImageDrawable(R.drawable.arcade);
                    description="résidence les arcades";
                    break;
                case 17:
                    sliderView.setImageDrawable(R.drawable.alpes);
                    description="résidence les alpes";
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(description);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                   // Toast.makeText(GalleryActivity.this, "" + (finalI + 1), Toast.LENGTH_SHORT).show();
                }
            });

            //at last add this view in your layout :
            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finish();
    }
}










