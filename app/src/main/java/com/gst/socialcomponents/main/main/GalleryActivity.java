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

        for (int i = 0; i <= 3; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(getApplicationContext());

            switch (i) {
                case 0:
                    sliderView.setImageUrl("http://vosges.immobilieregloulou.com/wp-content/uploads/2018/02/1.jpg");
                    break;
                case 1:
                    sliderView.setImageUrl("http://diarelmanezah.immobilieregloulou.com/wp-content/uploads/2018/09/bloc1-1.jpg");
                    break;
                case 2:
                    sliderView.setImageUrl("http://jasmins3.immobilieregloulou.com/assets/imgs/gal/ext00.jpg");
                    break;
                case 3:
                    sliderView.setImageUrl("http://residencek2.immobilieregloulou.com/web/uploads/images/6a7011ac30e3308c247bbfe4ffc69f48.jpeg");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("this is a good example of what i am trying to demonstrate for a gallery slider, it is simple but i think it is efficient " + (i + 1));
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    Toast.makeText(GalleryActivity.this, "" + (finalI + 1), Toast.LENGTH_SHORT).show();
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










