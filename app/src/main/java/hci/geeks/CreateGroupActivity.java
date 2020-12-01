package hci.geeks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class CreateGroupActivity extends AppCompatActivity {

    TextView creategroupTV;
    EditText inputGroupET, inputSubjectET;
    Button createB, cancelB;
    Switch privateS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Geeks");
        setContentView(R.layout.activity_create_group);
        creategroupTV = findViewById(R.id.creategroupTextview);
        inputGroupET = findViewById(R.id.inputGroupEditText);
        inputSubjectET = findViewById(R.id.inputSubjectEditText);
        createB = findViewById(R.id.createButton);
        cancelB = findViewById(R.id.cancelButton);
        privateS = findViewById(R.id.privateSwitch);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.settingsMenu){
            Toast.makeText(this, "Settings Cliked!", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.logOutMenu) {
            new AlertDialog.Builder(this)
                    .setTitle("Log Out")
                    .setMessage("Are you sure you want to Log Out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }})
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void createClicked(View view){
        Intent intent = new Intent(this, AvailableGroupsActivity.class);
        intent.putExtra("Name", inputGroupET.getText().toString());
        intent.putExtra("Subject", inputSubjectET.getText().toString());
        if(privateS.isChecked()){
            intent.putExtra("Privacy", "Private");
        }
        else{
            intent.putExtra("Privacy", "Public");
        }
        intent.putExtra("Members", 1);
        setResult(2, intent);
        finish();
    }

    public void cancelClicked(View view){
        Intent intent = new Intent(this, AvailableGroupsActivity.class);
        setResult(3, intent);
        finish();
    }
}