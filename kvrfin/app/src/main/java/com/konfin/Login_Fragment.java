package com.konfin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.konfin.util.*;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login_Fragment extends Fragment implements OnClickListener {
	private static View view;

	private static EditText userName, password;
	private static Button loginButton;
	private static TextView forgotPassword, signUp;

	private static LinearLayout loginLayout;
	private static Animation shakeAnimation;
	private static FragmentManager fragmentManager;
	private String user;
	public Login_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle args = getArguments();
try {
	user = args.getString("userObject");
}
catch (Exception e)
{

}
		view = inflater.inflate(R.layout.login_layout, container, false);
		initViews();
		setListeners();
		return view;
	}

	// Initiate Views
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();

		userName = (EditText) view.findViewById(R.id.login_emailid);
		password = (EditText) view.findViewById(R.id.login_password);
		if(user!=null)
		{
			try {
				User userObject = new ObjectMapper().readValue(user, User.class);
				userName.setText(userObject.getUserLoginID());
				password.setFocusable(true);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		loginButton = (Button) view.findViewById(R.id.loginBtn);
		forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
		signUp = (TextView) view.findViewById(R.id.createAccount);

		loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

		// Load ShakeAnimation
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);

		// Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			forgotPassword.setTextColor(csl);

			signUp.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	// Set Listeners
	private void setListeners() {
		loginButton.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
		signUp.setOnClickListener(this);

		// Set check listener over checkbox for showing and hiding password

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			checkValidation();
			break;

		case R.id.forgot_password:

			// Replace forgot password fragment with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer,
							new ForgotPassword_Fragment(),
							Utils.ForgotPassword_Fragment).commit();
			break;
		case R.id.createAccount:

			// Replace signup frgament with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer, new SignUp_Fragment(),
							Utils.SignUp_Fragment).commit();
			break;
		}

	}

	// Check Validation before login
	private void checkValidation() {
		// Get email id and password

		String userLoninId = userName.getText().toString();
		String getPassword = password.getText().toString();

		// Check patter for email id
	//	Pattern p = Pattern.compile(Utils.regEx);

		//Matcher m = p.matcher(userId);

		// Check for both field is empty or not
		if (userLoninId.equals("") || userLoninId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0) {
			loginLayout.startAnimation(shakeAnimation);
			new CustomToast().Show_Toast(getActivity(), view,
					"Enter both credentials.");

		}
		// Check if email id is valid or not
//		else if (!m.find())
//			new CustomToast().Show_Toast(getActivity(), view,
//					"Your Email Id is Invalid.");
		// Else do login and do your stuff
		if(userLoninId.equals("Konda"))
		{
			RequestTask requestTask=new RequestTask(KonFinUtils.getUsers(userLoninId));
			Admin_Fragment f = new Admin_Fragment();
			Bundle args = new Bundle();
			try {
				String str_result = requestTask.execute().get();
				JSONObject getResponce = requestTask.postResponce;
				if (getResponce.length() != 0) {
					args.putString("users", getResponce.toString());
					args.putString("status", "exist");
					System.out.println("exist" + getResponce);
				}
				f.setArguments(args);
				fragmentManager
						.beginTransaction()
						.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
						.replace(R.id.frameContainer,
								f,
								Utils.Admin_Fragment).commit();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{

			RequestTask requestTask=new RequestTask(KonFinUtils.getFundsData(userLoninId, getPassword));
			InputParams responseParam=new InputParams();
			try
			{
				String str_result=  requestTask.execute().get();
				Bundle args = new Bundle();
				JSONArray getResponce=requestTask.getResponce;
				if(getResponce.length()!=0) {
					args.putString("portpolio", getResponce.toString());
					args.putString("status", "exist");
					System.out.println("exist"+getResponce);
				}
				else
				{
					args.putString("portpolio", getResponce.toString());
					args.putString("status", "empty");
					System.out.println("empty"+getResponce.length());
				}
					Portpolio_Fragment f = new Portpolio_Fragment();
					f.setArguments(args);
					fragmentManager
							.beginTransaction()
							.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
							.replace(R.id.frameContainer,
									f,
									Utils.Portpolio_Fragment).commit();



			}
			catch(Exception e)
			{
				e.printStackTrace();
			}


			//	finish();
		}


	}



}
