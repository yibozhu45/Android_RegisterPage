package com.example.registerpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int SELECT_PICTURE = 1;

    private ImageView imageView;
    private EditText edtTxtName, edtTxtEmail, edtTxtPassword, edtTxtPasswordRepeat;
    private Button btnPickImage, btnRegister;
    private TextView txtWarnName, txtWarnEmail, txtWarnPassword, txtWarnPasswordRepeat;
    private Spinner countriesSpinner;
    private RadioGroup rgGender;
    private CheckBox agreementCheck;
    private ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        
        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Yet to talk about", Toast.LENGTH_SHORT).show();
                openGallery();
            }
        });
        
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRegister();
            }
        });
    }

    //choose image from online gallery
    //http://androidbitmaps.blogspot.com/2015/04/loading-images-in-android-part-iii-pick.html
    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE) {
            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }


    private void initRegister() {
        Log.d(TAG, "initRegidter: Started");
        
        //do something write in a method
        if (validateData()) {
            if (agreementCheck.isChecked())
            {
                showResult();
            }else
            {
                Toast.makeText(this, "You have to agree the license", Toast.LENGTH_SHORT).show();
            }
            
        }
    }

    private void showResult() {
        Log.d(TAG, "showSnackBar: Started");

        txtWarnName.setVisibility(View.GONE);
        txtWarnEmail.setVisibility(View.GONE);
        txtWarnPassword.setVisibility(View.GONE);
        txtWarnPasswordRepeat.setVisibility(View.GONE);

        String name = edtTxtName.getText().toString();
        String email = edtTxtEmail.getText().toString();
        String country = countriesSpinner.getSelectedItem().toString();
        String gender = "";
        switch (rgGender.getCheckedRadioButtonId())
        {
            case R.id.rbMale:
                gender = "Male";
                break;
            case R.id.rbFemale:
                gender = "Female";
                break;
            case R.id.rbOther:
                gender = "Other";
                break;
        }

        String text = "Name: " + name + "\n" + "Email: " + email + "\n" + "Country: " + country + "\n" + "Gender: " + gender + "\n";

        //customize snackbar
        Snackbar snackbar = Snackbar.make(parent, text, Snackbar.LENGTH_INDEFINITE)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edtTxtName.setText("");
                        edtTxtEmail.setText("");
                        edtTxtPassword.setText("");
                        edtTxtPasswordRepeat.setText("");
                    }
                });
        View snackbarView = snackbar.getView();
        TextView snackTextView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);

        snackTextView.setMaxLines(4);
        snackbar.show();

    }

    private boolean validateData() {
        Log.d(TAG, "validateData: started");
        Boolean fName, fEmail, fPassword, fPasswordRepeat;
        fName = fEmail = fPassword = fPasswordRepeat = true;
        if (edtTxtName.getText().toString().equals("")) {
            txtWarnName.setText("Enter your Name");
            fName = false;
        }

        if(edtTxtEmail.getText().toString().equals(""))
        {
            txtWarnEmail.setText("Enter your Email");
            fEmail = false;
        }

        if (edtTxtPassword.getText().toString().equals(""))
        {
            txtWarnPassword.setText("Enter your Password");
            fPassword = false;
        }

        if (edtTxtPasswordRepeat.getText().toString().equals(""))
        {
            txtWarnPasswordRepeat.setText("Re-Enter your Password");
            fPasswordRepeat = false;
        }

        if (!edtTxtPassword.getText().toString().equals(edtTxtPasswordRepeat.getText().toString()))
        {
            txtWarnPasswordRepeat.setText("Password doesn't match");
            fPasswordRepeat = false;
        }

        if (fName && fEmail && fPassword && fPasswordRepeat)
        {
            return true;
        }
        else
        {
            if (!fName)
            {
                txtWarnName.setVisibility(View.VISIBLE);
            }
            else
            {
                txtWarnName.setVisibility(View.GONE);
            }

            if (!fEmail)
            {
                txtWarnEmail.setVisibility(View.VISIBLE);
            }
            else
            {
                txtWarnEmail.setVisibility(View.GONE);
            }

            if (!fPassword)
            {
                txtWarnPassword.setVisibility(View.VISIBLE);
            }
            else
            {
                txtWarnPassword.setVisibility(View.GONE);
            }

            if (!fPasswordRepeat)
            {
                txtWarnPasswordRepeat.setVisibility(View.VISIBLE);
            }
            else
            {
                txtWarnPasswordRepeat.setVisibility(View.GONE);
            }
            return false;
        }
    }

    private void initView() {
        Log.d(TAG, "initViews: Started");

        imageView = findViewById(R.id.imgProfile);
        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtEmail = findViewById(R.id.edtTxtEmail);
        edtTxtPassword = findViewById(R.id.edtTxtPassword);
        edtTxtPasswordRepeat = findViewById(R.id.edtTxtPasswordRepeat);

        btnPickImage = findViewById(R.id.btnPickImage);
        btnRegister = findViewById(R.id.btnRegister);

        txtWarnName = findViewById(R.id.txtWarnName);
        txtWarnEmail = findViewById(R.id.txtWarnEmail);
        txtWarnPassword = findViewById(R.id.txtWarnPassword);
        txtWarnPasswordRepeat = findViewById(R.id.txtWarnPasswordRepeat);

        countriesSpinner = findViewById(R.id.spinnerCountry);
        rgGender = findViewById(R.id.rgGender);
        agreementCheck = findViewById(R.id.agreementCheck);
        parent = findViewById(R.id.parent);
    }
}
