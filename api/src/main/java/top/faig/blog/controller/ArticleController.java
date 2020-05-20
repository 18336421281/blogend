package top.faig.blog.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.*;
import top.faig.blog.code.entity.Article;
import top.faig.blog.common.utils.R;
import top.faig.blog.service.ArticleServiceImpl;

/**
 * <p>
 * 博文表 前端控制器
 * </p>
 *
 * @author fhs
 * @since 2020-04-27
 */
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {


    private final ArticleServiceImpl articleService;


    /**
     * @api {POST}  /article/add  添加博文
     * @apiGroup article
     * @apiSuccess {String} title 标题
     * @apiSuccess {String} context 内容
     * @apiSuccess {int} cateId 分类 ID
     * @apiSuccess {String} state 状态   {RELEASE: 发布，DRAFT: 草稿 (默认)，RECOVERY: 回收}
     * @apiSuccessExample {json} 成功
     * {
     * "msg": "添加成功",
     * "code": 0
     * }
     * @apiErrorExample {json} 失败
     * {
     * "msg": "分类名已存在",
     * "code": 500
     * }
     * {
     * "msg": "分类名为空",
     * "code": 500
     * }
     */
    @PostMapping("/add")
    public R add(Article article) {
        article.setContext(" ");
        return articleService.add(article);
    }
    /**
     * @api {POST}  /article/save  保存博文
     * @apiGroup article
     * @apiSuccess {id} articleId 博文 ID
     * @apiSuccess {String} title 标题
     * @apiSuccess {String} context 内容
     * @apiSuccess {int} cateId 分类 ID
     * @apiSuccess {String} state 状态   {RELEASE: 发布，DRAFT: 草稿 (默认)，RECOVERY: 回收}
     * @apiSuccessExample {json} 成功
     * {
     * "msg": "添加成功",
     * "code": 0
     * }
     * @apiErrorExample {json} 失败
     * {
     * "msg": "分类名已存在",
     * "code": 500
     * }
     * <p>
     * {
     * "msg": "分类名为空",
     * "code": 500
     * }
     */
    @PostMapping("/save")
    public R save(Article article) {
        articleService.saveOrUpdate(article);
        return R.ok("保存成功");
    }
    /**
     * @api {GET}  /article/list  列表
     * @apiGroup article
     * @apiSuccess {int} type 模式  默认查发布和草稿 RELEASE： 发布 RECOVERY： 删除 DRAFT：草稿
     * @apiSuccessExample {json} 成功
     * "contextList": [
     * {
     * "articleId": 1,
     * "title": "321",
     * "context": "32111",
     * "cateId": 1,
     * "state": "RECOVERY",
     * "updateTime": "2020-04-27 15:57:57",
     * "createTime": "2020-04-27 14:28:40"
     * }
     */
    @GetMapping("list")
    public R list(String type){
        return articleService.list(type);
    }


    /**
     * @api {GET}  /article/index_list  首页列表
     * @apiGroup article
     * @apiSuccess {int} type 模式 {1：时间排，2：标签，3：分类}
     * @apiSuccess (可选参数) {int} val 搜索内容
     * @apiSuccess (可选参数) {int} valType 搜索模式 {1：标题搜，2：内容搜}
     * @apiSuccessExample {json} 成功
     * "contextList": [
     */
    @GetMapping("page")
    public R page(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                  @RequestParam(value = "size", required = false, defaultValue = "8")  Integer size
            ,String searchValue
            ,Integer cateId
            ,Integer tagId){
        return articleService.page(page, size, searchValue, cateId, tagId);
    }

    /**
     * @api {POST}  /article/cate_save  博文分类选择
     * @apiGroup article
     * @apiSuccess {int} articleId 博文ID
     * @apiSuccess {String} cateName 分类名
     * @apiSuccessExample {json} 成功
     * {
     * 	"msg": "success",
     * 	"code": 0
     * }
     */
    @PostMapping("/cate_save")
    public R cate_save(Integer articleId, String  cateName) {
        return articleService.cate_save(articleId, cateName);
    }

    @GetMapping("/get/{articleId}")
    public R get(@PathVariable Integer articleId){
        return articleService.get(articleId);
    }


}