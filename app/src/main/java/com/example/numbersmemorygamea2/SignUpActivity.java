package com.example.numbersmemorygamea2;

// Import necessary libraries
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Objects;

// Class definition for SignUpActivity
public class SignUpActivity extends AppCompatActivity {

    // Firebase authentication instance
    private FirebaseAuth auth;

    // Firebase Firestore database instance
    private FirebaseFirestore database;

    // Progress dialog for showing loading state
    private ProgressDialog dialog;

    // UI elements
    private EditText signupEmail, signupPassword, username;
    private Button signupButton;
    private TextView loginRedirectText;

    // onCreate method for initializing the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase authentication and Firestore database
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        // Initialize progress dialog
        dialog = new ProgressDialog(this);
        dialog.setMessage("We're creating a new account...");

        // Initialize UI elements
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        username = findViewById(R.id.username_sign);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Set OnClickListener for signupButton
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input values
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String name = username.getText().toString().trim();

                // Validate user inputs
                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                }
                if (pass.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                }
                if (name.isEmpty()) {
                    username.setError("Username cannot be empty");
                } else {
                    // Show progress dialog
                    dialog.show();

                    // Create a User object with name, email, and password
                    final User user1 = new User(name, user, pass);

                    // Use Firebase authentication to create a new user
                    auth.createUserWithEmailAndPassword(user, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // If authentication is successful, get user UID
                                        String uid = Objects.requireNonNull(task.getResult()).getUser().getUid();

                                        // Store username locally
                                        saveUsernameLocal(name);

                                        // Store email and password in Firebase Firestore database
                                        database.collection("users").document(uid).set(user1)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            // Dismiss progress dialog
                                                            dialog.dismiss();
                                                            // Show success message and redirect to LoginActivity
                                                            Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                                            finish();
                                                        } else {
                                                            // Dismiss progress dialog
                                                            dialog.dismiss();
                                                            // Show error message
                                                            Toast.makeText(SignUpActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });

        // Set OnClickListener for loginRedirectText
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redirect to LoginActivity
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    // Method to save username locally in SQLite database
    private void saveUsernameLocal(String username) {
        ScoreDbHelper dbHelper = new ScoreDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ScoreContract.ScoreEntry.COLUMN_NAME, username);
        values.put(ScoreContract.ScoreEntry.COLUMN_SCORE, 0); // Default score is set to 0

        db.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, values);
        db.close();
    }
}
