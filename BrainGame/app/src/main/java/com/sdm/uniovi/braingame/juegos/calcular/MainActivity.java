package com.sdm.uniovi.braingame.juegos.calcular;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.sdm.uniovi.braingame.R;

/**
 * Created by luism_000 on 11/11/2015.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calcular_activity_main);


        WebView webViewFormula = (WebView)findViewById(R.id.webViewFormula);
        webViewFormula.loadUrl("file:///android_assets/formula/formula.html");


    }
}
