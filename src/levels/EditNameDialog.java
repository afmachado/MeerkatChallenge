package levels;
import meerkatchallenge.main.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EditNameDialog extends DialogFragment implements OnEditorActionListener {

	public interface EditNameDialogListener {
        void onFinishEditDialog(String inputText);
    }

	
    private EditText mEditText;

    public EditNameDialog() {
        // Empty constructor required for DialogFragment
    }

    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
//    	setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black);
    }
    
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//    	View view = inflater.inflate(R.layout.fragment_edit_name, container);
//
//        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
//
//        // Show soft keyboard automatically
//        mEditText.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        mEditText.setOnEditorActionListener(this);
//
//        return view;
//    }
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    	AlertDialog.Builder builder;
    	AlertDialog alertDialog;

    	LayoutInflater inflater = getActivity().getLayoutInflater();
    	View layout = inflater.inflate(R.layout.fragment_edit_name, null);
    	
    	builder = new AlertDialog.Builder(getActivity());
    	builder.setView(layout);
    	alertDialog = builder.create();
    	
    	return alertDialog;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            EditNameDialogListener activity = (EditNameDialogListener) getActivity();
            activity.onFinishEditDialog(mEditText.getText().toString());
            
            this.dismiss();
            return true;
        }
        return false;
    }

}