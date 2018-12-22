package pl.edu.wat.mikan;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import pl.edu.wat.mikan.menu.IMenu;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

//PostgresSQL port: 5432

public class Apk {
    public static final String dbUserName = "Game_Player";
    public static final String dbPassword = "!234qweR";
    public static final String dbURL = "jdbc:postgresql://localhost:5432/Ships";

    private static final String TIME_SERVER = "ntp.itl.waw.pl";
    private static final String PROPERTIES_FILE = "app.xml";
    private static final String DATE_PATTERN = "yyyy MMM dd E kk:mm:ss";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 512;
    private static final String PBK_ALGORITHM = "PBKDF2WithHmacSHA512";
    private static final String SR_ALGORITHM = "SHA1PRNG";
    public static final int HASH_SIZE = 64;
    public static final String LOGIN_PATTERN = "^[a-zA-Z0-9._-]{3,20}$";
    public static final int MIN_LOGIN_LENGHT = 3;
    public static final int MAX_LOGIN_LENGHT = 20;
    public static final String[][] supportedLocales = {
            {"pl", "PL", "polski"},
            {"en", "US", "english USA"}
    };
    public static final String BAD_PASSWORD_DICTIONARY = "badPasswords.dictionary";
    ////////////////////////////////////////////////////////////////////////////////////////////

    private static Locale locale = new Locale(supportedLocales[0][0]);
    private static IMenu menu;
    private static boolean isClosing = false;
    private static GamePlayer player1, player2;
    private static JFrame mainFrame;
    private static boolean isConnectionNotReachable = false;
    private static Theme theme = Theme.Dark;

    private static int localeIdx = 0;

    public static void main(String[] argv) {
        loadSettings();

        mainFrame = new JFrame(getLocaleText("GUI", "GAME_NAME"));
        mainFrame.setContentPane((menu = MenuFactory.createMenu(MenuFactory.MenuType.LoggingMenu)).getMainPanel());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);

        final Thread timeUpdater = new Thread(() -> {
            while (!isClosing) {
                try {
                    updateTime();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                isClosing = true;
            }
        });

        timeUpdater.start();
    }

    public static String getServerTime() throws UnknownHostException, IOException {
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
        TimeInfo timeInfo = timeClient.getTime(inetAddress);
        long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();

        return new SimpleDateFormat(DATE_PATTERN, locale).format(new Date(returnTime));
    }

    private static void updateTime() {
        try {
            menu.setTime(getServerTime());
            if (isConnectionNotReachable) {
                isConnectionNotReachable = false;
                menu.setConnectionNotReachable(false);
            }
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
            menu.setTime(new Date().toString());
            menu.setConnectionNotReachable(true);
            isConnectionNotReachable = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] genSalt() {
        try {
            byte[] salt = new byte[HASH_SIZE];
            SecureRandom.getInstance(SR_ALGORITHM).nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.exit(1);
            throw new RuntimeException(e);
        }
    }

    public static byte[] genEncrypted(char[] password, byte[] salt) {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBK_ALGORITHM);
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
            SecretKey key = skf.generateSecret(spec);
            return key.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            System.exit(1);
            throw new RuntimeException(e);
        }
    }

    public static void addPlayer1(GamePlayer player) {
        player1 = player;
    }

    public static void addPlayer2(GamePlayer player) {
        if (player != player2)
            player2 = player;
        else throw new IllegalArgumentException();
    }

    public static GamePlayer getPlayer(@org.jetbrains.annotations.NotNull String name) throws SQLException {
            return DataBaseConnector.getPlayer(name);
    }

    public static void changeCurrMenu(IMenu menu) {
        Apk.menu = menu;

        mainFrame.setContentPane(menu.getMainPanel());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static boolean checkExistPlayer(@org.jetbrains.annotations.NotNull String name) throws SQLException {
            return DataBaseConnector.checkIsCreated(name);
    }

    public static Locale getLocale() {
        return locale;
    }

    public static String getLocaleString() {
        return Apk.supportedLocales[localeIdx][2];
    }

    public static String getLocaleText(String bundle, String id) {
        return ResourceBundle.getBundle("localization/" + bundle, locale).getString(id);
    }

    private static void setLocale(String language, String country) {
        int idx;

        for (idx = 0; !(Apk.supportedLocales[idx][0].equals(language)
                && Apk.supportedLocales[idx][1].equals(country)); idx++);

        if (idx != localeIdx) {
            locale = new Locale(language, country);
            locale = new Locale(language, country);
            Locale.setDefault(locale);
            ResourceBundle.clearCache();
            localeIdx = idx;
            if (menu != null)
                menu.onLocaleChange();
        }
    }

    public static void setLocale(int idx) {
        if (idx > Apk.supportedLocales.length)
            throw new IllegalArgumentException();

        setLocale(supportedLocales[idx][0], supportedLocales[idx][1]);
    }

    private static void loadSettings() {

        try {
            Properties apkProperties = new Properties();

            apkProperties.loadFromXML(new FileInputStream(Apk.class.getClassLoader().getResource(PROPERTIES_FILE).getPath()));
            String language = apkProperties.getProperty("language");
            String country = apkProperties.getProperty("country");
            if (!checkSupportedLocale(language, country))
                throw new SettingsException();
            setLocale(language, country);
            Theme theme = Theme.getTheme(apkProperties.getProperty("theme"));
            if (theme == null)
                throw new SettingsException();
            else Apk.theme = theme;
        } catch (FileNotFoundException | InvalidPropertiesFormatException | NullPointerException
                | SettingsException e) {
            e.printStackTrace();
            saveSettings();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void saveSettings() {
        Properties apkProperties = new Properties();
//        try {
//            apkProperties.storeToXML(new FileOutputStream(Apk.class.getClassLoader().getResource(PROPERTIES_FILE).getPath()), "store to xml file");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //apkProperties.setProperty("varName","value");
        //apkProperties.storeToXML(new FileOutputStream(newAppConfigXmlFile), "store to xml file");
    }

    private static boolean checkSupportedLocale(String language, String country) {
        for (String[] locale : supportedLocales)
            if (locale[0].equals(language) & locale[1].equals(country))
                return true;
        return false;
    }

    public static Theme getTheme() {
        return theme;
    }
}
