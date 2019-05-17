package com.monco.core.service;

import com.monco.core.entity.Attendance;

import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/17 13:01
 * @Description:
 */
public interface AttendanceService extends BaseService<Attendance, Long> {

    /**
     * 保存考勤 和 工薪
     *
     * @param attendanceList
     */
    void saveAttendance(List<Attendance> attendanceList);

    /**
     * 查找考勤
     *
     * @param userId
     * @param year
     * @param month
     * @return
     */
    List<Attendance> getAttendanceList(Long userId, Integer year, Integer month);
}
