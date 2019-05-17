package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Attendance;
import com.monco.core.entity.Salary;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/17 15:06
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("salary")
public class SalaryController {

    @Autowired
    SalaryService salaryService;

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          Salary salary, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 姓名
        if (StringUtils.isNotBlank(salary.getNickName())) {
            queryParam = new QueryParam("nickName", MatchType.like, salary.getNickName());
            params.add(queryParam);
        }
        // 月份
        if (salary.getMonth() != null) {
            queryParam = new QueryParam("month", MatchType.equal, salary.getMonth());
            params.add(queryParam);
        }
        Page<Salary> result = salaryService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }

    @PutMapping
    public ApiResult update(@RequestBody Salary salary) {
        salaryService.save(salary);
        return ApiResult.ok();
    }
}
