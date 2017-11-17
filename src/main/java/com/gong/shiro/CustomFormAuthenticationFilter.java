package com.gong.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;



import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;


public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;
    @Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = request.getRemoteHost();
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);
		return new CustomUsernamePasswordToken(username, password, rememberMe, host,captcha,mobile);
}
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest= (HttpServletRequest) request;
		String verifyCode;
		try{
			verifyCode=httpServletRequest.getParameter("validateCode").toUpperCase();
		}catch (NullPointerException e){
			verifyCode=null;
		}
		//判断验证码输入是否正确
		if(verifyCode!=null && !verifyCode.equals(httpServletRequest.getSession().getAttribute(captchaParam))){
			//如果校验失败，将验证码错误的失败信息，通过shiroLoginFailure设置到request中
			httpServletRequest.setAttribute("shiroLoginFailure","验证码错误！");
			//拒绝访问，不再校验账号和密码
			return true;
		}
		return super.onAccessDenied(request, response);
	}


	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
	
	public String getMessageParam() {
		return messageParam;
	}
	
	protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
    	request.setAttribute(getFailureKeyAttribute(), ae.getClass().getName());
		if (ae.getMessage() != null && StringUtils.startsWith(ae.getMessage(), "msg:")){
			String message = StringUtils.replace(ae.getMessage(), "msg:", "");
	        request.setAttribute(getMessageParam(), message);
		}
    }
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

}