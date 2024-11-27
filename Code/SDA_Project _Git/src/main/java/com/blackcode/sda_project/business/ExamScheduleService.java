package com.blackcode.sda_project.business;

import com.blackcode.sda_project.database.ExamScheduleDAO;
import java.time.LocalDate;
import java.time.LocalTime;

public class ExamScheduleService {

    public void addOrUpdateExamSchedule(String course, LocalDate date, String day, String startTime, String endTime, String roomNo) throws Exception {
        // Validate the date
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The exam date cannot be in the past.");
        }

        // Validate time format and range
        LocalTime start = parseTime(startTime, "Start time must be in HH:mm format.");
        LocalTime end = parseTime(endTime, "End time must be in HH:mm format.");

        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }

        // Check for room conflicts
        if (ExamScheduleDAO.isRoomConflict(date, startTime, endTime, roomNo, course)) {
            throw new IllegalArgumentException("Another exam is already scheduled in this room during the selected time.");
        }


        // Update if course already exists, otherwise add
        if (ExamScheduleDAO.isCourseScheduled(course)) {
            ExamScheduleDAO.updateExamSchedule(course, date, day, startTime, endTime, roomNo);
        } else {
            ExamScheduleDAO.addExamSchedule(course, date.toString(), day, startTime, endTime, roomNo);
        }
    }

    public void removeAllExamSchedules() throws Exception {
        // Call DAO to clear the exam_schedule table
        ExamScheduleDAO.removeAllSchedules();
    }

    private LocalTime parseTime(String time, String errorMessage) {
        try {
            return LocalTime.parse(time);
        } catch (Exception e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
