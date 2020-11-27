/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.util;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import smartworkflow.dwfms.urifia.fmml.miu.util.exceptions.ApplException;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxPerimeter;
import com.mxgraph.view.mxStylesheet;

/**
 *
 * @author Ndadji Maxime
 */
public class Theme {

    private Color   buttonBorderColor = (new Color(227, 70, 70)).brighter(),
                    buttonDisabledBorderColor = (new Color(115, 115, 115)).brighter(),
                    buttonBgColor = new Color(227, 70, 70),
                    buttonDisabledBgColor = (new Color(125, 135, 125)).brighter(),
                    buttonHoverBorderColor = (new Color(255, 196, 1)).brighter(),
                    buttonHoverBgColor = new Color(255, 196, 1),
                    buttonForegroundColor = Color.WHITE,
                    buttonHoverForegroundColor = Color.WHITE,
                    toolTipColor = (new Color(227, 70, 70)).darker(),
                    bgColor = (new Color(227, 70, 70)),
                    toolBarColor = new Color(254, 211, 211);
    private Font    buttonFont = new Font("Cambria", Font.PLAIN, 14),
                    menuFont = new Font("Cambria", Font.PLAIN, 16),
                    toolTipFont = new Font("Times", Font.PLAIN, 16),
                    areasFont = new Font("Cambria", Font.PLAIN, 16),
                    messageFont = new Font("Georgia", Font.PLAIN, 14),
                    titleFont = new Font("Cambria", Font.PLAIN, 20),
                    tableTitleFont = new Font("Cambria", Font.BOLD, 16),
                    secondTitleFont = new Font("Cambria", Font.ITALIC, 15);
    private int     toolTipSize = 4;
    private Border  comboBorder = BorderFactory.createRaisedBevelBorder();
    
    public static String[] THEMES = {"green", "blue", "pink", "black"};
    public String theme = "pink";
    
    private static final mxStylesheet PINK = new mxStylesheet(),
            GREEN = new mxStylesheet(), BLUE = new mxStylesheet(),
            BLACK = new mxStylesheet();
    
    static{
        HashMap<String, Object> style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#E34646");
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
        style.put(mxConstants.STYLE_STROKECOLOR, "#E34646");
        PINK.putCellStyle("PINK", style);
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#FFFFFF");
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#000000");
        style.put(mxConstants.STYLE_STROKECOLOR, "#E34646");
        PINK.putCellStyle("PINK_BUD", style);
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
        //style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#E34646");
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        PINK.setDefaultEdgeStyle(style);
        
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#1A8FCC");
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        BLUE.putCellStyle("BLUE", style);
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#FFFFFF");
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#000000");
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        BLUE.putCellStyle("BLUE_BUD", style);
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_OPACITY, 50);
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
        //style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#6482B9");
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        BLUE.setDefaultEdgeStyle(style);
        
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#FFFFFF");
        style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        BLACK.putCellStyle("BLACK", style);
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#CCCCCC");
        style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        BLACK.putCellStyle("BLACK_BUD", style);
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        //style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#000000");
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        BLACK.setDefaultEdgeStyle(style);
        
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR, "#FFFFFF");
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#1D863B");
        style.put(mxConstants.STYLE_STROKECOLOR, "#1D8630");
        GREEN.putCellStyle("GREEN", style);
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR, "#FFFFFF");
        style.put(mxConstants.STYLE_PERIMETER, mxPerimeter.EllipsePerimeter);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_BOTTOM);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_FILLCOLOR, "#F06000");
        style.put(mxConstants.STYLE_STROKECOLOR, "#1D8630");
        GREEN.putCellStyle("GREEN_BUD", style);
        
        style = new HashMap<String, Object>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_OPACITY, 100);
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
        //style.put(mxConstants.STYLE_ENDARROW, mxConstants.ARROW_CLASSIC);
        style.put(mxConstants.STYLE_VERTICAL_ALIGN, mxConstants.ALIGN_MIDDLE);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_CENTER);
        style.put(mxConstants.STYLE_STROKECOLOR, "#F06000");
        style.put(mxConstants.STYLE_FONTCOLOR, "#446299");
        GREEN.setDefaultEdgeStyle(style);
    }
    
    private ConfigurationManager configurationManager;
    
    public Theme(){
        
    }
    
    public Theme(ConfigurationManager configurationManager) throws ApplException {
        this.configurationManager = configurationManager;
        String defaultTheme = this.configurationManager.getDefaultTheme();
        boolean treated = false;
        if(defaultTheme.equalsIgnoreCase("blue")){
            theme = "blue";
            buttonBorderColor = (new Color(27, 150, 245)).brighter();
            buttonBgColor = new Color(27, 150, 245);
            buttonHoverBorderColor = (new Color(255, 196, 1)).brighter();
            buttonHoverBgColor = new Color(255, 196, 1);
            buttonForegroundColor = Color.WHITE;
            buttonHoverForegroundColor = Color.WHITE;
            toolTipColor = (new Color(27, 150, 245)).darker();
            bgColor = (new Color(27, 150, 245));
            toolBarColor = new Color(195, 225, 250);
            buttonFont = new Font("Cambria", Font.PLAIN, 14);
            menuFont = new Font("Georgia", Font.PLAIN, 15);
            toolTipFont = new Font("Cambria", Font.PLAIN, 16);
            areasFont = new Font("Cambria", Font.PLAIN, 16);
            messageFont = new Font("Georgia", Font.PLAIN, 15);
            titleFont = new Font("Times", Font.PLAIN, 22);
            tableTitleFont = new Font("Cambria", Font.ITALIC, 16);
            secondTitleFont = new Font("Cambria", Font.PLAIN, 14);
            toolTipSize = 4;
            comboBorder = BorderFactory.createLoweredBevelBorder();
            treated = true;
        }
        
        if(defaultTheme.equalsIgnoreCase("green")){
            theme = "green";
            buttonBorderColor = (new Color(60, 138, 60)).brighter();
            buttonBgColor = new Color(60, 138, 60);
            buttonHoverBorderColor = (new Color(255, 196, 1)).brighter();
            buttonHoverBgColor = new Color(255, 196, 1);
            buttonForegroundColor = Color.WHITE;
            buttonHoverForegroundColor = Color.WHITE;
            toolTipColor = new Color(60, 138, 60);
            bgColor = (new Color(60, 138, 60));
            toolBarColor = new Color(227, 250, 227);
            buttonFont = new Font("Cambria", Font.PLAIN, 14);
            menuFont = new Font("Cambria", Font.PLAIN, 16);
            toolTipFont = new Font("Times", Font.PLAIN, 16);
            areasFont = new Font("Cambria", Font.PLAIN, 16);
            messageFont = new Font("Georgia", Font.PLAIN, 14);
            titleFont = new Font("Cambria", Font.PLAIN, 20);
            tableTitleFont = new Font("Cambria", Font.BOLD, 16);
            secondTitleFont = new Font("Cambria", Font.ITALIC, 15);
            toolTipSize = 4;
            comboBorder = BorderFactory.createRaisedBevelBorder();
            treated = true;
        }
        
        if(defaultTheme.equalsIgnoreCase("black")){
            theme = "black";
            buttonBorderColor = (new Color(3, 3, 3)).brighter();
            buttonBgColor = new Color(3, 3, 3);
            buttonHoverBorderColor = Color.GRAY.brighter();
            buttonHoverBgColor = Color.GRAY;
            buttonForegroundColor = Color.WHITE;
            buttonHoverForegroundColor = Color.WHITE;
            toolTipColor = new Color(3, 3, 3);
            bgColor = (new Color(3, 3, 3));
            toolBarColor = new Color(90, 90, 90);
            buttonFont = new Font("Times", Font.PLAIN, 14);
            menuFont = new Font("Times", Font.PLAIN, 15);
            toolTipFont = new Font("Times", Font.PLAIN, 16);
            areasFont = new Font("Cambria", Font.PLAIN, 16);
            messageFont = new Font("Georgia", Font.PLAIN, 15);
            titleFont = new Font("Times", Font.PLAIN, 21);
            tableTitleFont = new Font("Times", Font.BOLD, 17);
            secondTitleFont = new Font("Cambria", Font.BOLD, 14);
            toolTipSize = 5;
            comboBorder = BorderFactory.createLineBorder(buttonHoverBorderColor);
            treated = true;
        }
        if(!treated && !defaultTheme.equalsIgnoreCase("pink"))
            this.configurationManager.setDefaultTheme(theme);
    }
    
    public Color getButtonBorderColor() {
        return buttonBorderColor;
    }

    public Color getButtonBgColor() {
        return buttonBgColor;
    }

    public Font getButtonFont() {
        return buttonFont;
    }

    public Color getButtonHoverBgColor() {
        return buttonHoverBgColor;
    }

    public Color getButtonHoverBorderColor() {
        return buttonHoverBorderColor;
    }

    public Color getButtonForegroundColor() {
        return buttonForegroundColor;
    }

    public Font getMenuFont() {
        return menuFont;
    }

    public Color getToolTipColor() {
        return toolTipColor;
    }

    public int getToolTipSize() {
        return toolTipSize;
    }

    public Font getToolTipFont() {
        return toolTipFont;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Font getAreasFont() {
        return areasFont;
    }

    public Border getComboBorder() {
        return comboBorder;
    }

    public Color getButtonHoverForegroundColor() {
        return buttonHoverForegroundColor;
    }

    public Font getMessageFont() {
        return messageFont;
    }
    
    public static String extractRGB(Color color){
        String r = "", g = "", b = "", col = color.toString();
        String[] block = col.split(",");
        r = block[0].split("=")[1];
        g = block[1].split("=")[1];
        b = block[2].split("=")[1].substring(0, block[2].split("=")[1].length()-1);
        return "rgb("+r+","+g+","+b+")";
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public Font getTableTitleFont() {
        return tableTitleFont;
    }
    
    public Color getToolBarColor() {
        return toolBarColor;
    }

    public Font getSecondTitleFont() {
        return secondTitleFont;
    }

    public Color getButtonDisabledBgColor() {
        return buttonDisabledBgColor;
    }

    public Color getButtonDisabledBorderColor() {
        return buttonDisabledBorderColor;
    }
    
    public mxStylesheet getmxGraphStylesheet() {
        if(theme.equals("blue"))
            return BLUE;
        if(theme.equals("green"))
            return GREEN;
        if(theme.equals("black"))
            return BLACK;
        if(theme.equals("pink"))
            return PINK;
        return null;
    }
    
    public String getmxGraphStyle() {
        if(theme.equals("blue"))
            return "BLUE";
        if(theme.equals("green"))
            return "GREEN";
        if(theme.equals("black"))
            return "BLACK";
        if(theme.equals("pink"))
            return "PINK";
        return null;
    }
    
    public String getmxGraphBudStyle() {
        return getmxGraphStyle()+"_BUD";
    }
}
