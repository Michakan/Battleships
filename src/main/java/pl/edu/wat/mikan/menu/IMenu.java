package pl.edu.wat.mikan.menu;

import javax.swing.*;

public interface IMenu {
    void setTime(String date);
    JPanel getMainPanel();
    void setConnectionNotReachable(boolean isError);
    void onLocaleChange();
    void onThemeChange();
}
