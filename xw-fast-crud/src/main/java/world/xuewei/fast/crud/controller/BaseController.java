package world.xuewei.fast.crud.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import world.xuewei.fast.core.exception.BusinessRunTimeException;
import world.xuewei.fast.core.util.Assert;
import world.xuewei.fast.crud.dto.request.ReqBody;
import world.xuewei.fast.crud.query.QueryBody;
import world.xuewei.fast.crud.query.ResultPage;
import world.xuewei.fast.crud.service.BaseDBService;
import world.xuewei.fast.web.dto.response.RespResult;

import java.io.Serializable;
import java.util.List;

/**
 * 基础控制器
 *
 * @author XUEW
 * @since 2023/11/1 18:02
 */
public class BaseController<T> {

    protected final BaseDBService<T> baseService;

    public BaseController(BaseDBService<T> baseService) {
        this.baseService = baseService;
    }

    /**
     * 保存实体
     *
     * @param reqBody 通用请求体
     * @return 实体对象
     */
    @RequestMapping("/saveData")
    @ResponseBody
    public RespResult saveData(@RequestBody ReqBody<T> reqBody) {
        T t = reqBody.getObj();
        Assert.assertNotEmpty(t, "实体[obj]");
        T obj = baseService.saveData(t);
        return RespResult.success("新增成功", obj);
    }

    /**
     * 批量保存实体
     *
     * @param reqBody 通用请求体
     * @return 实体对象列表
     */
    @RequestMapping("/saveBatchData")
    @ResponseBody
    public RespResult saveBatchData(@RequestBody ReqBody<T> reqBody) {
        List<T> objs = reqBody.getObjs();
        Assert.assertNotEmpty(objs, "实体数组[objs]");
        List<T> tList = baseService.saveBatchData(objs);
        return RespResult.success("新增成功", tList);
    }

    /**
     * 通过主键删除数据
     *
     * @param reqBody 通用请求体
     * @return 删除条数
     */
    @RequestMapping("/delete")
    @ResponseBody
    public RespResult delete(@RequestBody ReqBody<T> reqBody) {
        Assert.assertNotEmpty(reqBody, "ID[id]");
        Serializable id = reqBody.getId();
        Assert.assertNotEmpty(id, "ID[id]");
        int deleted = baseService.delete(id);
        if (deleted == 0) {
            return RespResult.notFound("目标");
        }
        return RespResult.success("删除成功", deleted);
    }

    /**
     * 通过主键删除数据
     *
     * @param reqBody 通用请求体
     * @return 删除条数
     */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public RespResult deleteBatch(@RequestBody ReqBody<T> reqBody) {
        Assert.assertNotEmpty(reqBody, "ID数组[ids]");
        List<Serializable> ids = reqBody.getIds();
        Assert.assertNotEmpty(ids, "ID数组[ids]");
        int deleted = baseService.deleteBatch(ids);
        if (deleted == 0) {
            return RespResult.notFound("目标");
        }
        return RespResult.success("删除成功", deleted);
    }

    /**
     * 通过指定字段及对应值删除数据
     *
     * @param reqBody 通用请求体
     * @return 删除条数
     */
    @RequestMapping("/deleteByField")
    @ResponseBody
    public RespResult deleteByField(@RequestBody ReqBody<T> reqBody) {
        String field = reqBody.getField();
        Object value = reqBody.getValue();
        Assert.assertNotEmpty(field, "字段[field]");
        Assert.assertNotEmpty(value, "值[value]");
        int deleted = baseService.deleteByField(field, value);
        if (deleted == 0) {
            return RespResult.notFound("目标");
        }
        return RespResult.success("删除成功", deleted);
    }

    /**
     * 通过指定字段及对应值删除数据
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/deleteBatchByField")
    @ResponseBody
    public RespResult deleteBatchByField(@RequestBody ReqBody<T> reqBody) {
        String field = reqBody.getField();
        List<Object> values = reqBody.getValues();
        Assert.assertNotEmpty(field, "字段[field]");
        Assert.assertNotEmpty(values, "值数组[values]");
        int deleted = baseService.deleteBatchByField(field, values);
        if (deleted == 0) {
            return RespResult.notFound("目标");
        }
        return RespResult.success("删除成功", deleted);
    }

    /**
     * 通过主键查询
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/getById")
    @ResponseBody
    public RespResult getById(@RequestBody ReqBody<T> reqBody) {
        Serializable id = reqBody.getId();
        Assert.assertNotEmpty(id, "ID[id]");
        T t = baseService.getById(id);
        if (Assert.isEmpty(t)) {
            return RespResult.notFound("目标");
        }
        return RespResult.success("查询成功", t);
    }

    /**
     * 通过主键查询
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/getByIds")
    @ResponseBody
    public RespResult getByIds(@RequestBody ReqBody<T> reqBody) {
        List<Serializable> ids = reqBody.getIds();
        Assert.assertNotEmpty(ids, "ID数组[ids]");
        List<T> list = baseService.getByIds(ids);
        return RespResult.success("查询成功", list);
    }

    /**
     * 通过实体查询数据集合
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/getByObj")
    @ResponseBody
    public RespResult getByObj(@RequestBody ReqBody<T> reqBody) {
        T obj = reqBody.getObj();
        Assert.assertNotEmpty(obj, "实体[obj]");
        List<T> list = baseService.getByObj(obj);
        return RespResult.success("查询成功", list);
    }

    /**
     * 通过指定字段和值查询
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/getByField")
    @ResponseBody
    public RespResult getByField(@RequestBody ReqBody<T> reqBody) {
        String field = reqBody.getField();
        Object value = reqBody.getValue();
        Assert.assertNotEmpty(field, "字段[field]");
        Assert.assertNotEmpty(value, "值[value]");
        List<T> list = baseService.getByField(field, value);
        return RespResult.success("查询成功", list);
    }

    /**
     * 通过指定字段及对应值查询数据
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/getBatchByField")
    @ResponseBody
    public RespResult getBatchByField(@RequestBody ReqBody<T> reqBody) {
        String field = reqBody.getField();
        List<Object> values = reqBody.getValues();
        Assert.assertNotEmpty(field, "字段[field]");
        Assert.assertNotEmpty(values, "值数组[values]");
        List<T> list = baseService.getBatchByField(field, values);
        return RespResult.success("查询成功", list);
    }

    /**
     * 查询全部
     *
     * @return 出参
     */
    @RequestMapping("/getAll")
    @ResponseBody
    public RespResult getAll() {
        List<T> list = baseService.getAll();
        return RespResult.success("查询成功", list);
    }

    /**
     * 自定义查询
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/customQuery")
    @ResponseBody
    public RespResult customQuery(@RequestBody ReqBody<T> reqBody) {
        QueryBody<T> queryBody = reqBody.getQueryBody();
        Assert.assertNotEmpty(queryBody, "查询策略[queryBody]");
        // 参数合法性检查
        queryBody.check();
        QueryWrapper<T> queryWrapper = queryBody.buildWrapper();
        IPage<T> page = queryBody.buildPage();
        try {
            if (Assert.notEmpty(page)) {
                IPage<T> wrapperPage = baseService.getByWrapperPage(queryWrapper, page);
                ResultPage<T> resultPage = new ResultPage<>(wrapperPage);
                return RespResult.success("查询成功", resultPage);
            } else {
                List<T> byWrapper = baseService.getByWrapper(queryWrapper);
                return RespResult.success("查询成功", byWrapper);
            }
        } catch (BadSqlGrammarException e) {
            throw new BusinessRunTimeException("查询失败：{}", e.getMessage());
        }
    }

    /**
     * 通过实体计数数据集合
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/countByObj")
    @ResponseBody
    public RespResult countByObj(@RequestBody ReqBody<T> reqBody) {
        T obj = reqBody.getObj();
        Assert.assertNotEmpty(obj, "实体[obj]");
        int count = baseService.countByObj(obj);
        return RespResult.success("查询成功", count);
    }

    /**
     * 通过指定字段和值计数
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @RequestMapping("/countByField")
    @ResponseBody
    public RespResult countByField(@RequestBody ReqBody<T> reqBody) {
        String field = reqBody.getField();
        Object value = reqBody.getValue();
        Assert.assertNotEmpty(field, "字段[field]");
        Assert.assertNotEmpty(value, "值[value]");
        int count = baseService.countByField(field, value);
        return RespResult.success("查询成功", count);
    }
}
