package com.kimmin.mail;

/**
 * Created by min.jin on 2016/2/26.
 */

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/** Command Pattern **/

public class MailSendTask implements com.kimmin.mail.IMailSend, Callable<Boolean> {


    public MailSendTask(File templateDir,String templateName,Map<String,Object> dom,String from,String to){
        this.templateDir = templateDir;
        this.dom = dom;
        this.templateName = templateName;
        this.destination = to;
        this.departure = from;
    }

    /** Linked Command **/
    private MailSendTask nextCommand = null;

    /** Freemarker Configuration **/
    private Configuration configuration = new Configuration();
    private File templateDir = null;
    private String templateName = null;
    private Map<String,Object> dom = null;

    /** Mail Sender Part **/
    private JavaMailSenderImpl sender = null;
    private String destination = null;
    private String departure = null;


    public Boolean call(){
        try{
            /** Transactional Support **/
            send();
            return true;
        }catch(Throwable e){
            e.printStackTrace();
            /** Rolling Back **/

            return false;
        }
    }

    public void send(){
        try{
            doSend();
            if(nextCommand!=null) {
                nextCommand.send();
            }
        }catch(MailSendException mse){
            mse.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }catch (TemplateException te){
            te.printStackTrace();
        }
    }

    private void doSend() throws MailSendException, IOException, TemplateException{
        /** Push A Mail-Sending Task to the Worker Pool **/
        configuration.setDirectoryForTemplateLoading(templateDir);
        Template template = configuration.getTemplate(templateName);
        final String szMailCtnt = FreeMarkerTemplateUtils.processTemplateIntoString(template,dom);

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                /** Specify Message From & To **/
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(destination);
                message.setFrom(departure);

                /** Fake Title .. Need to change dynamically **/
                message.setSubject("[AUTO MAIL] :THIS IS A AUTOMATIC MAIL, PLEASE DO NOT REPLY!");

                message.setText(szMailCtnt,true);
            }
        };
        sender.send(preparator);
    }

    public boolean appendCommand(MailSendTask command){
        if(this.nextCommand!=null){
            return false;
        }
        this.nextCommand = command;
        return true;
    }

    public boolean cancelCommand(){
        if(this.nextCommand==null){
            return true;
        }
        this.nextCommand = null;
        return false;
    }

    public void setConfiguration(String host,String username,String password){

        if(sender==null){
            sender = new JavaMailSenderImpl();
        }
        sender.setProtocol("smtp");
        sender.setHost(host);
        sender.setUsername(username);
        sender.setPassword(password);
    }


}
