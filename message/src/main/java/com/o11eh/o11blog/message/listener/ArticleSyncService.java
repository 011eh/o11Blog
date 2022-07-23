package com.o11eh.o11blog.message.listener;

import com.o11eh.o11blog.message.client.SearchClient;
import com.o11eh.o11blog.servicebase.constants.RabbitConstants;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArticleSyncService {

    private final SearchClient searchClient;

    @RabbitListener(queues = RabbitConstants.QUEUE_ARTICLE)
    public void articleSync(Article article) {
        try {
            searchClient.update(article);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
