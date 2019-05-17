package com.monco.core.impl;

import com.monco.common.bean.ConstantUtils;
import com.monco.core.dao.AttendanceDao;
import com.monco.core.entity.Attendance;
import com.monco.core.entity.Dictionary;
import com.monco.core.entity.Salary;
import com.monco.core.entity.User;
import com.monco.core.service.AttendanceService;
import com.monco.core.service.DictionaryService;
import com.monco.core.service.SalaryService;
import com.monco.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/17 13:02
 * @Description:
 */
@Service
public class AttendanceServiceImpl extends BaseServiceImpl<Attendance, Long> implements AttendanceService {

    @Autowired
    SalaryService salaryService;

    @Autowired
    UserService userService;

    @Autowired
    DictionaryService dictionaryService;

    @Override
    @Transactional
    public void saveAttendance(List<Attendance> attendanceList) {
        this.saveCollection(attendanceList);
        for (Attendance attendance : attendanceList) {
            Salary salary = new Salary();
            salary.setMonth(attendance.getMonth());
            salary.setYear(attendance.getYear());
            salary.setNickName(attendance.getNickName());
            salary.setUserId(attendance.getUserId());
            User user = userService.find(attendance.getUserId());
            Dictionary dictionary = dictionaryService.find(user.getJobId());
            BigDecimal value = dictionary.getKeyMoney();
            salary.setSalary(value.multiply(new BigDecimal(attendance.getAttendDays())));
            salary.setSettleStatus(ConstantUtils.NUM_0);
            salaryService.save(salary);
        }
    }

    @Override
    public List<Attendance> getAttendanceList(Long userId, Integer year, Integer month) {
        Attendance attendance = new Attendance();
        attendance.setDataDelete(ConstantUtils.UN_DELETE);
        attendance.setMonth(month);
        attendance.setYear(year);
        attendance.setUserId(userId);
        Example<Attendance> attendanceExample = Example.of(attendance);
        return this.findAll(attendanceExample, Sort.by("id"));
    }
}
