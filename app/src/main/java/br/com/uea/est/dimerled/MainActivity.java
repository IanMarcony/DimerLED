package br.com.uea.est.dimerled;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.slider.Slider;
import com.google.firebase.database.DataSnapshot;

import br.com.uea.est.dimerled.controllers.StorageController;
import br.com.uea.est.dimerled.models.LightStatus;

public class MainActivity extends AppCompatActivity {

    private TextView intensityText;
    private Slider intensitySlider;
    private Button inputLightButton;

    private boolean isLightOn=  false;
    private LightStatus lightStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intensitySlider =  findViewById(R.id.intensityLightSlider);
        intensityText = findViewById(R.id.intensityLightText);
        inputLightButton = findViewById(R.id.toggleLightButton);

        lightStatus = new LightStatus();

        StorageController.getInstance().getStatus(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Ocorreu erro de comunicação com o servidor", Toast.LENGTH_LONG).show();
                }
                else {
                    LightStatus lightStatusFromDB = task.getResult().getValue(LightStatus.class);
                    if(lightStatusFromDB==null){
                        isLightOn= false;
                        verifyButtonColor(false);
                        intensitySlider.setValue(0.0f);
                        intensityText.setText("0%");
                        return;
                    }
                    int value =  (int) lightStatusFromDB.getIntensity();
                    isLightOn =  lightStatusFromDB.isLightOn();
                    verifyButtonColor(isLightOn);
                    intensitySlider.setValue(lightStatusFromDB.getIntensity());
                    intensityText.setText(value+"%");
                    lightStatus = lightStatusFromDB;

                }
            }
        });


        inputLightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLightOn = !isLightOn;
                verifyButtonColor(isLightOn);
                lightStatus.setLightOn(isLightOn);
                StorageController.getInstance().saveStatus(lightStatus);
            }
        });


        intensitySlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {

                float value = slider.getValue();
                lightStatus.setIntensity(value);

                StorageController.getInstance().saveStatus(lightStatus);
            }
        });

        intensitySlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                int valueInt =  (int) value;
                intensityText.setText(valueInt+"%");
            }
        });

    }


    private void verifyButtonColor(boolean isOn){
        if(isOn){
            inputLightButton.setBackgroundTintList(getApplicationContext().getColorStateList(R.color.primary_color));
        }else{
            inputLightButton.setBackgroundTintList(getApplicationContext().getColorStateList(R.color.dark_color));
        }

    }
}