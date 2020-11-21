package hci.geeks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class LogInDialog extends AppCompatDialogFragment {

    private EditText usernameText;
    private EditText passwordText;

    private LogInDialog.LogInDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
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
            listener = (LogInDialog.LogInDialogListener) context;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public interface LogInDialogListener{
        void logIn(String username, String password);
    }
}
