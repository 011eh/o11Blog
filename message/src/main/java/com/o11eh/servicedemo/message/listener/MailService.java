package com.o11eh.servicedemo.message.listener;

import com.o11eh.servicedemo.servicebase.constants.RabbitConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Map;


@Slf4j
@Component
@AllArgsConstructor
public class MailService {

    public static final String SUBJECT = "011eh注册";
    public static final String WEB_URL = "localhost:8001";
    private JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @RabbitListener(queues = RabbitConstants.EMAIL_QUEUE)
    public void sendEmail(Map<String, String> mailInfo) {
        try {
            String token = mailInfo.get("token");
            String text = "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "  <title>011eh</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<a href=\"" + WEB_URL + "/auth/activate/" + token + "\"> 点击这里</a>完成账户激活\n" +
                    "</body>\n" +
                    "</html>\n";

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(SUBJECT);

            helper.setText(text, true);
            helper.setTo(mailInfo.get("receiver"));
            helper.setFrom(sender);
            mailSender.send(mimeMessage);
            log.info("邮件发送成功");
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
