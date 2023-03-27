package br.com.uea.est.dimerled.controllers;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import br.com.uea.est.dimerled.models.LightStatus;
import br.com.uea.est.dimerled.utils.Constants;

public class StorageController {
    private static StorageController controller;
    private static DatabaseReference databaseReference;


    public static StorageController getInstance(){
        if(controller==null){
            controller = new StorageController();
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return  controller;
    }

    public void saveStatus(LightStatus lightStatus){
        Map<String, LightStatus> lightStatusMap = new HashMap<>();

        lightStatusMap.put(Constants.DEFAULT_KEY, lightStatus);

        databaseReference.setValue(lightStatusMap);
    }


    public void getStatus(OnCompleteListener<DataSnapshot> listener){
        databaseReference.child(Constants.DEFAULT_KEY).get().addOnCompleteListener(listener);
    }


}
