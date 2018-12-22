package pl.edu.wat.mikan.menu;

import pl.edu.wat.mikan.Apk;
import pl.edu.wat.mikan.DataBaseConnector;
import pl.edu.wat.mikan.MenuFactory;
import pl.edu.wat.mikan.Theme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class CreateUserMenu implements IMenu, ActionListener {
    private enum PasswordErrors{
        Good, Empty,  BadPassword
    }

    private static final String ENTER_LOGIN = "enter_login";
    private static final String ENTER_PASSWORD = "enter_password";
    private static final String CREATE_USER = "create";
    private static final String BACK = "back";
    ////////////////////////////////////////
    private JPanel mainPl;
    private JTextField loginFd;
    private JButton createBt;
    private JPasswordField passwordFd;
    private JPasswordField confPasswordFd;

    private JLabel timeLb;
    private JTextField loginStatusTF;
    private JButton backBt;
    private JPanel centralPl;
    private JLabel confPassLb;
    private JLabel loginLb;
    private JLabel passwordLb;
    private JLabel titleLb;

    public CreateUserMenu(String date) {
        timeLb.setText(date);

        passwordFd.setActionCommand(ENTER_PASSWORD);
        passwordFd.addActionListener(this);
        passwordFd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordFd.selectAll();
            }
        });

        confPasswordFd.setActionCommand(CREATE_USER);
        confPasswordFd.addActionListener(this);
        confPasswordFd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confPasswordFd.selectAll();
            }
        });

        loginFd.requestFocusInWindow();
        loginFd.selectAll();
        loginFd.setActionCommand(ENTER_LOGIN);
        loginFd.addActionListener(this);

        createBt.setActionCommand(CREATE_USER);
        createBt.addActionListener(this);

        backBt.setActionCommand(BACK);
        backBt.addActionListener(this);

        onThemeChange();
    }

    @Override
    public void setTime(String date) {
        timeLb.setText(date);
    }

    @Override
    public JPanel getMainPanel() {
        return mainPl;
    }

    @Override
    public void setConnectionNotReachable(boolean isError) {
//        loginStatusTF.setText();
    }

    @Override
    public void onLocaleChange() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case ENTER_LOGIN:
                try {
                    checkLogin(loginFd.getText());
                } catch (SQLException e1) {
                    e1.printStackTrace();

                    JOptionPane.showMessageDialog(mainPl.getParent(),
                            Apk.getLocaleText("GUI", "ERROR_CONNECT_TO_DATA_BASE"),
                            Apk.getLocaleText("GUI", "ERROR_TITLE"),
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case ENTER_PASSWORD: {
                char[] input = passwordFd.getPassword();
                onPasswordAction(checkPassword(input));
                Arrays.fill(input, '0');
            }
            break;
            case CREATE_USER: {
                try {
                    if (!checkLogin(loginFd.getText())) break;
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(mainPl.getParent(),
                            Apk.getLocaleText("GUI", "ERROR_CONNECT_TO_DATA_BASE"),
                            Apk.getLocaleText("GUI", "ERROR_TITLE"),
                            JOptionPane.ERROR_MESSAGE);
                    break;
                }
                char[] input = passwordFd.getPassword();
                PasswordErrors passwordError=checkPassword(input);
                if (passwordError==PasswordErrors.Good) {
                    if (Arrays.equals(input, confPasswordFd.getPassword())) {
                        JOptionPane.showMessageDialog(mainPl.getParent(),
                                Apk.getLocaleText("GUI", "CREATE_USER_MENU_SUCCESS") + " " + loginFd.getText(),
                                Apk.getLocaleText("GUI", "MESSAGE_TITLE"),
                                JOptionPane.INFORMATION_MESSAGE);
                        try {
                            byte[] salt=Apk.genSalt();
                            DataBaseConnector.addPlayer(loginFd.getText(), Apk.genEncrypted(input, salt), salt);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                            System.exit(1);
                        }
                        Arrays.fill(input, '0');
                    } else {
                        JOptionPane.showMessageDialog(mainPl.getParent(),
                                Apk.getLocaleText("GUI", "CREATE_USER_MENU_PASSWORD_ERROR_CONFIRM"),
                                Apk.getLocaleText("GUI", "ERROR_TITLE"),
                                JOptionPane.ERROR_MESSAGE);
                        confPasswordFd.selectAll();
                        confPasswordFd.requestFocusInWindow();
                        Arrays.fill(input, '0');
                        break;
                    }
                }
                else {
                    onPasswordAction(passwordError);
                    break;
                }
            }
            case BACK:
                Apk.changeCurrMenu(MenuFactory.createMenu(MenuFactory.MenuType.LoggingMenu));
                break;
        }
    }

    private boolean checkLogin(String login) throws SQLException {
        boolean isGood = false;
        Theme theme = Apk.getTheme();
        if (login.length() < Apk.MIN_LOGIN_LENGHT) {
            loginStatusTF.setText(Apk.getLocaleText("GUI", "CREATE_USER_MENU_LOGIN_ERROR_SHORT"));
            loginStatusTF.setForeground(theme.getError());
            loginFd.requestFocusInWindow();
        } else if (login.length() > Apk.MAX_LOGIN_LENGHT) {
            loginStatusTF.setText(Apk.getLocaleText("GUI", "CREATE_USER_MENU_LOGIN_ERROR_LONG"));
            loginStatusTF.setForeground(theme.getError());
            loginFd.requestFocusInWindow();
        } else if (!login.matches(Apk.LOGIN_PATTERN)) {
            loginStatusTF.setText(Apk.getLocaleText("GUI", "CREATE_USER_MENU_LOGIN_ERROR_CHARACTERS"));
            loginStatusTF.setForeground(theme.getError());
            loginFd.requestFocusInWindow();
        } else if (!Apk.checkExistPlayer(login)) {
            loginStatusTF.setText(Apk.getLocaleText("GUI", "CREATE_USER_MENU_LOGIN_GOOD"));
            loginStatusTF.setForeground(theme.getGood());
            passwordFd.requestFocusInWindow();
            isGood = true;
        } else {
            loginStatusTF.setText(Apk.getLocaleText("GUI", "CREATE_USER_MENU_LOGIN_ERROR_NOT_UNIQUE"));
            loginStatusTF.setForeground(theme.getError());
            loginFd.requestFocusInWindow();
        }
        return isGood;
    }

    private static boolean checkInvalidPasswords(final char[] password) {
        try {
            Scanner badPasswordDictionary = new Scanner(new File(Apk.class.getClassLoader().getResource(Apk.BAD_PASSWORD_DICTIONARY).getPath()));
            while (badPasswordDictionary.hasNext())
                if (Arrays.equals(password, badPasswordDictionary.next().toCharArray())) {
                    return false;
                }
            return true;
        } catch (FileNotFoundException | NullPointerException e) {
            return true;
        }
    }

    private PasswordErrors checkPassword(final char[] password) {
        if (password.length == 0) {
            return PasswordErrors.Empty;
        } else if (!checkInvalidPasswords(password))
                    return PasswordErrors.BadPassword;
        return PasswordErrors.Good;
    }

    private void onPasswordAction( PasswordErrors error)
    {
        switch (error)
        {
            case Good:
                confPasswordFd.selectAll();
                confPasswordFd.requestFocusInWindow();
                break;
            case Empty:
                JOptionPane.showMessageDialog(mainPl.getParent(),
                        Apk.getLocaleText("GUI", "CREATE_USER_MENU_PASSWORD_ERROR_EMPTY"),
                        Apk.getLocaleText("GUI", "ERROR_TITLE"),
                        JOptionPane.ERROR_MESSAGE);
                passwordFd.selectAll();
                passwordFd.requestFocusInWindow();
                break;
            case BadPassword:
                JOptionPane.showMessageDialog(mainPl.getParent(),
                        Apk.getLocaleText("GUI", "CREATE_USER_MENU_INVALID_PASSWORD"),
                        Apk.getLocaleText("GUI", "ERROR_TITLE"),
                        JOptionPane.ERROR_MESSAGE);
                passwordFd.selectAll();
                passwordFd.requestFocusInWindow();
                break;
        }
    }

    @Override
    public void onThemeChange()
    {
        Theme theme = Apk.getTheme();

        passwordFd.setBackground(theme.getBackground());
        passwordFd.setForeground(theme.getForeground());
        loginFd.setBackground(theme.getBackground());
        loginFd.setForeground(theme.getForeground());
        timeLb.setBackground(theme.getBackground());
        timeLb.setForeground(theme.getForeground());
        confPassLb.setBackground(theme.getBackground());
        confPassLb.setForeground(theme.getForeground());
        loginLb.setBackground(theme.getBackground());
        loginLb.setForeground(theme.getForeground());
        passwordLb.setBackground(theme.getBackground());
        passwordLb.setForeground(theme.getForeground());
        confPasswordFd.setBackground(theme.getBackground());
        confPasswordFd.setForeground(theme.getForeground());

        titleLb.setBackground(theme.getBackground());
        titleLb.setForeground(theme.getForeground());
        loginStatusTF.setForeground(theme.getError());
        loginStatusTF.setBackground(theme.getBackground());

        createBt.setBackground(theme.getButtonBackground());
        createBt.setForeground(theme.getButtonForeground());
        backBt.setBackground(theme.getButtonBackground());
        backBt.setForeground(theme.getButtonForeground());

        mainPl.setBackground(theme.getBorderBackground());
        mainPl.setForeground(theme.getBorderForeground());
        centralPl.setBackground(theme.getPanelBackground());
        centralPl.setForeground(theme.getPanelForeground());
    }
}