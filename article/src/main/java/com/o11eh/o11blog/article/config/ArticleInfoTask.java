package com.o11eh.o11blog.article.config;

import cn.hutool.core.collection.CollUtil;
import com.o11eh.o11blog.article.repository.ArticleRepository;
import com.o11eh.o11blog.servicebase.config.RedisConfig;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.support.TransactionTemplate;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Configuration
//@EnableScheduling
@RequiredArgsConstructor
public class ArticleInfoTask {

    private final ArticleRepository articleRepository;
    private final RedisTemplate<String, Object> redis;
    private final TransactionTemplate transactionTemplate;
    private int cursorCount = 1000;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount() {
        log.info("定时任务：更新文章阅读量开始");
        List<String> keyList = redis.execute((RedisCallback<List<String>>) connection -> {
            List<String> keys = new ArrayList<>();
            try (Cursor<byte[]> nextCursor = connection.scan(ScanOptions.scanOptions().match(RedisConfig.ARTICLE_VIEW + "*").count(cursorCount).build())) {
                while (nextCursor.hasNext()) {
                    keys.add(new String(nextCursor.next(), StandardCharsets.UTF_8));
                }
            }
            return keys;
        });
        if (CollUtil.isNotEmpty(keyList)) {
            List<Long> viewCountList = redis.opsForValue().multiGet(keyList).stream().map(i -> Long.parseLong(String.valueOf(i))).collect(Collectors.toList());
            List<String> ids = keyList.stream()
                    .map(key -> key.substring(RedisConfig.ARTICLE_VIEW.length()))
                    .collect(Collectors.toList());

            log.info("更新数据库：文章阅读量");
            transactionTemplate.executeWithoutResult(status -> {
                for (int i = 0; i < ids.size(); i++) {
                    Article article = new Article();
                    article.setId(ids.get(i));
                    article.setViewCount(viewCountList.get(i));
                    articleRepository.updateById(article);
                }
            });
        }
        log.info("定时任务：更新文章阅读量结束");
    }
}
