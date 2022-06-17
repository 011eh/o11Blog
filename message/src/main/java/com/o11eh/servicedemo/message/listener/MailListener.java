package com.o11eh.servicedemo.message.listener;

import com.o11eh.servicedemo.servicebase.constants.RabbitConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class MailListener {

    @RabbitListener(queues = RabbitConstants.EMAIL_QUEUE)
    public void sendEmail(String msg) {
        System.out.println(msg);
    }
}
