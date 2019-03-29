package com.rnsit.utopiaupdater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.google.android.material.snackbar.Snackbar;
import com.rnsit.utopiaupdater.AdapterObjects.PostViewObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


public class PostActivity extends AppCompatActivity {
    private Button uploadBtn,chooseIconBtn;
    private final int ICON_INTENT = 1;
    private ImageView iconIv;
    private Uri imageIconURL;
    private ProgressDialog mProgressDialog;
    private Uri firebaseImageIconURL;
    private StorageReference mStorageRef;
    private FirebaseFirestore db;
    private Context context;
    private EditText postDetail;
    private PostViewObject postViewObject;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        uploadBtn = (Button)findViewById(R.id.uploadBtn);
        iconIv = (ImageView) findViewById(R.id.iconIv);
        mProgressDialog = new ProgressDialog(this);
        postDetail = (EditText)  findViewById(R.id.postDetail);
        context = this;

        chooseIconBtn = (Button)findViewById(R.id.chooseIconBtn);

        chooseIconBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!postDetail.getText().toString().equals(""))
                    uploadFunction();
                else
                    Toast.makeText(context,"Post text is empty",Toast.LENGTH_SHORT).show();
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
        final StorageReference ref = mStorageRef.child("Images/"+ UUID.randomUUID().toString());
        UploadTask uploadTask;
        uploadTask = ref.putFile(imageIconURL);


        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                mProgressDialog.setProgress((int)progress);
            }
        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    firebaseImageIconURL = task.getResult();
                    updateDatabase();
                }
            }
        });

    }

    private void updateDatabase() {

        final String datetime = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        final String timeStamp = datetime.replace(".","");

        postViewObject = new PostViewObject();

        postViewObject.setTimeStamp(Long.parseLong(timeStamp));
        postViewObject.setPostDetail(postDetail.getText().toString());
        postViewObject.setImagePostURL(firebaseImageIconURL.toString());

        db = FirebaseFirestore.getInstance();
        db.collection("Posts")
                .add(postViewObject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        mProgressDialog.dismiss();
                        Snackbar.make(findViewById(R.id.relativeLayout), "Uploaded", Snackbar.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.relativeLayout),e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
}
