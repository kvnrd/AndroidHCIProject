package hci.geeks;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Dialog.DialogListener{

    Button signupButton, loginButton;

    UsersDatabaseHelper db;

    UsersManager users = new UsersManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signupButton = findViewById(R.id.SignUPButton);
        loginButton = findViewById(R.id.LogInButton);

        db = new UsersDatabaseHelper(this);

        users.setUsers(db.allUsers());
        if(users.getUsers() == null){
            users.newList();
        }
    }

    public void signUpClicked(View view){
        Dialog signUpDialog = new Dialog(false);
        signUpDialog.show(getSupportFragmentManager(), "Sign Up Dialog");
    }

    public void logInClicked(View view){
        Dialog logInDialog = new Dialog(true);
        logInDialog.show(getSupportFragmentManager(), "Log In Dialog");
    }

    @Override
    public void logIn(String username, String password) {
        if(db.checkUser(username, password)){
            Toast.makeText(this, "Log In Succesful!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AvailableGroupsActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Username or Password Incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void signUp(String firstName, String lastName, String email, String usernameS, String passwordS) {
        User user = new User(firstName, lastName, email, usernameS, passwordS);
        user.setId(users.getUsers().size());
        boolean status = db.addUser(user);
        if(status){
            Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
            users.addUser(user);
        }
        else{
            Toast.makeText(this, "Sign Up Failed!", Toast.LENGTH_SHORT).show();
        }
    }
}
