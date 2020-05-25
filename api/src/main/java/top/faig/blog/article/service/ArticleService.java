package top.faig.blog.article.service;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.faig.blog.code.article.entity.Article;
import top.faig.blog.code.article.entity.Category;
import top.faig.blog.code.article.mapper.ArticleMapper;
import top.faig.blog.code.article.mapper.CategoryMapper;
import top.faig.blog.code.article.vo.ArticleVO;
import top.faig.blog.common.type.ArticleType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 博文表 服务实现类
 * </p>
 *
 * @author fhs
 * @since 2020-04-27
 */
@Service
@RequiredArgsConstructor
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {

    private final ArticleMapper articleMapper;
    private final CategoryMapper categoryMapper;

    public void add(Article article) {
        Optional.ofNullable(article)
                .filter(art -> art.getTitle() == null)
                .map(art -> {
                    art.setState(ArticleType.DRAFT.getType());
                    art.setTitle(LocalDate.now().toString());
                    this.save(art);
                    return art;
                })
                .orElseGet(() -> {
                    article.setState(ArticleType.DRAFT.getType());
                    this.save(article);
                    return article;
                });
    }

    public List<ArticleVO> list(String type) {
         return articleMapper.selectAll(type);
    }


    public Page<ArticleVO> page(Integer page, Integer size, String searchValue
            , Integer cateId
            , Integer tagId) {
        Page<ArticleVO> page1 = new Page<>(page, size);
        List<ArticleVO> articleVOS = articleMapper.selectPageVo(page1, searchValue, cateId, tagId);
        return page1.setRecords(articleVOS);
    }


    public void cate_save(Integer articleId, String cateName) {
        Article article = this.getOne(Wrappers.<Article>lambdaQuery()
                .eq(Article::getArticleId, articleId));
        Category category = categoryMapper.selectOne(Wrappers.<Category>lambdaQuery()
                .eq(Category::getCateName, cateName));

        Optional.ofNullable(category)
                .filter(cate -> cate != null)
                .map(cate -> {
                    article.setCateId(cate.getCateId());
                    this.saveOrUpdate(article);
                    return cate;
                })
                .orElseGet(() -> {
                    Category category1 = new Category();
                    category1.setCateName(cateName);
                    categoryMapper.insert(category1);
                    article.setCateId(category1.getCateId());
                    this.saveOrUpdate(article);
                    return category1;
                });
    }


    public ArticleVO get(Integer articleId) {
         return articleMapper.getById(articleId);
    }
}
