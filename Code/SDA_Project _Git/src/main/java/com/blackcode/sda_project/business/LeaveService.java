package com.blackcode.sda_project.business;

import com.blackcode.sda_project.core.SessionManager;
import com.blackcode.sda_project.database.LeaveDAO;
import com.blackcode.sda_project.model_entity.FacultyLeave;

import java.time.LocalDate;
import java.util.List;

public class LeaveService {

    private final LeaveDAO leaveDAO = new LeaveDAO();

    public void submitLeaveRequest(String reason, LocalDate startDate, int days) throws Exception {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start date cannot be in the past.");
        }
        if (days <= 0 || days > 30) {
            throw new IllegalArgumentException("Days must be between 1 and 30.");
        }

        int facultyId = SessionManager.getInstance().getUserId();
        leaveDAO.insertLeaveRequest(facultyId, reason, startDate, days);
    }

    public List<FacultyLeave> getLeaveRequests() throws Exception {
        int facultyId = SessionManager.getInstance().getUserId();
        return leaveDAO.getLeaveRequestsForFaculty(facultyId);
    }

    public List<FacultyLeave> getPendingLeaves() throws Exception {
        return leaveDAO.getLeavesByStatus("pending");
    }

    public void updateLeaveStatus(int leaveId, String status) throws Exception {
        if (!status.equals("approved") && !status.equals("disapproved")) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        leaveDAO.updateLeaveStatus(leaveId, status);
    }
}
