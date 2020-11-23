package hci.geeks;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog extends AppCompatDialogFragment {

    private EditText usernameText;
    private EditText passwordText;

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;
    private EditText usernameTextS;
    private EditText passwordTextS;

    private Dialog.DialogListener listener;

    private boolean flag;

    public Dialog(boolean flag){
        this.flag = flag;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        if(flag == true){

            View view = inflater.inflate(R.layout.log_in_dialog, null);

            usernameText = view.findViewById(R.id.dialogUsername);
            passwordText = view.findViewById(R.id.dialogPassword);

            builder.setView(view).setTitle("Log In")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }})
                    .setPositiveButton("Log In", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try{
                                String username = usernameText.getText().toString();
                                String password = passwordText.getText().toString();
                                listener.logIn(username, password);
                            } catch(Exception e){
                                Toast.makeText(getActivity(), "Fill Out All the Fields!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{

            View view = inflater.inflate(R.layout.sign_up_dialog, null);

            firstNameText = view.findViewById(R.id.dialogSFirstName);
            lastNameText = view.findViewById(R.id.dialogLastName);
            emailText = view.findViewById(R.id.dialogEmail);
            usernameTextS = view.findViewById(R.id.dialogSUsername);
            passwordTextS = view.findViewById(R.id.dialogSPassword);

            builder.setView(view).setTitle("Sign Up")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }})
                    .setPositiveButton("Sign Up", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try{
                                String firstName = firstNameText.getText().toString();
                                String lastName = lastNameText.getText().toString();
                                String email = emailText.getText().toString();
                                String usernameS = usernameTextS.getText().toString();
                                String passwordS = passwordTextS.getText().toString();
                                listener.signUp(firstName, lastName, email, usernameS, passwordS);
                            } catch(Exception e){
                                Toast.makeText(getActivity(), "Fill Out All the Fields!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (Dialog.DialogListener) context;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface DialogListener{
        void logIn(String username, String password);
        void signUp(String firstName, String lastName, String email, String usernameS, String passwordS);
    }
}
