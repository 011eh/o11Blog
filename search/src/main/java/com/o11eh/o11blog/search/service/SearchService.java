package com.o11eh.o11blog.search.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.o11eh.o11blog.search.client.ArticleClient;
import com.o11eh.o11blog.search.entity.ESArticle;
import com.o11eh.o11blog.search.repository.ESArticleRepository;
import com.o11eh.o11blog.servicebase.entity.PageReq;
import com.o11eh.o11blog.servicebase.entity.front.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final ElasticsearchRestTemplate elasticsearch;
    private final ArticleClient articleClient;
    private int size = 1;
    private final ESArticleRepository articleRepository;

    public void init() {
        elasticsearch.indexOps(ESArticle.class).delete();
        elasticsearch.indexOps(ESArticle.class).create();
        int page = 0;

        while (true) {
            List<Article> articles = articleClient.articlePage(page, size).getData();
            if (articles.isEmpty()) {
                break;
            }
            List<ESArticle> articleList = BeanUtil.copyToList(articles, ESArticle.class, CopyOptions.create().ignoreError());
            for (int i = 0; i < articles.size(); i++) {
                Article article = articles.get(i);
                articleList.get(i).setMemberId(article.getMember().getId());
                articleList.get(i).setMember(article.getMember().getNickName());
            }

            articleRepository.saveAll(articleList);
            page += 1;
        }
    }

    public Page<ESArticle> search(PageReq req) {
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(req.getKeyword()))
                .withPageable(PageRequest.of(Math.toIntExact(req.getCurrent()), Math.toIntExact(req.getSize()), Sort.by(Sort.Direction.DESC, "_score"))).build();

        SearchHits<ESArticle> hits = elasticsearch.search(query, ESArticle.class);
        List<ESArticle> articles = hits.stream().map(SearchHit::getContent).collect(Collectors.toList());
        Page<ESArticle> page = new PageImpl<>(articles, PageRequest.of((int) req.getCurrent(), (int) req.getSize()), hits.getTotalHits());
        return page;
    }

    public void update(Article article) {
        ESArticle esArticle = BeanUtil.copyProperties(article, ESArticle.class);
        esArticle.setMemberId(article.getMember().getNickName());
        esArticle.setMemberId(article.getMember().getId());
        articleRepository.save(esArticle);
    }
}
