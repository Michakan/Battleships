package pl.edu.wat.mikan;

import pl.edu.wat.mikan.menu.CreateUserMenu;
import pl.edu.wat.mikan.menu.IMenu;
import pl.edu.wat.mikan.menu.LoggingMenu;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

public class MenuFactory {
    public enum MenuType {
        LoggingMenu, CreateUserMenu, GameMenu, ResultsMenu
    }

    public static IMenu createMenu(MenuType type) {

        String date;

        try {
            date = Apk.getServerTime();
        } catch (UnknownHostException e) {
            date = new Date().toString();
            System.err.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            date = new Date().toString();
        }

        switch (type) {
            case LoggingMenu:
                return new LoggingMenu(date);
            case CreateUserMenu:
                return new CreateUserMenu(date);
//            case GameMenu:
//                return new GameMenu(date);
//            case ResultsMenu:
//                return new ResultMenu(date);
            default:
                return null;
        }
    }
}
