package top.faig.blog.article.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.faig.blog.code.article.entity.Article;
import top.faig.blog.code.article.entity.Category;
import top.faig.blog.code.article.mapper.ArticleMapper;
import top.faig.blog.code.article.mapper.CategoryMapper;
import top.faig.blog.common.type.ArticleType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 类别表 服务实现类
 * </p>
 *
 * @author fhs
 * @since 2020-04-27
 */
@Service
@RequiredArgsConstructor
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {

    private final ArticleMapper articleMapper;

    public void add(String name) {
        // 查看数据库 是否存在该分类
        Category category = this.getOne(Wrappers.<Category>lambdaQuery()
                .eq(Category::getCateName, name));

        if(!Objects.isNull(category)){
            this.save(new Category()
                    .setCateName(category.getCateName())
            );
        }
    }

    public List<Category> classCount() {
        // 从 mapper 取集合数据
        List<Article> articles = articleMapper.selectList(Wrappers.<Article>lambdaQuery()
                .eq(Article::getState, ArticleType.RELEASE.getType()));
        List<Category> categories = this.list();

        // 统计分类个数
        categories.forEach(category -> {
            long count = articles.stream()
                    .filter(art -> Objects.equals(art.getCateId(), category.getCateId()))
                    .count();
            category.setCount((int) count);
        });

        // 排序
        categories.sort((o1, o2) -> o2.getCount() - o1.getCount());
        return categories;
    }

}
