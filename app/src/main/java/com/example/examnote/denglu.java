package com.example.examnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class denglu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUsername = findViewById(R.id.et_username);
                EditText etPassword = findViewById(R.id.et_password);

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();


                if ("qwert".equals(username) && "123456".equals(password)) {
                    // 登录成功，跳转到下一个界面
                    Intent intent = new Intent(denglu.this, Main.class);
                    startActivity(intent);
                } else {
                    // 登录失败，显示错误提示
                    Toast.makeText(denglu.this, "请输入正确的账号密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}