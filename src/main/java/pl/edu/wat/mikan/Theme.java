package pl.edu.wat.mikan;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

public enum Theme {
    Dark( new Color(90, 90, 90), new Color(187, 187, 187),
             Color.RED, Color.GREEN,
            new Color(69, 73, 74), new Color(60, 63, 65),
            new Color(49, 51, 53), new Color(69, 73, 74),
            new Color(90, 90, 90), new Color(187, 187, 187) )
    , Purple(new Color(120, 50, 160), new Color(187, 187, 187),
            Color.RED, Color.GREEN,
            new Color(120, 25, 187), new Color(60, 63, 65),
            new Color(49, 60, 90), new Color(69, 73, 74),
            new Color(60, 90, 120), new Color(187, 187, 187) )
    ;
    private Color bg, fg;
    private Color err, good;
    private Color panelBg, panelFg;
    private Color borderBg, borderFg;
    private Color buttonBg, buttonFg;

    private static final Map<String, Theme> dictionary = new TreeMap<>();

    static{
        dictionary.put("Dark", Dark);
        dictionary.put("Purple", Purple);
    }

    Theme(Color bg, Color fg, Color err, Color good,
          Color panelBg, Color panelFg, Color borderBg, Color borderFg,
          Color buttonBg, Color buttonFg){
        this.bg=bg;
        this.fg=fg;
        this.err=err;
        this.good=good;
        this.panelBg=panelBg;
        this.panelFg=panelFg;
        this.borderBg=borderBg;
        this.borderFg=borderFg;
        this.buttonBg=buttonBg;
        this.buttonFg=buttonFg;
    }

    public Color getBackground() {
        return bg;
    }

    public Color getForeground() {
        return fg;
    }

    public Color getError() {
        return err;
    }

    public Color getGood() {
        return good;
    }

    public Color getPanelBackground() {
        return panelBg;
    }

    public Color getPanelForeground() {
        return panelFg;
    }

    public Color getBorderBackground() {
        return borderBg;
    }

    public Color getBorderForeground() {
        return borderFg;
    }

    public Color getButtonBackground() {
        return buttonBg;
    }

    public Color getButtonForeground() {
        return buttonFg;
    }

    public static Theme getTheme(String name)
    {
        return dictionary.get(name);
    }
}
