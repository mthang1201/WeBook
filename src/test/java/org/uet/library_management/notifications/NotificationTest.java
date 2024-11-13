package org.uet.library_management.notifications;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.uet.library_management.core.entities.Loan;
import org.uet.library_management.core.services.LoanService;
import org.uet.library_management.tools.AlertUtil;
import org.uet.library_management.tools.SessionManager;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

class NotificationTest {

    @Test
    void testDueSoon() {
        try (MockedConstruction<AlertUtil> ignored = Mockito.mockConstruction(AlertUtil.class)) {
//            List<Loan> loans = Arrays.asList(new Loan("123", "Java Basics", "DUE_SOON", "2024-12-31"));
//            LoanService loanService = Mockito.mock(LoanService.class);
//            Mockito.when(loanService.findByUserId(Mockito.anyString())).thenReturn(loans);
//            Notification.checkDueDate();
//            Mockito.verify(loanService, Mockito.times(2)).findByUserId(Mockito.any());
//            Mockito.verify(AlertUtil.class, Mockito.times(1)).showWarningAlert(
//                    Mockito.eq("Reminder"),
//                    Mockito.eq("Your loan for \"Java Basics\" is due soon! (2 days left)"),
//                    Mockito.any(),
//                    Mockito.any());
        }
    }

    @Test
    void testOverdue() {
        try (MockedConstruction<AlertUtil> ignored = Mockito.mockConstruction(AlertUtil.class)) {
//            List<Loan> loans = Arrays.asList(new Loan("123", "Java Basics", "OVERDUE", "2024-12-28"));
//            LoanService loanService = Mockito.mock(LoanService.class);
//            Mockito.when(loanService.findByUserId(Mockito.anyString())).thenReturn(loans);
//            Notification.checkDueDate();
//            Mockito.verify(loanService, Mockito.times(2)).findByUserId(Mockito.any());
//            Mockito.verify(AlertUtil.class, Mockito.times(1)).showWarningAlert(
//                    Mockito.eq("Overdue"),
//                    Mockito.eq("Your loan for Java Basics is overdue!"),
//                    Mockito.eq("Please return Java Basics as soon as possible or you will be fined!"),
//                    Mockito.any());
        }
    }
}