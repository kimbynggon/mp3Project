package com.example.mp3player;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtId;
    private EditText edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setContentView(R.layout.activity_main);

        edtId = (EditText) findViewById(R.id.edtId);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtId.getText().toString();
                String pw = edtPassword.getText().toString();
                if (id.equals("admin") && pw.equals("1234")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("로그인화면", MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("id", id);

                    editor.putString("pw", pw);

                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), MusicPlayMain.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "회원정보가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    edtId.setText("");
                    edtPassword.setText("");
                }
            }
        });
    }

}
