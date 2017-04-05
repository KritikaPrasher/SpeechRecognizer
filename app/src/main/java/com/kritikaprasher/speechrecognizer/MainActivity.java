package com.kritikaprasher.speechrecognizer;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecognitionListener{

    TextView txtOutput;
    Button btn;

    SpeechRecognizer speechRecognizer;

    TextToSpeech tts;
    ProgressDialog progressDialog;

 void initViews(){
     txtOutput=(TextView)findViewById(R.id.textoutput);
     btn=(Button)findViewById(R.id.button);
     btn.setOnClickListener(this);

     speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);

     speechRecognizer.setRecognitionListener(this);

     progressDialog=new ProgressDialog(this);
     progressDialog.setMessage("Listening...");
     progressDialog.setCancelable(false);

     tts= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
         @Override
         public void onInit(int i) {
             if (i==TextToSpeech.SUCCESS){
                 Toast.makeText(MainActivity.this, "TTS is initialized", Toast.LENGTH_SHORT).show();
             }

         }
     });



 }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==R.id.button) {
            speechRecognizer.startListening(RecognizerIntent.getVoiceDetailsIntent(this));

        }
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {
         progressDialog.show();
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
           progressDialog.dismiss();
    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> resultlist = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (resultlist != null && resultlist.size() > 0) {
            String output = resultlist.get(0);
            txtOutput.setText(output);

            if (output.toLowerCase().contains("amazing")) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel: 8146739920"));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Please grant permissions in settings", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(i);
                }
            }

        }

    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
