package cn.edu.jlu.ccst.action;

import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import cn.edu.jlu.ccst.dao.UserServiceImpl;
import cn.edu.jlu.ccst.model.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Component("forgetpassAction")
public class ForgetpassAction extends ActionSupport {
	private JavaMailSender mailSender;
	private User user;
	private UserServiceImpl userServiceImpl;
	private String captcha;
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String numberChar = "0123456789";
	private String tip;
	public String cap;

	@Resource
	public void setUserSerbviceImpl(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	public UserServiceImpl getUserserviceImpl() {
		return userServiceImpl;
	}

	@Resource
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public JavaMailSender getMailSender() {
		return mailSender;
	}

	@Resource
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String sendMail() throws Exception {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		Properties props = new Properties();
		props.put("mail.smtp.host ", "stmp.163.com");
		props.put("mail.smtp.auth ", "true ");
		mailMessage.setTo(user.getUsername());
		mailMessage.setFrom("ykwolf.hi@163.com");
		mailMessage.setSubject("captcha");
		cap = generateString(6);
		mailMessage.setText("Your current password is:" + cap);
		if (userServiceImpl.exits(user.getUsername())) {
			mailSender.send(mailMessage);
			System.out.println("send success!");
			User person = userServiceImpl.findBYusername(user);
			person.setCaptcha(cap);
			userServiceImpl.update(person.getId());
			return SUCCESS;
		} else {
			setTip("The USERNAME you input is not exist");
			return ERROR;
		}
	}

	/*
	 * public String forgetPass() { User person =
	 * userServiceImpl.findBYusername(user); person.setPassword(cap); if
	 * (captcha.equals(person.getPassword())) { return SUCCESS; } else { return
	 * ERROR; }
	 * 
	 * }
	 */
	public String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getTip() {
		return tip;
	}

}
