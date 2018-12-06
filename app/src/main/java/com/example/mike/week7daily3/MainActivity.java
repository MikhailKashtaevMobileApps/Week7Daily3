package com.example.mike.week7daily3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.leakcanary.LeakCanary;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());

        setContentView(R.layout.activity_main);

        Timber.plant(new Timber.DebugTree());

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Timber.d("Cancelled some shit__TAG__");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {

                Timber.d("Found some shit__TAG__");
                TextView scanned = findViewById( R.id.tvScannedData );
                scanned.setText( "Scanned: " + result.getContents() );
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void scanBar(View view) {
        new IntentIntegrator(this).initiateScan(); // `this` is the current Activity
    }

    public void loadPDF(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("http://www.pdf995.com/samples/pdf.pdf"), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
