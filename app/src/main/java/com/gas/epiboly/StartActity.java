package com.gas.epiboly;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gas.conf.Common;
import com.gas.connector.protocol.LoginHttpProtocol;
import com.gas.database.UserWorker;
import com.gas.entity.User;
import com.gas.ui.common.SuperActivity;
import com.gas.utils.Utils;
import com.google.gson.Gson;

/**
 * Created by Heart on 2015/7/27.
 */
public class StartActity extends SuperActivity{

    private Button bt_login;
    private EditText edit_name;
    private EditText edit_pass;
    private long loginFlag;
    private UserWorker userWorker;
    private long referenceTiem;
    private User user;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, false);
        setContentView(R.layout.activity_start);
        init();
        initListener();
    }

    public void init(){
        bt_login = (Button) findViewById(R.id.bt_login);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_pass= (EditText) findViewById(R.id.edit_pass);
        setOnDismissListener(this);
        LoginHttpProtocol.serviceTime();
        userWorker = new UserWorker(this);

        user = userWorker.getUser();

        if(user.getIsLogin() == 1){
            Common.getInstance().user = user;
            MainActivity.launchActivity(StartActity.this);
        }
}

    public void initListener(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                referenceTiem = System.currentTimeMillis()/1000;
                loginFlag = LoginHttpProtocol.login(StartActity.this, edit_name.getText().toString(),edit_pass.getText().toString());
            }
        });
    }

    @Override
    public void onGeneralSuccess(final String result, long flag) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissProgressDialog();
                User u = new Gson().fromJson(result, User.class);
                u.setIsLogin(1);
                userWorker.addUser(u);
                Common.getInstance().user = u;
                MainActivity.launchActivity(StartActity.this);
                finish();
                Utils.log(" flag", result);
            }
        },System.currentTimeMillis()/1000-referenceTiem>1000?100:1000);

    }

    @Override
    public void onGeneralError(String e, long flag) {
        dismissProgressDialog();
        Utils.toastMsg(this,e);
        Utils.log(" flag error",Utils.decodeUnicode(e));
    }

    @Override
    public void onBackPressed() {
        Utils.log("back", "meiyong ");
        // super.onBackPressed();
    }


}