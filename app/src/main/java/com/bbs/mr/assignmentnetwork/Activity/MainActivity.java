package com.bbs.mr.assignmentnetwork.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Scene;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                            product.enter();
                        } else if (tab.getText().toString().contains("Nhân viên")){
                            acc.enter();
                        } else {
                            info.enter();
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