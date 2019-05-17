package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.core.entity.Attendance;
import com.monco.core.entity.Dictionary;
import com.monco.core.entity.Material;
import com.monco.core.entity.User;
import com.monco.core.page.ErrorPage;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.AttendanceService;
import com.monco.core.service.DictionaryService;
import com.monco.core.service.UserService;
import com.monco.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/17 13:04
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("attendance")
public class AttendanceController {


    @Autowired
    AttendanceService attendanceService;

    @Autowired
    UserService userService;

    @Autowired
    DictionaryService dictionaryService;

    @PostMapping("import")
    public ApiResult importExcel(@RequestParam MultipartFile file) {
        List<Attendance> attendanceList = ExcelUtils.importExcel(file, ConstantUtils.NUM_1, ConstantUtils.NUM_1, Attendance.class);
        boolean dateError = false;
        List<Attendance> saveList = new ArrayList<>();
        List<ErrorPage> errorList = new ArrayList<>();
        int i = 1;
        for (Attendance attendance : attendanceList) {
            StringBuilder stringBuilder = new StringBuilder();
            ErrorPage errorPage = new ErrorPage();
            if (attendance.getUserId() == null || attendance.getYear() == null || attendance.getMonth() == null) {
                dateError = true;
                stringBuilder.append("第" + i + "数据有误");
            } else {
                List<Attendance> attendances = attendanceService.getAttendanceList(attendance.getUserId(), attendance.getYear(), attendance.getMonth());
                if (CollectionUtils.isNotEmpty(attendances)) {
                    attendance.setId(attendances.get(0).getId());
                }
            }
            if (attendance.getUserId() != null) {
                User user = userService.find(attendance.getUserId());
                if (user == null) {
                    dateError = true;
                    stringBuilder.append("第" + i + "数据有误,工号为" + attendance.getUserId() + "的账户姓名不匹配");
                }
                if (!user.getNickName().equals(attendance.getNickName())) {
                    dateError = true;
                    stringBuilder.append("第" + i + "数据有误,工号为" + attendance.getUserId() + "的账户姓名不匹配");
                }
            }
            attendance.setSettleStatus(ConstantUtils.NUM_0);
            User user = userService.find(attendance.getUserId());
            Dictionary dictionary = dictionaryService.find(user.getJobId());
            attendance.setSalary(dictionary.getKeyMoney().multiply(new BigDecimal(attendance.getAttendDays())));
            if (dateError) {
                errorPage.setErrorMsg(stringBuilder.toString());
                errorList.add(errorPage);
            } else {
                saveList.add(attendance);
            }
            i++;
        }
        if (CollectionUtils.isNotEmpty(attendanceList)) {
            attendanceService.saveAttendance(attendanceList);
        }
        return ApiResult.ok(errorList);
    }

    @GetMapping("download")
    public void exportExcel(Attendance attendance, HttpServletResponse response) {
        List<Attendance> attendanceList = attendanceService.findAll();
        ExcelUtils.exportExcel(attendanceList, "出勤列表", "出勤列表", Attendance.class, "出勤列表.xls", response);
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          Attendance attendance, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        if (StringUtils.isNoneBlank(attendance.getNickName())) {
            queryParam = new QueryParam("nickName", MatchType.like, attendance.getNickName());
            params.add(queryParam);
        }
        if (attendance.getSettleStatus() != null) {
            queryParam = new QueryParam("settleStatus", MatchType.equal, attendance.getSettleStatus());
            params.add(queryParam);
        }
        if (attendance.getMonth() != null) {
            queryParam = new QueryParam("month", MatchType.equal, attendance.getMonth());
            params.add(queryParam);
        }
        if (attendance.getYear() != null) {
            queryParam = new QueryParam("year", MatchType.equal, attendance.getYear());
            params.add(queryParam);
        }
        Page<Attendance> result = attendanceService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }

    @PutMapping
    public ApiResult update(@RequestBody Attendance attendance) {
        attendanceService.save(attendance);
        return ApiResult.ok();
    }
}
