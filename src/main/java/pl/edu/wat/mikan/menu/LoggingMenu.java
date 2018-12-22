package pl.edu.wat.mikan.menu;

import pl.edu.wat.mikan.MenuFactory;
import pl.edu.wat.mikan.GamePlayer;
import pl.edu.wat.mikan.Apk;
import pl.edu.wat.mikan.Theme;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Arrays;

public class LoggingMenu implements IMenu, ActionListener {

    private static final String LOGGING = "logging";
    private static final String ENTER_LOGIN="enter_login";
    private static final String CREATE_USER= "create";
    private static final String CHANGE_LANGUAGE="change_language";
    /////////////////////////////////////////////
    private JPasswordField passwordFd;
    private JPanel mainPl;
    private JButton loginBt;
    private JTextField loginFd;
    private JComboBox<String> languageCBx;
    private JLabel titleLb;
    private JLabel timeLb;
    private JButton newUserButton;
    private JTextField errorTf;
    private JPanel centralPl;


    public LoggingMenu(String date) {
        timeLb.setText(date);

        passwordFd.setActionCommand(LOGGING);
        passwordFd.addActionListener(this);
        passwordFd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                passwordFd.selectAll();
            }
        });

        loginBt.setActionCommand(LOGGING);
        loginBt.addActionListener(this);

        loginFd.selectAll();
        loginFd.setActionCommand(ENTER_LOGIN);
        loginFd.addActionListener(this);

        newUserButton.setActionCommand(CREATE_USER);
        newUserButton.addActionListener(this);

        for(String[] language :Apk.supportedLocales)
            languageCBx.addItem(language[2]);

        languageCBx.setSelectedItem(Apk.getLocaleString());
        languageCBx.setActionCommand(CHANGE_LANGUAGE);
        languageCBx.addActionListener(this);

        onThemeChange();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case LOGGING: {
                if (loginFd.getText().matches(Apk.LOGIN_PATTERN)) {
                    char[] input = passwordFd.getPassword();
                    try {
                        GamePlayer player = Apk.getPlayer(loginFd.getText());
                        if (player != null && player.checkPassword(input)) {
                            JOptionPane.showMessageDialog(mainPl.getParent(),
                                    Apk.getLocaleText("GUI", "LOGGING_MENU_SUCCESS_PASSWORD"));

                            Apk.addPlayer1(player);
                        } else {
                            JOptionPane.showMessageDialog(mainPl.getParent(),
                                    Apk.getLocaleText("GUI", "LOGGING_MENU_INVALID_LOGIN_PASSWORD"),
                                    Apk.getLocaleText("GUI", "ERROR_TITLE"),
                                    JOptionPane.ERROR_MESSAGE);
                            passwordFd.selectAll();
                            passwordFd.requestFocusInWindow();
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(mainPl.getParent(),
                                Apk.getLocaleText("GUI", "ERROR_CONNECT_TO_DATA_BASE"),
                                Apk.getLocaleText("GUI", "ERROR_TITLE"),
                                JOptionPane.ERROR_MESSAGE);
                    }

                    Arrays.fill(input, '0');
                }
            }
            break;
            case ENTER_LOGIN: {
                if(loginFd.getText().matches(Apk.LOGIN_PATTERN)) {
                    passwordFd.requestFocusInWindow();
                }
                else
                {
                    JOptionPane.showMessageDialog(mainPl.getParent(),
                            Apk.getLocaleText("GUI", "LOGIN_NOT_MACHES"),
                            Apk.getLocaleText("GUI", "ERROR_TITLE"),
                            JOptionPane.ERROR_MESSAGE);

                    loginFd.requestFocusInWindow();
                }
            }
            break;

            case CREATE_USER: {
               Apk.changeCurrMenu(MenuFactory.createMenu(MenuFactory.MenuType.CreateUserMenu));
            }
            break;
            case CHANGE_LANGUAGE:
                Apk.setLocale(languageCBx.getSelectedIndex());
                break;
        }
    }

    @Override
    public void setTime(String date)
    {
        timeLb.setText(date);
    }

    @Override
    public JPanel getMainPanel() {
        return mainPl;
    }

    @Override
    public void setConnectionNotReachable(boolean isError) {
        errorTf.setVisible(isError);
        errorTf.setEnabled(isError);
    }

    @Override
    public void onLocaleChange() {
        loginBt.setText(Apk.getLocaleText("GUI", "LOGIN"));
        loginFd.setText(Apk.getLocaleText("GUI", "ENTER_LOGIN"));
        titleLb.setText(Apk.getLocaleText("GUI", "LOGGING_MENU_TITLE"));
        newUserButton.setText(Apk.getLocaleText("GUI", "LOGGING_MENU_NEW_USER"));
        errorTf.setText(Apk.getLocaleText("GUI", "CONNECTION_ERROR"));
        languageCBx.setToolTipText(Apk.getLocaleText("GUI", "LANGUAGE_TOOLTIP"));
    }

    @Override
    public void onThemeChange(){
        Theme theme=Apk.getTheme();

        passwordFd.setBackground(theme.getBackground());
        passwordFd.setForeground(theme.getForeground());
        loginFd.setBackground(theme.getBackground());
        loginFd.setForeground(theme.getForeground());
        timeLb.setBackground(theme.getBackground());
        timeLb.setForeground(theme.getForeground());
        titleLb.setBackground(theme.getBackground());
        titleLb.setForeground(theme.getForeground());
        languageCBx.setBackground(theme.getBackground());
        languageCBx.setForeground(theme.getForeground());
        errorTf.setForeground(theme.getError());
        errorTf.setForeground(theme.getBackground());

        loginBt.setBackground(theme.getButtonBackground());
        loginBt.setForeground(theme.getButtonForeground());
        newUserButton.setBackground(theme.getButtonBackground());
        newUserButton.setForeground(theme.getButtonForeground());

        mainPl.setBackground(theme.getBorderBackground());
        mainPl.setForeground(theme.getBorderForeground());
        centralPl.setBackground(theme.getPanelBackground());
        centralPl.setForeground(theme.getPanelForeground());
    }
}
