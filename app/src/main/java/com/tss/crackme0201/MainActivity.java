package com.tss.crackme0201;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private EditText edit_userName,edit_sn;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.unregister));
        edit_userName = findViewById(R.id.edit_username);
        edit_sn = findViewById(R.id.edit_sn);
        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkSN(edit_userName.getText().toString().trim(),edit_sn.getText().toString().trim())){
                    Toast.makeText(MainActivity.this, R.string.unsuccessed, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, R.string.successed, Toast.LENGTH_SHORT).show();
                    btn_register.setEnabled(false);
                    setTitle(R.string.registered);
                }
            }
        });
    }

    private boolean checkSN(String username,String sn){
        if(TextUtils.isEmpty(username)){
            return false;
        }
        if(sn==null||sn.length()!=16){
            return false;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(username.getBytes());
            byte[] bytes = digest.digest();
            String hexStr = toHexString(bytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < hexStr.length(); i+=2) {
                sb.append(hexStr.charAt(i));
            }
            String userSN = sb.toString();
            if(!userSN.equalsIgnoreCase(sn)){
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Convert byte[] to hex string
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String toHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
