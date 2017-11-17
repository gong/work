package com.gong.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;


public class CustomUsernamePasswordToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	private boolean mobileLogin;
	
	public CustomUsernamePasswordToken() {
		super();
	}

	public CustomUsernamePasswordToken(String username, String password,
			boolean rememberMe, String host, String captcha, boolean mobileLogin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}
	
}