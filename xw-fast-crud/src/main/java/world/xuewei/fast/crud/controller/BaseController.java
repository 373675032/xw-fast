package world.xuewei.fast.crud.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import world.xuewei.fast.core.exception.BusinessRunTimeException;
import world.xuewei.fast.core.exception.ParamEmptyException;
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
    @PostMapping("/saveData")
    @ResponseBody
    public RespResult saveData(@RequestBody ReqBody<T> reqBody) {
        T obj = Assert.notNull(reqBody.getObj(), () -> ParamEmptyException.build("实体[obj]"));
        return RespResult.success("新增成功", baseService.saveData(obj));
    }

    /**
     * 批量保存实体
     *
     * @param reqBody 通用请求体
     * @return 实体对象列表
     */
    @PostMapping("/saveBatchData")
    @ResponseBody
    public RespResult saveBatchData(@RequestBody ReqBody<T> reqBody) {
        List<T> objs = Assert.notEmpty(reqBody.getObjs(), () -> ParamEmptyException.build("实体数组[objs]"));
        return RespResult.success("新增成功", baseService.saveBatchData(objs));
    }

    /**
     * 通过主键删除数据
     *
     * @param reqBody 通用请求体
     * @return 删除条数
     */
    @PostMapping("/delete")
    @ResponseBody
    public RespResult delete(@RequestBody ReqBody<T> reqBody) {
        Serializable id = Assert.notNull(reqBody.getId(), () -> ParamEmptyException.build("ID[id]"));
        int deleted = baseService.delete(id);
        return deleted == 0 ? RespResult.notFound("目标") : RespResult.success("删除成功", deleted);
    }

    /**
     * 通过主键删除数据
     *
     * @param reqBody 通用请求体
     * @return 删除条数
     */
    @PostMapping("/deleteBatch")
    @ResponseBody
    public RespResult deleteBatch(@RequestBody ReqBody<T> reqBody) {
        List<Serializable> ids = Assert.notEmpty(reqBody.getIds(), () -> ParamEmptyException.build("ID数组[ids]"));
        int deleted = baseService.deleteBatch(ids);
        return deleted == 0 ? RespResult.notFound("目标") : RespResult.success("删除成功", deleted);
    }

    /**
     * 通过指定字段及对应值删除数据
     *
     * @param reqBody 通用请求体
     * @return 删除条数
     */
    @PostMapping("/deleteByField")
    @ResponseBody
    public RespResult deleteByField(@RequestBody ReqBody<T> reqBody) {
        String field = Assert.notNull(reqBody.getField(), () -> ParamEmptyException.build("字段[field]"));
        Object value = Assert.notNull(reqBody.getValue(), () -> ParamEmptyException.build("值[value]"));
        int deleted = baseService.deleteByField(field, value);
        return deleted == 0 ? RespResult.notFound("目标") : RespResult.success("删除成功", deleted);
    }

    /**
     * 通过指定字段及对应值删除数据
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @PostMapping("/deleteBatchByField")
    @ResponseBody
    public RespResult deleteBatchByField(@RequestBody ReqBody<T> reqBody) {
        String field = Assert.notNull(reqBody.getField(), () -> ParamEmptyException.build("字段[field]"));
        List<Object> values = Assert.notEmpty(reqBody.getValues(), () -> ParamEmptyException.build("值数组[values]"));
        int deleted = baseService.deleteBatchByField(field, values);
        return deleted == 0 ? RespResult.notFound("目标") : RespResult.success("删除成功", deleted);
    }

    /**
     * 通过主键查询
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @PostMapping("/getById")
    @ResponseBody
    public RespResult getById(@RequestBody ReqBody<T> reqBody) {
        Serializable id = Assert.notNull(reqBody.getId(), () -> ParamEmptyException.build("ID[id]"));
        T t = baseService.getById(id);
        return ObjectUtil.isEmpty(t) ? RespResult.notFound("目标") : RespResult.success("查询成功", t);
    }

    /**
     * 通过主键查询
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @PostMapping("/getByIds")
    @ResponseBody
    public RespResult getByIds(@RequestBody ReqBody<T> reqBody) {
        List<Serializable> ids = Assert.notEmpty(reqBody.getIds(), () -> ParamEmptyException.build("ID数组[ids]"));
        return RespResult.success("查询成功", baseService.getByIds(ids));
    }

    /**
     * 通过实体查询数据集合
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @PostMapping("/getByObj")
    @ResponseBody
    public RespResult getByObj(@RequestBody ReqBody<T> reqBody) {
        T obj = Assert.notNull(reqBody.getObj(), () -> ParamEmptyException.build("实体[obj]"));
        return RespResult.success("查询成功", baseService.getByObj(obj));
    }

    /**
     * 通过指定字段和值查询
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @PostMapping("/getByField")
    @ResponseBody
    public RespResult getByField(@RequestBody ReqBody<T> reqBody) {
        String field = Assert.notNull(reqBody.getField(), () -> ParamEmptyException.build("字段[field]"));
        Object value = Assert.notNull(reqBody.getValue(), () -> ParamEmptyException.build("值[value]"));
        return RespResult.success("查询成功", baseService.getByField(field, value));
    }

    /**
     * 通过指定字段及对应值查询数据
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @PostMapping("/getBatchByField")
    @ResponseBody
    public RespResult getBatchByField(@RequestBody ReqBody<T> reqBody) {
        String field = Assert.notNull(reqBody.getField(), () -> ParamEmptyException.build("字段[field]"));
        List<Object> values = Assert.notEmpty(reqBody.getValues(), () -> ParamEmptyException.build("值数组[values]"));
        return RespResult.success("查询成功", baseService.getBatchByField(field, values));
    }

    /**
     * 查询全部
     *
     * @return 出参
     */
    @PostMapping("/getAll")
    @ResponseBody
    public RespResult getAll() {
        return RespResult.success("查询成功", baseService.getAll());
    }

    /**
     * 自定义查询
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @PostMapping("/customQuery")
    @ResponseBody
    public RespResult customQuery(@RequestBody ReqBody<T> reqBody) {
        QueryBody<T> queryBody = Assert.notNull(reqBody.getQueryBody(), () -> ParamEmptyException.build("查询策略[queryBody]"));
        // 参数合法性检查
        queryBody.check();
        QueryWrapper<T> queryWrapper = queryBody.buildWrapper();
        IPage<T> page = queryBody.buildPage();
        try {
            if (ObjectUtil.isNotEmpty(page)) {
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
    @PostMapping("/countByObj")
    @ResponseBody
    public RespResult countByObj(@RequestBody ReqBody<T> reqBody) {
        T obj = Assert.notNull(reqBody.getObj(), () -> ParamEmptyException.build("实体[obj]"));
        return RespResult.success("查询成功", baseService.countByObj(obj));
    }

    /**
     * 通过指定字段和值计数
     *
     * @param reqBody 通用请求体
     * @return 出参
     */
    @PostMapping("/countByField")
    @ResponseBody
    public RespResult countByField(@RequestBody ReqBody<T> reqBody) {
        String field = Assert.notNull(reqBody.getField(), () -> ParamEmptyException.build("字段[field]"));
        Object value = Assert.notNull(reqBody.getValue(), () -> ParamEmptyException.build("值[value]"));
        return RespResult.success("查询成功", baseService.countByField(field, value));
    }
}
