package com.bbs.mr.assignmentnetwork.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bbs.mr.assignmentnetwork.Interface.BaseRetrofit;
import com.bbs.mr.assignmentnetwork.Interface.mAPI;
import com.bbs.mr.assignmentnetwork.R;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {

    Scene loginScene;
    Scene registerScene;
    ViewGroup sceneRoot;
    Transition transition;
    Button btnRegLo, btnLoReg, btnLogin, btnRegister;
    EditText userLog, passLog, userReg, passReg, nameReg, emailReg;
    TextInputLayout lUserLog, lPassLog, lUserReg, lPassReg, lNameReg, lEmailReg;
    CheckBox chkLog;
    ProgressBar prgLog, prgReg;
    LinearLayout masterLayout;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    String[] info;
    mAPI mAPI;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getSharedPreferences("USER_INFO", MODE_PRIVATE);
        Retrofit retrofit = BaseRetrofit.getClient();
        mAPI = retrofit.create(mAPI.class);
        masterLayout = findViewById(R.id.master_layout);
        transition = TransitionInflater.from(this).inflateTransition(R.transition.activity_slide);
        sceneRoot = findViewById(R.id.scene_root);
        loginScene = Scene.getSceneForLayout(sceneRoot, R.layout.login_sence, this);
        registerScene = Scene.getSceneForLayout(sceneRoot, R.layout.register_sence, this);
        loginScene.setEnterAction(new Runnable() {
            @Override
            public void run() {
                lUserLog = loginScene.getSceneRoot().findViewById(R.id.lUserLog);
                lPassLog = loginScene.getSceneRoot().findViewById(R.id.lPassLog);
                userLog = loginScene.getSceneRoot().findViewById(R.id.edtUserLog);
                passLog = loginScene.getSceneRoot().findViewById(R.id.edtPassLog);
                chkLog = loginScene.getSceneRoot().findViewById(R.id.chkLogin);
                prgLog = loginScene.getSceneRoot().findViewById(R.id.prgLog);
                btnRegLo = loginScene.getSceneRoot().findViewById(R.id.btnRegLogin);
                btnLogin = loginScene.getSceneRoot().findViewById(R.id.btnLogin);
                userLog.setText(pref.getString("USERNAME", null));
                passLog.setText(pref.getString("PASSWORD", null));
                chkLog.setChecked(pref.getBoolean("REMEMBER", false));
                userLog.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        btnLogin.setClickable(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (userLog.getText().length() < 6) {
                            lUserLog.setErrorEnabled(true);
                            lUserLog.setError("Chưa đủ ký tự");
                            btnLogin.setClickable(false);
                        } else {
                            lUserLog.setErrorEnabled(false);
                            btnLogin.setClickable(true);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                passLog.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        btnLogin.setClickable(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (passLog.getText().length() < 8) {
                            lPassLog.setErrorEnabled(true);
                            lPassLog.setError("Chưa đủ ký tự");
                            btnLogin.setClickable(false);
                        } else {
                            lPassLog.setErrorEnabled(false);
                            btnLogin.setClickable(true);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (userLog.getText().length() == 0) {
                            lUserLog.setErrorEnabled(true);
                            lUserLog.setError("Không được để trống");
                            btnLogin.setClickable(false);
                        } else if (passLog.getText().length() == 0) {
                            lPassLog.setErrorEnabled(true);
                            lPassLog.setError("Không được để trống");
                            btnLogin.setClickable(false);
                        } else {
                            if (userLog.getText().toString().matches("^[a-zA-Z0-9]*$")) {
                                if (passLog.getText().toString().matches("^[a-zA-Z0-9]*$")) {
                                    doLogin(userLog.getText().toString(), passLog.getText().toString());
                                } else {
                                    lPassLog.setErrorEnabled(true);
                                    lPassLog.setError("Chỉ được phép chứa ký tự a-Z,0-9");
                                }
                            } else {
                                lUserLog.setErrorEnabled(true);
                                lUserLog.setError("Chỉ được phép chứa ký tự a-Z,0-9");
                            }
                        }
                    }
                });
                btnRegLo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToRegister();
                    }
                });
            }
        });
        registerScene.setEnterAction(new Runnable() {
            @Override
            public void run() {
                userReg = registerScene.getSceneRoot().findViewById(R.id.edtUserReg);
                lUserReg = registerScene.getSceneRoot().findViewById(R.id.lUserReg);
                passReg = registerScene.getSceneRoot().findViewById(R.id.edtPassReg);
                lPassReg = registerScene.getSceneRoot().findViewById(R.id.lPassReg);
                nameReg = registerScene.getSceneRoot().findViewById(R.id.edtNameReg);
                lNameReg = registerScene.getSceneRoot().findViewById(R.id.lNameReg);
                emailReg = registerScene.getSceneRoot().findViewById(R.id.edtEmailReg);
                lEmailReg = registerScene.getSceneRoot().findViewById(R.id.lEmailReg);
                prgReg = registerScene.getSceneRoot().findViewById(R.id.prgReg);
                btnLoReg = registerScene.getSceneRoot().findViewById(R.id.btnLogRegister);
                btnRegister = registerScene.getSceneRoot().findViewById(R.id.btnRegister);
                userReg.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        btnRegister.setClickable(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (userReg.getText().length() < 6) {
                            lUserReg.setErrorEnabled(true);
                            lUserReg.setError("Chưa đủ ký tự");
                            btnRegister.setClickable(false);
                        } else {
                            btnRegister.setClickable(true);
                            lUserReg.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                passReg.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        btnRegister.setClickable(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (passReg.getText().length() < 8) {
                            lPassReg.setErrorEnabled(true);
                            lPassReg.setError("Chưa đủ ký tự");
                            btnRegister.setClickable(false);
                        } else {
                            btnRegister.setClickable(true);
                            lPassReg.setErrorEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                nameReg.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        btnRegister.setClickable(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (nameReg.getText().toString().matches("^[a-zA-Z ]*$")) {
                            lNameReg.setErrorEnabled(false);
                            btnRegister.setClickable(true);
                        } else {
                            lNameReg.setErrorEnabled(true);
                            btnRegister.setClickable(false);
                            lNameReg.setError("Không được chứa số và ký tự đặt biệt");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                emailReg.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        btnRegister.setClickable(false);
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (emailReg.getText().toString().matches("^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$")) {
                            lEmailReg.setErrorEnabled(false);
                            btnRegister.setClickable(true);
                        } else {
                            lEmailReg.setErrorEnabled(true);
                            btnRegister.setClickable(false);
                            lEmailReg.setError("Sai định dạng Email");
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                btnRegister.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (userReg.getText().length() == 0) {
                            lUserReg.setErrorEnabled(true);
                            lUserReg.setError("Không được để trống");
                            btnRegister.setClickable(false);
                        } else if (passReg.getText().length() == 0) {
                            btnRegister.setClickable(false);
                            lPassReg.setErrorEnabled(true);
                            lPassReg.setError("Không được để trống");
                        } else if (nameReg.getText().length() == 0) {
                            lNameReg.setErrorEnabled(true);
                            lNameReg.setError("Không được để trống");
                            btnRegister.setClickable(false);
                        } else if (emailReg.getText().length() == 0) {
                            lEmailReg.setErrorEnabled(true);
                            lEmailReg.setError("Không được để trống");
                            btnRegister.setClickable(false);
                        } else {
                            if (userReg.getText().toString().matches("^[a-zA-Z0-9]*$")) {
                                if (passReg.getText().toString().matches("^[a-zA-Z0-9]*$")) {
                                    doRegister(userReg.getText().toString(), passReg.getText().toString(), nameReg.getText().toString(), emailReg.getText().toString());
                                } else {
                                    lPassReg.setErrorEnabled(true);
                                    lPassReg.setError("Chỉ được chứa ký tự a-Z,0-9");
                                }
                            } else {
                                lUserReg.setErrorEnabled(true);
                                lUserReg.setError("Chỉ được chứa ký tự a-Z,0-9");
                            }
                        }
                    }
                });
                btnLoReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goToLogin();
                    }
                });
            }
        });
        loginScene.enter();
    }

    private void doRegister(String username, String password, String name, String email) {
        compositeDisposable.add(mAPI.register(username, password, name, email).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("registered")) {
                            Toast.makeText(LoginActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            edit = pref.edit();
                            edit.putString("USERNAME", username);
                            edit.putString("PASSWORD", password);
                            edit.apply();
                            goToLogin();
                        } else if (s.contains("username")) {
                            lUserReg.setErrorEnabled(true);
                            lUserReg.setError("Tên người dùng đã được sử dụng");
                        } else if (s.contains("email")) {
                            lEmailReg.setErrorEnabled(true);
                            lEmailReg.setError("Email đã được sử dụng");
                        } else {
                            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    private void doLogin(String username, String password) {
        compositeDisposable.add(mAPI.login(username, password).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.contains("login#")) {
                            String user = s.substring(6);
                            info = user.split("#");
                            for (String x : info) {
                                Log.i("wwwww", x);
                            }
                            edit = pref.edit();
                            if(info.length>7){
                                if (!chkLog.isChecked()) {
                                    edit.clear();
                                } else {
                                    edit.putString("USERNAME", info[0]);
                                    edit.putString("PASSWORD", password);
                                    edit.putString("NAME", info[3]);
                                    edit.putString("EMAIL", info[4]);
                                    edit.putString("PHONE", info[5]);
                                    edit.putString("BIRTHDAY", info[6]);
                                    edit.putString("ADDRESS", info[7]);
                                    edit.putString("ACCOUNT_TYPE", info[8]);
                                    edit.putBoolean("REMEMBER", chkLog.isChecked());
                                }
                            } else {
                                if (!chkLog.isChecked()) {
                                    edit.clear();
                                } else {
                                    edit.putString("USERNAME", info[0]);
                                    edit.putString("PASSWORD", password);
                                    edit.putString("NAME", info[3]);
                                    edit.putString("EMAIL", info[4]);
                                    edit.putString("ACCOUNT_TYPE", info[5]);
                                    edit.putBoolean("REMEMBER", chkLog.isChecked());
                                }
                            }
                            edit.apply();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        } else if (s.contains("không")) {
                            lUserLog.setErrorEnabled(true);
                            lUserLog.setError(s);
                        } else if (s.contains("sai")) {
                            lPassLog.setErrorEnabled(true);
                            lPassLog.setError(s);
                        } else {
                            Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    public void goToLogin() {

        TransitionManager.go(loginScene, transition);
    }

    public void goToRegister() {
        TransitionManager.go(registerScene, transition);
    }
}