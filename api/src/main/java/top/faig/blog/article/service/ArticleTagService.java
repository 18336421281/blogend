package top.faig.blog.article.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.faig.blog.code.article.entity.ArticleTag;
import top.faig.blog.code.tags.entity.Tags;
import top.faig.blog.code.article.mapper.ArticleTagMapper;
import top.faig.blog.code.tags.mapper.TagsMapper;

import java.util.Arrays;
import java.util.Optional;


/**
 * <p>
 * 博文标签表 服务实现类
 * </p>
 *
 * @author fhs
 * @since 2020-04-27
 */
@Service
@RequiredArgsConstructor
public class ArticleTagService extends ServiceImpl<ArticleTagMapper, ArticleTag> {


    private final TagsMapper tagsMapper;


    public void stick(Integer articleId, String tagNames) {
        this.remove(Wrappers.<ArticleTag>lambdaQuery().eq(ArticleTag::getArticleId, articleId));

        ArticleTag articleTag = new ArticleTag();
        articleTag.setArticleId(articleId);
        String[] split = tagNames.split(",");

        Arrays.stream(split).forEach(item -> {
            Tags tags = tagsMapper.selectOne(Wrappers.<Tags>lambdaQuery()
                    .eq(Tags::getTagName, item));

            Optional.ofNullable(tags)
                    .filter(t -> t != null)
                    .map(t -> {
                        articleTag.setTagId(t.getTagId());
                        this.save(articleTag);
                        return t;
                    })
                    .orElseGet(() -> {
                        Tags tags2 = new Tags();
                        tags2.setTagName(item);
                        tagsMapper.insert(tags2);
                        articleTag.setTagId(tags2.getTagId());
                        this.save(articleTag);
                        return tags2;
                    });
        });
    }
}
