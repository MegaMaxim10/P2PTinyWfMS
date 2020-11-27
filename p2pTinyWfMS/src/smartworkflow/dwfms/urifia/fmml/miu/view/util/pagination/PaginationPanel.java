/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartworkflow.dwfms.urifia.fmml.miu.view.util.pagination;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import smartworkflow.dwfms.urifia.fmml.miu.util.ConfigurationManager;
import smartworkflow.dwfms.urifia.fmml.miu.util.Theme;

/**
 *
 * @author Ndadji Maxime
 */
public class PaginationPanel<T> extends JPanel{
	private static final long serialVersionUID = 1L;
	private ConfigurationManager config;
    @SuppressWarnings("rawtypes")
	private ArrayList<PaginationObserver> filterObserverList;
    private ArrayList<T> data;
    private JLabel pageDisplayer, label;
    @SuppressWarnings("rawtypes")
	private JComboBox displayRange;
    private JButton first, prev, next, last;
    private int totalPages, currentPage;
    private ActionListener listener;
    private JPopupMenu popMenu;
    private JMenuItem itemFirst, itemPrev, itemNext, itemLast;

    @SuppressWarnings("rawtypes")
	public PaginationPanel(ConfigurationManager config, ArrayList<T> data) {
        this.config = config;
        this.data = data;
        filterObserverList = new ArrayList<PaginationObserver>();
        this.setPreferredSize(new Dimension(350, 50));
        this.setOpaque(false);
        
        this.initComponents();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void initComponents() {
        listener = new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if(command.equals("next"))
                    next();
                if(command.equals("prev"))
                    previous();
                if(command.equals("first"))
                    toStart();
                if(command.equals("last"))
                    toEnd();
                if(command.equals("range")){
                    try{
                        config.setSelectionRangeIndex(displayRange.getSelectedIndex()+"");
                        reset(data);
                    }catch(Exception ex){
                        
                    }
                }
            }
            
        };
        label = new JLabel("#"+config.getLangValue("per_page"));
        label.setPreferredSize(new Dimension(65, 30));
        label.setFont(config.getTheme().getButtonFont());
        this.add(label);
        
        displayRange = new JComboBox();
        displayRange.addItem(5);
        displayRange.addItem(10);
        displayRange.addItem(15);
        displayRange.addItem(20);
        displayRange.addItem(50);
        displayRange.addItem(100);
        displayRange.addItem(config.getLangValue("all"));
        try{
            displayRange.setSelectedIndex(Integer.parseInt(config.getSelectionRangeIndex()));
        }catch(Exception e){
            displayRange.setSelectedIndex(6);
        }
        displayRange.setPreferredSize(new Dimension(60, 25));
        displayRange.setBorder(config.getTheme().getComboBorder());
        displayRange.setFont(config.getTheme().getAreasFont());
        displayRange.setActionCommand("range");
        displayRange.addActionListener(listener);
        this.add(displayRange);
        
        label = new JLabel("");
        label.setPreferredSize(new Dimension(35, 30));
        this.add(label);
        
        first = new JButton();
        first.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("first_page")+"</font></body></html>");
        first.setPreferredSize(new Dimension(25, 25));
        first.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/first.png")));
        first.setBorderPainted(false);
        first.setFocusPainted(false);
        first.setActionCommand("first");
        first.addActionListener(listener);
        first.setContentAreaFilled(false);
        this.add(first);
        
        prev = new JButton();
        prev.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("prev_page")+"</font></body></html>");
        prev.setPreferredSize(new Dimension(25, 25));
        prev.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/prev.png")));
        prev.setBorderPainted(false);
        prev.setFocusPainted(false);
        prev.setActionCommand("prev");
        prev.addActionListener(listener);
        prev.setContentAreaFilled(false);
        this.add(prev);
        
        pageDisplayer = new JLabel("");
        pageDisplayer.setFont(config.getTheme().getAreasFont());
        pageDisplayer.setForeground(config.getTheme().getBgColor());
        pageDisplayer.setOpaque(true);
        pageDisplayer.setBackground(new Color(250, 250, 250));
        pageDisplayer.setBorder(BorderFactory.createEtchedBorder());
        pageDisplayer.setHorizontalAlignment(JLabel.CENTER);
        pageDisplayer.setPreferredSize(new Dimension(90, 25));
        pageDisplayer.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("current_page")+"</font></body></html>");
        this.add(pageDisplayer);
        
        next = new JButton();
        next.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("next_page")+"</font></body></html>");
        next.setPreferredSize(new Dimension(25, 25));
        next.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/next.png")));
        next.setBorderPainted(false);
        next.setFocusPainted(false);
        next.setActionCommand("next");
        next.addActionListener(listener);
        next.setContentAreaFilled(false);
        this.add(next);
        
        last = new JButton();
        last.setToolTipText("<html><body><font family=\""+config.getTheme().getToolTipFont().getFamily()+"\" size=\""+config.getTheme().getToolTipSize()+"\" color=\""+Theme.extractRGB(config.getTheme().getToolTipColor())+"\">"
                +config.getLangValue("last_page")+"</font></body></html>");
        last.setPreferredSize(new Dimension(25, 25));
        last.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/last.png")));
        last.setBorderPainted(false);
        last.setFocusPainted(false);
        last.setActionCommand("last");
        last.addActionListener(listener);
        last.setContentAreaFilled(false);
        this.add(last);
        
        popMenu = new JPopupMenu();
        popMenu.setBorder(BorderFactory.createEtchedBorder());
        
        itemFirst = new JMenuItem(config.getLangValue("first_page"));
        itemFirst.setFont(config.getTheme().getMenuFont());
        itemFirst.setActionCommand("first");
        itemFirst.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/first.png")));
        itemFirst.addActionListener(listener);
        popMenu.add(itemFirst);
        
        itemPrev = new JMenuItem(config.getLangValue("prev_page"));
        itemPrev.setFont(config.getTheme().getMenuFont());
        itemPrev.setActionCommand("prev");
        itemPrev.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/prev.png")));
        itemPrev.addActionListener(listener);
        popMenu.add(itemPrev);
        
        itemNext = new JMenuItem(config.getLangValue("next_page"));
        itemNext.setFont(config.getTheme().getMenuFont());
        itemNext.setActionCommand("next");
        itemNext.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/next.png")));
        itemNext.addActionListener(listener);
        popMenu.add(itemNext);
        
        itemLast = new JMenuItem(config.getLangValue("last_page"));
        itemLast.setFont(config.getTheme().getMenuFont());
        itemLast.setActionCommand("last");
        itemLast.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/last.png")));
        itemLast.addActionListener(listener);
        popMenu.add(itemLast);
        
        this.add(popMenu);
        
        this.addMouseListener(new MouseAdapter(){

            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger())
                    popMenu.show(e.getComponent(), e.getX(), e.getY());
            }
            
        });
    }
    
    @SuppressWarnings("rawtypes")
	public void addPaginationObserver(PaginationObserver obs){
        filterObserverList.add(obs);
    }
    
    @SuppressWarnings("rawtypes")
	public void removePaginationObserver(PaginationObserver obs){
        filterObserverList.remove(obs);
    }
    
    public void next(){
        calculate();
        if(currentPage < totalPages){
            currentPage++;
            notify(getCurrentData());
            updateComponent();
        }
    }
    
    public void previous(){
        calculate();
        if(currentPage > 1){
            currentPage--;
            notify(getCurrentData());
            updateComponent();
        }
    }
    
    public void toStart(){
        calculate();
        if(currentPage != 1){
            currentPage = 1;
            notify(getCurrentData());
            updateComponent();
        }
    }
    
    public void toEnd(){
        calculate();
        if(currentPage != totalPages){
            currentPage = totalPages;
            notify(getCurrentData());
            updateComponent();
        }
    }
    
    public void updateComponent(){
        if(currentPage == 1){
            prev.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/prev_dea.png")));
            first.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/first_dea.png")));
            itemPrev.setEnabled(false);
            itemFirst.setEnabled(false);
            if(totalPages > 1){
                next.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/next.png")));
                last.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/last.png")));
                itemNext.setEnabled(true);
                itemLast.setEnabled(true);
            }else{
                next.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/next_dea.png")));
                last.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/last_dea.png")));
                itemNext.setEnabled(false);
                itemLast.setEnabled(false);
            }
        }else{
            if(currentPage == totalPages){
                next.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/next_dea.png")));
                last.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/last_dea.png")));
                itemNext.setEnabled(false);
                itemLast.setEnabled(false);
                if(totalPages > 1){
                    prev.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/prev.png")));
                    first.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/first.png")));
                    itemPrev.setEnabled(true);
                    itemFirst.setEnabled(true);
                }else{
                    prev.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/prev_dea.png")));
                    first.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/first_dea.png")));
                    itemPrev.setEnabled(false);
                    itemFirst.setEnabled(false);
                }
            }else{
                prev.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/prev.png")));
                first.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/first.png")));
                next.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/next.png")));
                last.setIcon(new ImageIcon(getClass().getResource("/miu/perfumery/manager/ressources/images/last.png")));
                itemPrev.setEnabled(true);
                itemFirst.setEnabled(true);
                itemNext.setEnabled(true);
                itemLast.setEnabled(true);
            }
        }
        pageDisplayer.setText(currentPage+"/"+totalPages);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void notify(ArrayList<T> data){
        for(PaginationObserver obs : filterObserverList)
            obs.update(data);
    }
    
    public void reset(ArrayList<T> data){
        this.data = data;
        currentPage = 1;
        calculate();
        notify(getCurrentData());
        updateComponent();
    }
    
    private void calculate(){
        if(displayRange.getSelectedIndex() == 6)
            totalPages = 1;
        else{
            try{
                totalPages = Math.round(data.size() / Integer.parseInt(displayRange.getSelectedItem().toString()));
                if(data.size() > Integer.parseInt(displayRange.getSelectedItem().toString()) * totalPages)
                    totalPages++;
                if(totalPages == 0)
                    totalPages++;
            }catch(Exception e){
                totalPages = 1;
            }
        }
    }
    
    private ArrayList<T> getCurrentData(){
        if(totalPages > currentPage)
            return(toArrayList(data.subList((currentPage - 1) * Integer.parseInt(displayRange.getSelectedItem().toString()), 
                    (currentPage) * Integer.parseInt(displayRange.getSelectedItem().toString()))));
        else{
            if(totalPages == 1)
                return data;
            else
                return(toArrayList(data.subList((currentPage - 1) * Integer.parseInt(displayRange.getSelectedItem().toString()), 
                    data.size())));
        }
    }
    
    private ArrayList<T> toArrayList(List<T> TL){
        ArrayList<T> list = new ArrayList<T>();
        for(T t : TL)
            list.add(t);
        return list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public ArrayList<T> getData() {
        return data;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
