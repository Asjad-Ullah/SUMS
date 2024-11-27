package com.blackcode.sda_project.business;

import com.blackcode.sda_project.database.ClassScheduleDAO;
import com.blackcode.sda_project.model_entity.ClassSchedule;

import java.time.LocalTime;
import java.util.List;

public class ClassScheduleService {

    public void addOrUpdateClassSchedule(int courseId, String day, String startTime, String endTime, String roomNo) throws Exception {
        if (!validateTime(startTime, endTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        if (ClassScheduleDAO.isRoomConflict(day, startTime, endTime, roomNo)) {
            throw new IllegalArgumentException("Another class is already scheduled in this room during the selected time.");
        }

        if (ClassScheduleDAO.isSameCourseScheduledOnSameDay(courseId, day)) {
            ClassScheduleDAO.updateClassSchedule(courseId, day, startTime, endTime, roomNo);
        } else {
            ClassScheduleDAO.addClassSchedule(courseId, day, startTime, endTime, roomNo);
        }
    }

    public List<ClassSchedule> getAllClassSchedules() throws Exception {
        return ClassScheduleDAO.getAllClassSchedules();
    }

    public void removeAllClassSchedules() throws Exception {
        ClassScheduleDAO.deleteAllClassSchedules();
    }

    private boolean validateTime(String startTime, String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        return end.isAfter(start);
    }
}
