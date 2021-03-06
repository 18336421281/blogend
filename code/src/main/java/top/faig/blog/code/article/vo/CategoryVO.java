package top.faig.blog.code.article.vo;

import lombok.Data;
import top.faig.blog.code.article.entity.Article;

import java.io.Serializable;
import java.util.List;

/**
 * @author fhs
 * 2020/4/30 13:45
 * 文件说明:
 */
@Data
public class CategoryVO implements Serializable {

    /**
     * 类别 ID
     */
    private Integer cateId;

    /**
     * 类别名
     */
    private String cateName;

    /**
     * 博文列表
     */
    private List<Article> articleList;
}
