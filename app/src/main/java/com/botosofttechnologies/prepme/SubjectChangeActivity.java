package com.botosofttechnologies.prepme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubjectChangeActivity extends AppCompatActivity {

    Button finished;
    CheckBox English, Maths, Physics, Chemistry, Biology, Commerce, Accounting, Government, Economics, Literature, Geography, Agric, History, Crk;
    TextView done_text, selectSubject;
    ImageView done;
    int checkAccumulator;
    String $paper1, $paper2, $paper3, $paper4 ;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference users = database.getReference().child("users");

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    static FirebaseUser User = mAuth.getCurrentUser();
    static final String userId = User.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_change);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initUi();
    }


    private void initUi(){
        finished = (Button) findViewById(R.id.finished);

        selectSubject = (TextView) findViewById(R.id.selectSubject);
        English = (CheckBox) findViewById(R.id.English);
        Maths = (CheckBox) findViewById(R.id.Maths);
        Physics = (CheckBox) findViewById(R.id.Physics);
        Chemistry = (CheckBox) findViewById(R.id.Chemistry);
        Biology = (CheckBox) findViewById(R.id.Biology);
        Commerce = (CheckBox) findViewById(R.id.Commerce);
        Accounting = (CheckBox) findViewById(R.id.Accounting);
        Government = (CheckBox) findViewById(R.id.Government);
        Economics = (CheckBox) findViewById(R.id.Economics);
        Literature = (CheckBox) findViewById(R.id.Literature);
        Geography = (CheckBox) findViewById(R.id.Geography);
        Agric = (CheckBox) findViewById(R.id.Agric);
        History = (CheckBox) findViewById(R.id.History);
        Crk = (CheckBox) findViewById(R.id.Crk);
        done_text = (TextView) findViewById(R.id.done_text);
        done = (ImageView) findViewById(R.id.done);


        done_text.setVisibility(View.INVISIBLE);
        done.setVisibility(View.INVISIBLE);


        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAccumulator = 0;
                countCheck();
                if(!English.isChecked()){
                    Toast.makeText(SubjectChangeActivity.this, "English is a compulsory subject", Toast.LENGTH_SHORT).show();
                }else if(checkAccumulator != 4){
                    Toast.makeText(SubjectChangeActivity.this,  "Select only four subject combinations", Toast.LENGTH_SHORT).show();
                }else if(checkAccumulator == 4){

                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String subscriptionStatus = String.valueOf(dataSnapshot.child(userId).child("subscription").getValue());

                            if(subscriptionStatus.equals("true")){
                                String subscriptionDate = String.valueOf(dataSnapshot.child(userId).child("subscription_date").getValue());
                                String subjectChangeCount = String.valueOf(dataSnapshot.child(userId).child("subject_change_count").getValue());

                                Date currentDate = (Date) java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("Africa/Lagos")).getTime();
                                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                final String date = dateFormat.format(currentDate);

                                if(date.split("-")[1].equals(subscriptionDate.split("-")[1]) && Integer.parseInt(subjectChangeCount) >= 2){
                                    AlertDialog alertDialog = new AlertDialog.Builder(SubjectChangeActivity.this).create();
                                    alertDialog.setTitle("OOps...");
                                    alertDialog.setMessage("You can only change your subject twice in 30 days" );
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();

                                                }
                                            });

                                }else if(Integer.parseInt(subjectChangeCount) >= 2){

                                    AlertDialog alertDialog = new AlertDialog.Builder(SubjectChangeActivity.this).create();
                                    alertDialog.setTitle("OOps...");
                                    alertDialog.setMessage("You can only change your subject twice in 30 days" );
                                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();

                                                }
                                            });
                                }else{
                                    done.setVisibility(View.VISIBLE);
                                    done_text.setVisibility(View.VISIBLE);

                                    selectSubject.setVisibility(View.INVISIBLE);
                                    English.setVisibility(View.INVISIBLE);
                                    Maths.setVisibility(View.INVISIBLE);
                                    Physics.setVisibility(View.INVISIBLE);
                                    Chemistry.setVisibility(View.INVISIBLE);
                                    Biology.setVisibility(View.INVISIBLE);
                                    Commerce.setVisibility(View.INVISIBLE);
                                    Accounting.setVisibility(View.INVISIBLE);
                                    Government.setVisibility(View.INVISIBLE);
                                    Economics.setVisibility(View.INVISIBLE);
                                    Literature.setVisibility(View.INVISIBLE);
                                    Geography.setVisibility(View.INVISIBLE);
                                    Agric.setVisibility(View.INVISIBLE);
                                    History.setVisibility(View.INVISIBLE);
                                    Crk.setVisibility(View.INVISIBLE);
                                    finished.setVisibility(View.INVISIBLE);


                                    getChecked();

                                    users.child(userId).child("subjects").child("0").setValue($paper1);
                                    users.child(userId).child("subjects").child("1").setValue($paper2);
                                    users.child(userId).child("subjects").child("2").setValue($paper3);
                                    users.child(userId).child("subjects").child("3").setValue($paper4);
                                }

                            }else{

                                AlertDialog alertDialog = new AlertDialog.Builder(SubjectChangeActivity.this).create();
                                alertDialog.setTitle("OOps...");
                                alertDialog.setMessage("You can't change your subject until you have an active subscription" );
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();

                                            }
                                        });

                                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Subscribe Now",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(SubjectChangeActivity.this, PaymentActivity.class);
                                                startActivity(intent);

                                            }
                                        });
                                alertDialog.show();

                            }

                        }

                       @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            }
        });





    }


    private boolean countCheck() {

        CheckBox[] checkBoxes = {English, Maths, Physics, Chemistry, Biology, Commerce, Accounting, Government, Economics, Literature, Geography, Agric, History, Crk};
        boolean isChecked = false;
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                checkAccumulator += 1;
                isChecked = checkAccumulator == 4;
            } else {
                isChecked = false;
            }
        }
        return isChecked;
    }


    private void getChecked() {
        CheckBox[] checkBoxes = {English, Maths, Physics, Chemistry, Biology, Commerce, Accounting, Government, Economics, Literature, Geography, Agric, History, Crk};
        int i = 1;
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                String subject = checkBox.getText().toString();
                if(i == 1){
                    $paper1 = "English Language";
                    i++;
                }else if(i == 2){
                    $paper2 = subject;
                    i++;
                }else if(i == 3){
                    $paper3 = subject;
                    i++;
                }else if(i == 4){
                    $paper4 = subject;
                    i++;
                }

            }
        }

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SubjectChangeActivity.this, NavigationActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                Intent intent = new Intent(SubjectChangeActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

}
