package com.bol.core.utils;

import org.springframework.mail.SimpleMailMessage;

public class PostmarkMessage  extends SimpleMailMessage{

	    private String tag;

	    private String htmlBody;

	    public void setTag(String tag) {
	        this.tag = tag;
	    }

	    public String getTag() {
	        return tag;
	    }

	    public String getHtmlBody() {
	        return htmlBody;
	    }

	    public void setHtmlBody(String htmlBody) {
	        this.htmlBody = htmlBody;
	    }
	

}
