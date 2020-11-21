package hci.geeks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class SignUpDialog extends AppCompatDialogFragment {
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;
    private EditText usernameTextS;
    private EditText passwordTextS;

    private SignUpDialog.SignUpDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
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
                        } catch(Exception e){
                            Toast.makeText(getActivity(), "Fill Out All the Fields!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (SignUpDialog.SignUpDialogListener) context;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface SignUpDialogListener{
        void signUp(String firstName, String lastName, String email, String usernameS, String passwordS);
    }
}
