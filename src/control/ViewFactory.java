package control;

import javax.swing.Timer;

import SwingCards.*;
import swingConstants.*;

public class ViewFactory {

    private static final int LOADING_TIME_MS = 100;

    static LoadingView loadingView = new LoadingView();
    static MainMenuView mainMenuView;
    static AdminCreationView adminCreationView;
    static AdminView adminView;
    static AttendanceView attendanceView;
    static CostumeMeasurementsView costumeMeasurementsView;
    static EnrollmentDetailsView enrollmentDetailsView;
    static IndividualStudentView individualStudentView;
    static LogInView logInView;
    static MedicalConditionsView medicalConditionsView;
    static StudentCreationView studentCreationView;
    static StudentProfileView studentProfileView;
    static StudentRepertoryView studentRepertoryView;
    static ShowDirectoryView showDirectoryView;
    static ShowMainView showMainView;
    static SystemSettingsView systemSettingsView;
    static LevelManagerView levelManagerView;
    static StudioManagerView studioManagerView;
    static AdminManagerView adminManagerView;
    static TeacherManagerView teacherManagerView;
    static SchedulesMainView schedulesMainView;
    static StudentView studentView;
    static TeacherPanelView teacherPanelView;
    static AdminAttendanceDataView adminAttendanceView;

    public void setUp() {

        Main.Router.setViewToPanel(ViewConstants.LOADING_SCREEN, loadingView);

    }

    public Object getViewFromName(String name) {

        Object View = switch (name) {

            case ViewConstants.MAIN_MENU -> mainMenuView;
            case ViewConstants.ADMIN_CREATION_VIEW -> adminCreationView;
            case ViewConstants.ADMIN_VIEW -> adminView;
            case ViewConstants.ATTENDANCE_VIEW -> attendanceView;
            case ViewConstants.COSTUME_MEASUREMENTS_VIEW -> costumeMeasurementsView;
            case ViewConstants.ENROLLMENT_DETAILS_VIEW -> enrollmentDetailsView;
            case ViewConstants.INDIVIDUAL_STUDENT_VIEW -> individualStudentView;
            case ViewConstants.LOGIN_VIEW -> logInView;
            case ViewConstants.MEDICAL_CONDITIONS_VIEW -> medicalConditionsView;
            case ViewConstants.STUDENT_CREATION_VIEW -> studentCreationView;
            case ViewConstants.STUDENT_PROFILE_VIEW -> studentProfileView;
            case ViewConstants.STUDENT_DIRECTORY_VIEW -> studentRepertoryView;
            case ViewConstants.SHOW_DIRECTORY_VIEW -> showDirectoryView;
            case ViewConstants.SHOW_VIEW -> showMainView;
            case ViewConstants.SYSTEM_SETTINGS_VIEW -> systemSettingsView;
            case ViewConstants.LEVEL_MANAGER_VIEW -> levelManagerView;
            case ViewConstants.STUDIO_MANAGER_VIEW -> studioManagerView;
            case ViewConstants.ADMIN_MANAGER_VIEW -> adminManagerView;
            case ViewConstants.TEACHER_MANAGER_VIEW -> teacherManagerView;
            case ViewConstants.SCHEDULES_VIEW -> schedulesMainView;
            case ViewConstants.STUDENT_VIEW -> studentView;
            case ViewConstants.TEACHER_VIEW -> teacherPanelView;
            case ViewConstants.ADMIN_ATTENDANCE_VIEW -> adminAttendanceView;
            default -> throw new IllegalArgumentException("Unexpected value: " + name);

        };

        return View;

    }

    public void generateView(String name) {

        switch (name) {

            case ViewConstants.MAIN_MENU:
                mainMenuView = new MainMenuView();
                break;

            case ViewConstants.ADMIN_CREATION_VIEW:
                adminCreationView = new AdminCreationView();
                break;

            case ViewConstants.ADMIN_VIEW:
                adminView = new AdminView();
                break;

            case ViewConstants.ATTENDANCE_VIEW:
                attendanceView = new AttendanceView();
                break;

            case ViewConstants.COSTUME_MEASUREMENTS_VIEW:
                costumeMeasurementsView = new CostumeMeasurementsView();
                break;

            case ViewConstants.ENROLLMENT_DETAILS_VIEW:
                enrollmentDetailsView = new EnrollmentDetailsView();
                break;

            case ViewConstants.INDIVIDUAL_STUDENT_VIEW:
                individualStudentView = new IndividualStudentView();
                break;

            case ViewConstants.LOGIN_VIEW:
                logInView = new LogInView();
                break;

            case ViewConstants.MEDICAL_CONDITIONS_VIEW:
                medicalConditionsView = new MedicalConditionsView();
                break;

            case ViewConstants.STUDENT_CREATION_VIEW:
                studentCreationView = new StudentCreationView();
                break;

            case ViewConstants.STUDENT_PROFILE_VIEW:
                studentProfileView = new StudentProfileView();
                break;

            case ViewConstants.STUDENT_DIRECTORY_VIEW:
                studentRepertoryView = new StudentRepertoryView();
                break;

            case ViewConstants.SHOW_DIRECTORY_VIEW:
                showDirectoryView = new ShowDirectoryView();
                break;

            case ViewConstants.SHOW_VIEW:
                showMainView = new ShowMainView();
                break;

            case ViewConstants.SYSTEM_SETTINGS_VIEW:
                systemSettingsView = new SystemSettingsView();
                break;

            case ViewConstants.LEVEL_MANAGER_VIEW:
                levelManagerView = new LevelManagerView();
                break;

            case ViewConstants.STUDIO_MANAGER_VIEW:
                studioManagerView = new StudioManagerView();
                break;

            case ViewConstants.ADMIN_MANAGER_VIEW:
                adminManagerView = new AdminManagerView();
                break;

            case ViewConstants.TEACHER_MANAGER_VIEW:
                teacherManagerView = new TeacherManagerView();
                break;

            case ViewConstants.SCHEDULES_VIEW:
                schedulesMainView = new SchedulesMainView();
                break;

            case ViewConstants.STUDENT_VIEW:
                studentView = new StudentView();
                break;

            case ViewConstants.TEACHER_VIEW:
                teacherPanelView = new TeacherPanelView();
                break;

            case ViewConstants.ADMIN_ATTENDANCE_VIEW:
                adminAttendanceView = new AdminAttendanceDataView();
                break;

        }

    }

    public boolean isViewGenerated(String name) {

        if (getViewFromName(name) == null)
            return false;
        return true;

    }

    public void LoadView(String name) {

        Timer loadingTimer = new Timer(LOADING_TIME_MS, e -> {

            Main.Router.showLoadingScreen();

        });

        loadingTimer.setRepeats(false);

        loadingTimer.start();

        String viewName = name;

        new Thread(() -> {

            if (!isViewGenerated(name)) {

                generateView(name);
                Main.Router.setViewToPanel(name, getViewFromName(name));

            }

            javax.swing.SwingUtilities.invokeLater(() -> {

                loadingTimer.stop();

                Main.Router.displayView(name);

            });

        }).start();

    }

    public void loadDynamicView(String name) {

        Timer loadingTimer = new Timer(LOADING_TIME_MS, e -> {

            Main.Router.showLoadingScreen();

        });

        loadingTimer.setRepeats(false);

        loadingTimer.start();

        String viewName = name;

        new Thread(() -> {

            if (!isViewGenerated(name)) {

                generateView(name);

            }

            switch (name) {

                case ViewConstants.ATTENDANCE_VIEW:
                    attendanceView.Update();
                    break;

                case ViewConstants.LOGIN_VIEW:
                    logInView.Update();
                    break;

                case ViewConstants.LEVEL_MANAGER_VIEW:
                    levelManagerView.Update();
                    break;

                case ViewConstants.STUDIO_MANAGER_VIEW:
                    studioManagerView.Update();
                    break;

                case ViewConstants.ADMIN_MANAGER_VIEW:
                    adminManagerView.Update();
                    break;

                case ViewConstants.TEACHER_MANAGER_VIEW:
                    teacherManagerView.Update();
                    break;

                case ViewConstants.SHOW_DIRECTORY_VIEW:
                    showDirectoryView.Update();
                    break;

                case ViewConstants.STUDENT_CREATION_VIEW:
                    studentCreationView.Update();
                    break;

                case ViewConstants.SCHEDULES_VIEW:
                    schedulesMainView.Update();
                    break;

            }

            Main.Router.setViewToPanel(name, getViewFromName(name));

            javax.swing.SwingUtilities.invokeLater(() -> {

                loadingTimer.stop();

                Main.Router.displayView(name);

            });

        }).start();

    }

    public void loadDynamicView(String name, String param1) {

        Timer loadingTimer = new Timer(LOADING_TIME_MS, e -> {

            Main.Router.showLoadingScreen();

        });

        loadingTimer.setRepeats(false);

        loadingTimer.start();

        String viewName = name;

        new Thread(() -> {

            if (!isViewGenerated(name)) {

                generateView(name);

            }

            switch (name) {

                case ViewConstants.ATTENDANCE_VIEW:
                    attendanceView.Update();
                    break;

                case ViewConstants.COSTUME_MEASUREMENTS_VIEW:
                    costumeMeasurementsView.Update(param1);
                    break;

                case ViewConstants.ENROLLMENT_DETAILS_VIEW:
                    enrollmentDetailsView.Update(param1);
                    break;

                case ViewConstants.INDIVIDUAL_STUDENT_VIEW:
                    individualStudentView.Update(param1);
                    break;

                case ViewConstants.LOGIN_VIEW:
                    logInView.Update();
                    break;

                case ViewConstants.MEDICAL_CONDITIONS_VIEW:
                    medicalConditionsView.Update(param1);
                    break;

                case ViewConstants.STUDENT_PROFILE_VIEW:
                    studentProfileView.Update(param1);
                    break;

                case ViewConstants.SHOW_VIEW:
                    showMainView.Update(param1);
                    break;

                case ViewConstants.LEVEL_MANAGER_VIEW:
                    levelManagerView.Update();
                    break;

                case ViewConstants.STUDIO_MANAGER_VIEW:
                    studioManagerView.Update();
                    break;

                case ViewConstants.ADMIN_MANAGER_VIEW:
                    adminManagerView.Update();
                    break;

                case ViewConstants.TEACHER_MANAGER_VIEW:
                    teacherManagerView.Update();
                    break;

                case ViewConstants.SCHEDULES_VIEW:
                    schedulesMainView.Update();
                    break;

                case ViewConstants.STUDENT_VIEW:
                    studentView.Update(param1);
                    break;
                case ViewConstants.ADMIN_ATTENDANCE_VIEW:
                    adminAttendanceView.Update(param1);
                    break;
                case ViewConstants.TEACHER_VIEW:
                    teacherPanelView.Update(param1);
                    break;


            }

            Main.Router.setViewToPanel(name, getViewFromName(name));

            javax.swing.SwingUtilities.invokeLater(() -> {

                loadingTimer.stop();

                Main.Router.displayView(name);

            });

        }).start();

    }

    public void loadDynamicView(String name, String param1, String param2) {

        Timer loadingTimer = new Timer(LOADING_TIME_MS, e -> {

            Main.Router.showLoadingScreen();

        });

        loadingTimer.setRepeats(false);

        loadingTimer.start();

        String viewName = name;

        new Thread(() -> {

            if (!isViewGenerated(name)) {

                generateView(name);

            }

            switch (name) {

                case ViewConstants.SHOW_DIRECTORY_VIEW:
                    showDirectoryView.Update(Boolean.parseBoolean(param1), Boolean.parseBoolean(param2));
                    break;

            }

            Main.Router.setViewToPanel(name, getViewFromName(name));

            javax.swing.SwingUtilities.invokeLater(() -> {

                loadingTimer.stop();

                Main.Router.displayView(name);

            });

        }).start();

    }

    public void loadDynamicView(String name, String param1, String param2, String param3) {

        Timer loadingTimer = new Timer(LOADING_TIME_MS, e -> {

            Main.Router.showLoadingScreen();

        });

        loadingTimer.setRepeats(false);

        loadingTimer.start();

        String viewName = name;

        new Thread(() -> {

            if (!isViewGenerated(name)) {

                generateView(name);

            }

            switch (name) {


            }

            Main.Router.setViewToPanel(name, getViewFromName(name));

            javax.swing.SwingUtilities.invokeLater(() -> {

                loadingTimer.stop();

                Main.Router.displayView(name);

            });

        }).start();

    }

    public void loadStudentDirectory(int page, String[] filters) {

        Timer loadingTimer = new Timer(LOADING_TIME_MS, e -> {

            Main.Router.showLoadingScreen();

        });

        loadingTimer.start();

        loadingTimer.setRepeats(false);

        new Thread(() -> {

            String viewName = ViewConstants.STUDENT_DIRECTORY_VIEW;

            if (!isViewGenerated(viewName)) {

                generateView(viewName);

            }

            studentRepertoryView.Update(page, filters);

            Main.Router.setViewToPanel(viewName, studentRepertoryView);

            javax.swing.SwingUtilities.invokeLater(() -> {

                loadingTimer.stop();

                Main.Router.displayView(viewName);

            });

        }).start();

    }

}
