package com.rnsit.utopiaupdater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rnsit.utopiaupdater.AdapterObjects.CFLObject;
import com.rnsit.utopiaupdater.AdapterObjects.SportsObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener {
    private EditText eventName1,firstName1,secondName1;
    private EditText eventName2,subHead;

    private Button buttonSubmit1,buttonSubmit2;

    private Spinner eventTypeSpinner1,firstTeamSpinner1,secondTeamSpinner1;
    private Spinner firstTeamSpinner2,secondTeamSpinner2,winTeamSpinnner2;
    private String eventTypeName1,firstTeamName1,secondTeamName1;
    private String firstTeamName2,secondTeamName2,winTeamName2;

    private FirebaseFirestore db;

    private CFLObject cflObject;
    private SportsObject sportsObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        eventName1 = (EditText)findViewById(R.id.eventName1);
        firstName1 = (EditText)findViewById(R.id.firstName1);
        secondName1 = (EditText)findViewById(R.id.secondName1);

        eventName2 = (EditText)findViewById(R.id.eventName2);
        subHead = (EditText)findViewById(R.id.subHead);

        eventName2 = (EditText)findViewById(R.id.eventName2);

        buttonSubmit1 = (Button)findViewById(R.id.buttonSubmit1);
        buttonSubmit2 = (Button)findViewById(R.id.buttonSubmit2);

        eventTypeSpinner1 = (Spinner) findViewById(R.id.eventTypeSpinner1);
        firstTeamSpinner1 = (Spinner) findViewById(R.id.firstTeamSpinner1);
        secondTeamSpinner1 = (Spinner) findViewById(R.id.secondTeamSpinner1);

        firstTeamSpinner2 = (Spinner) findViewById(R.id.firstTeamSpinner2);
        secondTeamSpinner2 = (Spinner) findViewById(R.id.secondTeamSpinner2);
        winTeamSpinnner2 = (Spinner) findViewById(R.id.winTeamSpinnner2);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.event_type, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter6 = ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_dropdown_item);

        eventTypeSpinner1.setAdapter(adapter3);
        firstTeamSpinner1.setAdapter(adapter1);
        secondTeamSpinner1.setAdapter(adapter2);

        firstTeamSpinner2.setAdapter(adapter4);
        secondTeamSpinner2.setAdapter(adapter5);
        winTeamSpinnner2.setAdapter(adapter6);


        eventTypeSpinner1.setOnItemSelectedListener(this);
        firstTeamSpinner1.setOnItemSelectedListener(this);
        secondTeamSpinner1.setOnItemSelectedListener(this);

        firstTeamSpinner2.setOnItemSelectedListener(this);
        secondTeamSpinner2.setOnItemSelectedListener(this);
        winTeamSpinnner2.setOnItemSelectedListener(this);

        buttonSubmit1.setOnClickListener(this);
        buttonSubmit2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        String NameOfEvent1 = "",NameOfFirst1="",NameOfSecond1="";
        String NameOfEvent2 = "",NameOfSubHead2="";

        switch (v.getId()){

            case R.id.buttonSubmit1:
                NameOfEvent1 = eventName1.getText().toString();
                NameOfFirst1 = firstName1.getText().toString();
                NameOfSecond1 = secondName1.getText().toString();

                if(!NameOfEvent1.equals("") && !NameOfFirst1.equals("") && !NameOfSecond1.equals("")){
                    submitTypeResult(NameOfEvent1,NameOfFirst1,NameOfSecond1);
                }
                else {
                    Snackbar.make(findViewById(R.id.linearLayout),"Some fields are Empty", Snackbar.LENGTH_SHORT).show();
                }
                break;

            case R.id.buttonSubmit2:
                NameOfEvent2 = eventName2.getText().toString();
                NameOfSubHead2 = subHead.getText().toString();

                if(!NameOfEvent2.equals("") && !NameOfSubHead2.equals("")){
                    submitSportsResult(NameOfEvent2,NameOfSubHead2);
                }
                else {
                    Snackbar.make(findViewById(R.id.linearLayout),"Some fields are Empty", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void submitTypeResult(String NameOfEvent,String NameOfFirst,String NameOfSecond) {
        final String datetime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        final String timeStamp = datetime.replace(".","");
        eventName1.setText("");
        firstName1.setText("");
        secondName1.setText("");

        cflObject = new CFLObject();
        cflObject.setTimeStamp(Long.parseLong(timeStamp));
        cflObject.setEventName(NameOfEvent);
        cflObject.setFirstName(NameOfFirst);
        cflObject.setSecondName(NameOfSecond);
        cflObject.setFirstTeam(firstTeamName1);
        cflObject.setSecondTeam(secondTeamName1);

        db = FirebaseFirestore.getInstance();
        db.collection(eventTypeName1)
                .add(cflObject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar.make(findViewById(R.id.linearLayout), "Submitted", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.linearLayout),e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    private void submitSportsResult(String NameOfEvent2, String NameOfSubHead2) {
        final String datetime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        final String timeStamp = datetime.replace(".","");
        eventName2.setText("");
        subHead.setText("");

        sportsObject = new SportsObject();
        sportsObject.setTimeStamp(Long.parseLong(timeStamp));
        sportsObject.setEventName(NameOfEvent2);
        sportsObject.setSubHead(NameOfSubHead2);
        sportsObject.setTeamName1(firstTeamName2);
        sportsObject.setTeamName2(secondTeamName2);
        sportsObject.setWinnerName(winTeamName2);

        db = FirebaseFirestore.getInstance();
        db.collection("Sports")
                .add(sportsObject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar.make(findViewById(R.id.linearLayout), "Submitted", Snackbar.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.linearLayout),e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.eventTypeSpinner1:
                eventTypeName1 = parent.getSelectedItem().toString();
                break;
            case R.id.firstTeamSpinner1:
                firstTeamName1 = parent.getSelectedItem().toString();
                break;
            case R.id.secondTeamSpinner1:
                secondTeamName1 = parent.getSelectedItem().toString();
                break;

            case R.id.firstTeamSpinner2:
                firstTeamName2 = parent.getSelectedItem().toString();
                break;
            case R.id.secondTeamSpinner2:
                secondTeamName2 = parent.getSelectedItem().toString();
                break;
            case R.id.winTeamSpinnner2:
                winTeamName2 = parent.getSelectedItem().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
