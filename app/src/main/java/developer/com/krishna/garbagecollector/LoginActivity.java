package developer.com.krishna.garbagecollector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import developer.com.krishna.garbagecollector.models.NewUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @BindView(R.id.mobile_num)
    EditText mobile;
    @BindView(R.id.otp)
    Button otpB;
   // @BindView(R.id.otp_code)
   // EditText otpCode;
    //@BindView(R.id.verify)
   // FloatingActionButton verify;
    @BindView(R.id.name)
    EditText name;
    //CircularProgressBar circularProgressBar;
    ProgressBar circularProgressBar;

    int animationDuration;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        circularProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        getSupportActionBar().hide();



         animationDuration = 5500; // 2500ms = 2,5s
         // Default duration = 1500ms


        FirebaseAuth auth;
        auth=FirebaseAuth.getInstance();
        final FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser!=null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));}
        otpB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobile.getText().toString().equals("")){mobile.setError("OOPS!");}
                else if(mobile.getText().toString().length()<10)
                   // Toast.makeText("this","Mobile num length",Toast.LENGTH_SHORT).show();

                {Snackbar.make(findViewById(android.R.id.content), "Invalid number", Snackbar.LENGTH_SHORT).show();}
                else
                {

                    startPhoneNumberVerification("+91"+mobile.getText().toString());
                    circularProgressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Snackbar.make(findViewById(android.R.id.content), "Verification on process",
                            Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                mVerificationInProgress = false;


                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    mobile.setError("Invalid phone number.");

                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                mVerificationId = verificationId;
                /*otpCode.setVisibility(View.VISIBLE);
                verify.show();
                verify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(otpCode.getText().toString().equals("")){otpCode.setError("OOPS!");}
                        else {

                            verifyPhoneNumberWithCode(mVerificationId, otpCode.getText().toString());
                        }
                        }
                });
                     */
            }
        };

    }

    private void startPhoneNumberVerification(String phoneNumber) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks


        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        circularProgressBar.setVisibility(View.INVISIBLE);
        signInWithPhoneAuthCredential(credential);
    }
    private void setUser()
    {
        FirebaseUser user1=mAuth.getCurrentUser();
        FirebaseDatabase.getInstance().getReference("Users")
                .push().setValue(new NewUser(name.getText().toString(),mobile.getText().toString())
        );
        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder()

                .setDisplayName(name.getText().toString()).build();
        try {
            user1.updateProfile(userProfileChangeRequest);
        }
        catch (Exception e) {}
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();
                            setUser();

                            Snackbar.make(findViewById(android.R.id.content), "Welcome",
                                    Snackbar.LENGTH_SHORT).show();
                            circularProgressBar.clearAnimation();

                            startActivity(new Intent(LoginActivity.this,MainActivity.class));



                        } else {


                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }

                        }
                    }
                });
    }
}
