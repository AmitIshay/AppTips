package com.example.myapplication_tips;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication_tips.databinding.ActivityPdfTableBinding;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CalcTipsSecondActivity extends AppCompatActivity {

    private MaterialButton buttonCalc;
    private TextInputEditText creditInput;
    private TextInputEditText tipsInput;
    private MaterialTextView finalText;
    private MaterialButton buttonShare;
    private ListView finalListViewWorkers;
    AppManager appManager;
    int checkNum;
    double totalHours;
    ArrayList<Worker> workers;
    String TAG = "Amit_Ishay";
    private ImageView addPhotoBtn;
    private ImageView imageView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private CollectionReference collectionReference = db.collection("Shift");
    private String  currentUserId;
    private String currentUserName;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    ActivityResultLauncher<String> mTakePhoto;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_tips_second);
        findViews();
        Intent i = getIntent();
        totalHours = Double.valueOf(i.getStringExtra("message"));
        workers = (ArrayList<Worker>)getIntent().getSerializableExtra("Array");
        checkNum = Integer.parseInt(i.getStringExtra("checknum"));
        buttonShare.setVisibility(View.INVISIBLE);
        if (checkNum == 0){
            creditInput.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            addPhotoBtn.setVisibility(View.INVISIBLE
            );
        }
        appManager = new AppManager(this);
        buttonCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNum == 1)
                    buttonShare.setVisibility(View.VISIBLE);
                int getInput= Integer.valueOf(tipsInput.getText().toString());
                int getInputOne = 0;
                if (checkNum == 1) {
                    getInputOne = Integer.valueOf(creditInput.getText().toString());
                }
                appManager.calcParms(totalHours, getInput, getInputOne, checkNum, workers, view);
            }

        });
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createPDFandShare();
                saveShift();
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        if (user != null){
            currentUserId = user.getUid();
            currentUserName = user.getDisplayName();
        }
        mTakePhoto = registerForActivityResult(
                new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {
                        imageView.setImageURI(result);
                        imageUri = result;
                    }
                }
        );
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTakePhoto.launch("image/*");
            }
        });
    }
    private void saveShift(){
        //String moneyCredit = finalText.getText().toString().trim();
        int moneyCredit = appManager.getMoneyCredit();
        int moneyCash = appManager.getMoneyCash();
        int moneySixINS = appManager.getMoneySixINS();
        int moneyMinusSixIns = appManager.getMoneyMinusSixINS();
        int moneyPerHour = appManager.getMoneyPerHour();
        double totalHours = appManager.getTotalHours();
        int moneyTips = appManager.getMoneyTips();
        ArrayList<Worker> workerArray = appManager.getWorkersArray();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        if (imageUri != null){
            final StorageReference filePath = storageReference.child("shifts_images")
                    .child("my_image_"+ Timestamp.now().getSeconds());
            filePath.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    Shift shift = new Shift();
                                    shift.setMoneyCredit(moneyCredit);
                                    shift.setMoneyCash(moneyCash);
                                    shift.setMoneySixINS(moneySixINS);
                                    shift.setImageUrl(imageUrl);
                                    shift.setUserName(currentUserName);
                                    shift.setUserId(currentUserId);
                                    shift.setMoneyMinusSixIns(moneyMinusSixIns);
                                    shift.setMoneyPerHour(moneyPerHour);
                                    shift.setTotalHours(totalHours);
                                    //shift.setTimeNow(currentDate);
                                    shift.setMoneyTips(moneyTips);
                                    shift.setWorkerArray(workers);
                                    Log.d("gggggggggggggggg",workers.toString());
                                    collectionReference.add(shift)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    //Intent i = new Intent(CalcTipsSecondActivity.this, PdfTableActivity.class);
                                                    //startActivity(i);
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(CalcTipsSecondActivity.this, "Failed : "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CalcTipsSecondActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user = firebaseAuth.getCurrentUser();
    }

    public void createPDF(OutputStream ref_outst) {
        PdfDocument document = new PdfDocument();
        //A4 size
        int PDF_PAGE_WIDTH = 595;
        int PDF_PAGE_HEIGHT = 842;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(PDF_PAGE_WIDTH,PDF_PAGE_HEIGHT,1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        int desire_text_size=20;
        paint.setTextSize(desire_text_size);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String text= finalText.getText().toString().trim();

        float cord_x_text = 30;
        float cord_y_text = 50;
        canvas.drawText(currentDate +" "+ currentTime,cord_x_text,cord_y_text,paint);
        canvas.drawText(text, cord_x_text, 110, paint);
        int num = 230;
        for (int i = 0; i < workers.size(); i++) {
            String text5 = workers.get(i).toString().trim();
            canvas.drawText(text5, cord_x_text, num, paint);
            num += 30;
        }
        document.finishPage(page);
        try {
            // write the document content
            document.writeTo(ref_outst);

            // close the document
            document.close();

            Toast.makeText(this, "PDF is saved", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void createPDFandShare() {
        OutputStream outst;
        try {
            //=== scoped storage is support after Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver contentResolver = getContentResolver();
                ContentValues contentValues = new ContentValues();

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
                LocalDateTime now = LocalDateTime.now();
                String date_suffix="_"+dtf.format(now);
                Log.d(TAG,dtf.format(now));

                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Amit"+date_suffix + ".pdf");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_DOCUMENTS + File.separator + "AmitFolder");
                Uri pdfUri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues);

                outst = contentResolver.openOutputStream(Objects.requireNonNull(pdfUri));
                Objects.requireNonNull(outst);

                //code to create pdf
                createPDF(outst);

                //=== now intent to share image
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, pdfUri);
                startActivity(Intent.createChooser(share, "Share PDF"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MaterialTextView getFinalText() {
        return finalText;
    }

    public ListView getFinalListViewWorkers() {
        return finalListViewWorkers;
    }


    private void findViews() {
        buttonCalc = findViewById(R.id.buttonCalc);
        creditInput = findViewById(R.id.creditInput);
        tipsInput = findViewById(R.id.tipsInput);
        finalText = findViewById(R.id.finalText);
        buttonShare = findViewById(R.id.buttonShare);
        finalListViewWorkers = findViewById(R.id.finalListViewWorkers);
        addPhotoBtn = findViewById(R.id.postCameraButton);
        imageView = findViewById(R.id.post_imageView);

    }
}