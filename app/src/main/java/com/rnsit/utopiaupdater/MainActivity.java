package com.rnsit.utopiaupdater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {
    private Button uploadBtn,chooseIconBtn;
    private final int ICON_INTENT = 1;
    private ImageView iconIv;
    private Uri imageIconURL;
    private ProgressDialog mProgressDialog;
    private Uri firebaseImageIconURL;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private Context context;
    private EditText postDetail;
    private PostViewObject postViewObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadBtn = (Button)findViewById(R.id.uploadBtn);
        iconIv = (ImageView) findViewById(R.id.iconIv);
        mProgressDialog = new ProgressDialog(this);
        postDetail = (EditText)  findViewById(R.id.postDetail);
        context = this;

        chooseIconBtn = (Button)findViewById(R.id.chooseIconBtn);

        chooseIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                startActivityForResult(intent,ICON_INTENT);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadFunction();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ICON_INTENT && resultCode == RESULT_OK) {
            imageIconURL = data.getData();
            iconIv.setImageURI(imageIconURL);
        }
    }

    private void uploadFunction(){
        uploadBtn.setEnabled(false);
        mProgressDialog.setMessage("Uploading...");
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mStorageRef
                .child("Images/"+ UUID.randomUUID().toString())
                .putFile(imageIconURL)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        firebaseImageIconURL = taskSnapshot.getDownloadUrl();
                        updateDatabase();

                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context,exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        mProgressDialog.setProgress((int)progress);
                    }
                });

    }

    private void updateDatabase() {

        final String datetime = new SimpleDateFormat("ss.mm.HH.dd.MM.yyyy").format(new Date());
        final String timeStamp = datetime.replace(".","");

        postViewObject = new PostViewObject();

        postViewObject.setTimeStamp("-"+timeStamp);
        postViewObject.setPostDetail(postDetail.getText().toString());
        postViewObject.setImagePostURL(firebaseImageIconURL.toString());

        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatabaseRef.push().setValue(postViewObject);
                mProgressDialog.dismiss();
                Snackbar.make(findViewById(R.id.relativeLayout), "Uploaded", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
