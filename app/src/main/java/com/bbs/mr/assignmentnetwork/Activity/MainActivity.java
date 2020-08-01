package com.bbs.mr.assignmentnetwork.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bbs.mr.assignmentnetwork.R;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref;
    String type = "";
    Scene boss,manager,employee,acc,product,info;
    ViewGroup sceneRoot,sceneSubBoss;
    TabLayout tab_boss;
    Transition transition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transition = new Fade();
        sceneRoot = findViewById(R.id.scene_root_main);
        boss = Scene.getSceneForLayout(sceneRoot,R.layout.manager_acc_sence,this);
        manager = Scene.getSceneForLayout(sceneRoot,R.layout.manager_product_sence,this);
        employee = Scene.getSceneForLayout(sceneRoot,R.layout.product_sence,this);

        pref = getSharedPreferences("USER_INFO",MODE_PRIVATE);
        type = pref.getString("ACCOUNT_TYPE","");

        boss.setEnterAction(new Runnable() {
            @Override
            public void run() {
                sceneSubBoss = boss.getSceneRoot().findViewById(R.id.scene_sub_main);
                acc = Scene.getSceneForLayout(sceneSubBoss,R.layout.manager_acc_sub,sceneSubBoss.getContext());
                product = Scene.getSceneForLayout(sceneSubBoss,R.layout.product_sub,sceneSubBoss.getContext());
                info = Scene.getSceneForLayout(sceneSubBoss,R.layout.info,sceneSubBoss.getContext());
                tab_boss = boss.getSceneRoot().findViewById(R.id.tab_boss);
                tab_boss.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        if (tab.getText().toString().contains("Sản phẩm")){
                            TransitionManager.go(product,transition);
                        } else if (tab.getText().toString().contains("Nhân viên")){
                            TransitionManager.go(acc,transition);
                        } else {
                            TransitionManager.go(info,transition);
                        }
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
                product.enter();
            }
        });

        if (type.equals("0")){
            boss.enter();
        } else if(type.equals("1")){
            manager.enter();
        } else {
            employee.enter();
        }
    }
}