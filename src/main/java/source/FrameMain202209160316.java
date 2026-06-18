package source;
import com.formdev.flatlaf.FlatIntelliJLaf;
import notify.Notifications;
import org.jetbrains.annotations.NotNull;
import source.MElements.MJTextArea;
import source.MElements.MJTextField;
import svgloader.loaderfunc.SvgUtils;
import java.io.File;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

import static java.lang.System.out;

public class FrameMain202209160316 extends JFrame{
    String str_return = "父窗体的值";
    Connection conn316 = null;
    Statement st316 = null;
    ResultSet rs316 = null;
    String url316 = null;
    String db_path = null;
    String user_path = System.getProperty("user.dir");
    private double calc(String num1,String num2,String type){
        double res;
        double n1;
        double n2;
        n1 = Double.parseDouble(num1);
        n2 = Double.parseDouble(num2);
        res = switch (type) {
            case "+" -> n1 + n2;
            case "-" -> n1 - n2;
            case "*" -> n1 * n2;
            case "/" -> n1 / n2;
            default -> 0;
        };
        return res;
    }
    private boolean OpenDB(String str){
        boolean b_return = false;
        try{
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            url316 = "jdbc:ucanaccess://" + str;
            conn316 = DriverManager.getConnection(url316);
            st316 = conn316.createStatement();
            b_return = true;
        }catch(Exception e){
            conn316 = null;
            st316 = null;
            rs316 = null;
        }
        return b_return;
    }
    private boolean ShowAll(DefaultTableModel tab ,String str_sql){
        boolean is = false;
        try {
            if (conn316 != null&&!conn316.isClosed()&&st316 != null&&!st316.isClosed()){
                rs316 = excuteSQL(str_sql);
                for(int i = tab.getRowCount() - 1;i >= 0;i--){
                    tab.removeRow(i);
                }
            }
            is = true;
        }catch(SQLException e1){
            e1.printStackTrace();
            CreateNotification("错误",3,2);
            conn316 =null;
            st316=null;
            rs316=null;
        }

        int numberOfColumns = 0;
        ResultSetMetaData rsmd;
        try{
            if (rs316 == null) return false;
            rsmd = rs316.getMetaData();
            if (rsmd == null) return false;
            numberOfColumns = rsmd.getColumnCount();

        }
        catch(Exception ex){
            //jt.append("获取数据错误");
            CreateNotification("获取数据错误",3,2);
        }
        try{
            while(rs316.next()){
                Vector<String> newRow = new Vector<>();
                int c = 1;
                while(c <= numberOfColumns){
                    newRow.addElement(rs316.getString(c));
                    c++;
                }
                tab.addRow(newRow);
            }
        }
        catch(Exception ex){
            is = false;
        }
        return is;
    }
    private void BtnSure(){
        FrameNext202209160316 fr = new FrameNext202209160316("毕天宇","202209160316");
        fr.fr_main = this;
        fr.pack();
        fr.setSize(600,400);
        fr.setTitle("子窗体 毕天宇 202209160316");
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setVisible(true);
    }
    private void BtnShow(MJTextArea a){
        a.append(str_return + "\n");
    }
    private ResultSet excuteSQL(String s){
        ResultSet res = null;
        try{
            res = st316.executeQuery(s);
        }catch (Exception e){
            out.println(e.getMessage());
        }
        return res;
    }
    private void CreateNotification(String mes,int type1,int location1){
        notify.Notifications.Type type = Notifications.Type.values()[type1];
        Notifications.Location location = Notifications.Location.values()[location1];
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = simpleDateFormat.format(new Date());
        Notifications.getInstance().show(type, location, mes + "\n"+dateStr);

    }
    private String FindFiles(String path,String type){
        File dir = new File(path);
        File[] subFiles = dir.listFiles();
        String str_res = "";

        if (subFiles == null)
            return "错误";

        for (File subFile : subFiles) {
            if (subFile.isDirectory()){
                FindFiles(subFile.getAbsolutePath(),type);
            }else if(subFile.isFile() && subFile.getName().endsWith("."+ type)){
                str_res = subFile.getName();
            }
        }
        if (str_res.equals(""))
                return "未找到文件";
        return str_res;
    }
    public String BetterDouble(double num,int length){
        NumberFormat nf = NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        nf.setMaximumFractionDigits(length);
        return nf.format(num);
    }
    public FrameMain202209160316() {
        JPanel panel = new JPanel();
        Font pf_b = Fontloader.CreateFonts("PingFang-Bold",13);

        UIManager.put("Button.arc", 14);
        UIManager.put("Component.arc", 14);
        UIManager.put("CheckBox.arc", 14);
        UIManager.put("TextComponent.arc", 14);
        UIManager.put("Component.arrowType", "triangle");
        UIManager.put("ScrollBar.thumbArc", 14);
        panel.setLayout(null);
        this.add(panel,BorderLayout.CENTER);
        JTextField text1 = new JTextField("");
        JComboBox combobox = new JComboBox(new String[]{"+", "-","*","/"});
        JTextField text2 = new JTextField("");
        JTextField restext = new JTextField("");
        restext.setEditable(false);
        JButton button_calc = new JButton();
        MJTextArea jt = new MJTextArea();
        JScrollPane jt_scro=new JScrollPane();
        JButton button_next = new JButton();
        JButton button_show = new JButton();

        String[] header = {"RowID","userName","userNum","comment"};
        JLabel lab_db = new JLabel();
        MJTextField db_path_ui = new MJTextField();
        JTable table_db = new JTable(){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        DefaultTableModel t_model = (DefaultTableModel) table_db.getModel();
        t_model.setColumnIdentifiers(header);
        JTableHeader tab_header = table_db.getTableHeader();

        JScrollPane scr_pane = new JScrollPane(table_db);

        JButton button_open = new JButton();

        JLabel lab_username = new JLabel();
        JLabel lab_usernum = new JLabel();
        JLabel lab_rowid = new JLabel();
        MJTextField text_username = new MJTextField();
        MJTextField text_usernum = new MJTextField();
        MJTextField text_rowid = new MJTextField();

        JButton button_ref = new JButton();
        JButton button_query = new JButton();
        JButton button_add = new JButton();
        JButton button_update = new JButton();
        JButton button_delete = new JButton();
        JButton button_clear = new JButton();
        jt.setLineWrap(true);

        jt_scro.setViewportView(jt);
        DefaultCaret caret = (DefaultCaret)jt.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        jt.append(user_path + "\\"+FindFiles(user_path,"mdb") + "\n");
        button_calc.setText("计算");
        button_next.setText("子窗体");
        button_show.setText("显示");
        lab_db.setText("数据库地址");
        button_open.setText("打开");
        lab_username.setText("姓名:");
        lab_usernum.setText("学号:");
        lab_rowid.setText("ID:");
        button_ref.setText("刷新");
        button_query.setText("查询");
        button_add.setText("添加");
        button_update.setText("更新");
        button_delete.setText("删除");
        button_clear.setText("清空");

        lab_db.setIcon(SvgUtils.getSvg("location-arrow-solid",13,13));
        button_calc.setIcon(SvgUtils.getSvg("equals-solid",13,13));
        lab_username.setIcon(SvgUtils.getSvg("signature-solid",13,13));
        lab_usernum.setIcon(SvgUtils.getSvg("list-ol-solid",13,13));
        lab_rowid.setIcon(SvgUtils.getSvg("address-card-regular",13,13));
        button_next.setIcon(SvgUtils.getSvg("baby-solid",13,13));
        button_show.setIcon(SvgUtils.getSvg("eye-solid",13,13));
        button_delete.setIcon(SvgUtils.getSvg("minus-circle-solid",13,13));
        button_add.setIcon(SvgUtils.getSvg("plus-solid",13,13));
        button_calc.setIcon(SvgUtils.getSvg("equals-solid",13,13));
        button_ref.setIcon(SvgUtils.getSvg("sync-solid",13,13));
        button_query.setIcon(SvgUtils.getSvg("search-solid",13,13));
        button_open.setIcon(SvgUtils.getSvg("folder-open-solid",13,13));
        button_update.setIcon(SvgUtils.getSvg("file-import-solid",13,13));
        button_clear.setIcon(SvgUtils.getSvg("times-solid",13,13));
        text1.setBounds(10,10,90,32);
        combobox.setBounds(110,10,50,32);
        text2.setBounds(170,10,90,32);
        restext.setBounds(10,48,250,32);
        button_calc.setBounds(280,10,90,70);
        button_show.setBounds(370,10,90,70);
        button_next.setBounds(460,10,90,70);

        jt_scro.setBounds(10,540,600,70);

        lab_db.setBounds(10,100,100,40);
        db_path_ui.setBounds(100,100,510,40);
        button_open.setBounds(615,100,80,40);
        scr_pane.setBounds(10,150,600,380);

        lab_username.setBounds(624,190,80,40);
        text_username.setBounds(670,190,150,40);
        lab_usernum.setBounds(624,250,80,40);
        text_usernum.setBounds(670,250,150,40);
        lab_rowid.setBounds(630,310,80,40);
        text_rowid.setBounds(670,310,80,40);
        button_ref.setBounds(670,370,80,40);
        button_query.setBounds(750,370,80,40);
        button_add.setBounds(670,430,80,40);
        button_update.setBounds(750,430,80,40);
        button_delete.setBounds(670,490,80,40);
        button_clear.setBounds(750,490,80,40);

        text1.setFont(pf_b);
        combobox.setFont(pf_b);
        text2.setFont(pf_b);
        button_calc.setFont(pf_b);
        button_next.setFont(pf_b);
        button_show.setFont(pf_b);
        lab_username.setFont(pf_b);
        lab_usernum.setFont(pf_b);
        lab_rowid.setFont(pf_b);
        button_ref.setFont(pf_b);
        button_query.setFont(pf_b);
        button_add.setFont(pf_b);
        button_update.setFont(pf_b);
        button_delete.setFont(pf_b);
        button_clear.setFont(pf_b);
        button_open.setFont(pf_b);
        db_path_ui.setFont(pf_b);
        lab_db.setFont(pf_b);
        jt.setFont(pf_b);
        jt_scro.setFont(pf_b);
        text_username.setFont(pf_b);
        text_usernum.setFont(pf_b);
        text_rowid.setFont(pf_b);
        text_rowid.copy.setFont(pf_b);
        tab_header.setFont(pf_b);
        table_db.setFont(pf_b);

        panel.add(text1);
        panel.add(combobox);
        panel.add(text2);
        panel.add(restext);
        panel.add(button_calc);
        panel.add(button_next);
        panel.add(button_show);
        panel.add(lab_username);
        panel.add(text_username);
        panel.add(lab_usernum);
        panel.add(text_usernum);
        panel.add(lab_rowid);
        panel.add(text_rowid);
        panel.add(button_ref);
        panel.add(button_query);
        panel.add(button_add);
        panel.add(button_update);
        panel.add(button_delete);
        panel.add(button_clear);
        panel.add(jt_scro);
        panel.add(lab_db);
        panel.add(db_path_ui);
        panel.add(button_open);
        panel.add(scr_pane);
        class text_listener implements DocumentListener {

            @Override
            public void insertUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    db_path = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    db_path = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    db_path = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        class button_listener implements ActionListener{
            public void actionPerformed(@NotNull ActionEvent e){
                JButton b = (JButton)e.getSource();

                if (b == button_next){
                    BtnSure();
                } else if (b == button_calc) {
                    String str1 = text1.getText().trim();
                    String str2 = text2.getText().trim();
                    String strc = Objects.requireNonNull(combobox.getSelectedItem()).toString().trim();
                    try{
                        double res = calc(str1,str2,strc);
                        restext.setText(BetterDouble(res,2));
                    }catch (Exception ee){
                        CreateNotification("输入的数据有误",3,2);
                    }
                } else if (b == button_show) {
                    BtnShow(jt);
                } else if (b == button_open) {
                    boolean bb = OpenDB(db_path);
                    if (bb){
                        CreateNotification(db_path + " - 连接成功",0,2);
                        ShowAll(t_model,"select * from tb_test");
                    }else{
                        CreateNotification(db_path + " - 连接失败",3,2);
                        t_model.setRowCount(0);
                    }
                } else if (b == button_add) {
                    String str_sql = String.format("insert into tb_test (userName,userNum,comment) VALUES ('%s', '%s','-')", text_username.getText().trim(), text_usernum.getText().trim());
                    try{
                        st316.executeUpdate(str_sql);
                        CreateNotification(String.format("(姓名: %s 学号: %s comment: -) - 添加成功", text_username.getText().trim(),text_usernum.getText().trim())+ text_username.getText().trim(),0,2);

                    }catch (Exception e2){
                        CreateNotification(e2.getMessage(),3,2);
                        CreateNotification(String.format("(姓名: %s 学号: %s comment: -) - 添加失败", text_username.getText().trim(),text_usernum.getText().trim())+ text_username.getText().trim(),3,2);
                    }
                    ShowAll(t_model,"select * from tb_test");
                } else if (b == button_update) {
                    String str_sql = String.format("UPDATE tb_test SET userName='%s', userNum='%s', comment='-' where RowID = '%s'", text_username.getText().trim(), text_usernum.getText().trim(), text_rowid.getText().trim());
                    try{
                        st316.executeUpdate(str_sql);
                        CreateNotification("姓名: " + text_username.getText().trim() + " - 更新成功",0,2);
                    }catch (Exception e2){
                        CreateNotification(e2.getMessage(),3,2);
                    }
                    ShowAll(t_model,"select * from tb_test");
                } else if (b == button_delete) {
                    String str_sql = String.format("delete from tb_test where RowID = '%s'", text_rowid.getText().trim());
                    try{
                        st316.executeUpdate(str_sql);
                        CreateNotification("ID: "+text_rowid.getText().trim() + " - 删除成功",0,2);

                    }catch (Exception e2){
                        CreateNotification(e2.getMessage(),3,2);
                    }
                    ShowAll(t_model,"select * from tb_test");
                } else if (b == button_query) {
                    String str_sql = String.format("select * from tb_test where userName like '%s'", text_username.getText().trim());

                    boolean a = ShowAll(t_model,str_sql);
                    if (a) {
                        CreateNotification(String.format("%s - 查询成功", text_username.getText().trim()),0,2);
                    }else {
                        CreateNotification(String.format("%s - 查询失败", text_username.getText().trim()),3,2);
                    }
                } else if (b == button_clear) {
                    text_username.setText("");
                    text_usernum.setText("");
                    text_rowid.setText("");
                } else if (b == button_ref) {
                    boolean bb = OpenDB(db_path);
                    if (bb){
                        CreateNotification("刷新成功",0,2);
                        ShowAll(t_model,"select * from tb_test");
                    }else{
                        CreateNotification("刷新失败",3,2);
                        t_model.setRowCount(0);
                    }
                }
            }
        }
        class JtableActlis extends MouseAdapter{
            public void mouseClicked(MouseEvent e){
                text_rowid.setText((String) table_db.getValueAt(table_db.getSelectedRow(), 0));
                text_username.setText((String) table_db.getValueAt(table_db.getSelectedRow(), 1));
                text_usernum.setText((String) table_db.getValueAt(table_db.getSelectedRow(), 2));
            }
        }

        table_db.addMouseListener(new JtableActlis());
        button_listener ac = new button_listener();
        button_calc.addActionListener(ac);
        button_next.addActionListener(ac);
        button_show.addActionListener(ac);
        button_open.addActionListener(ac);

        button_add.addActionListener(ac);
        button_clear.addActionListener(ac);
        button_query.addActionListener(ac);
        button_delete.addActionListener(ac);
        button_update.addActionListener(ac);
        button_ref.addActionListener(ac);
        db_path_ui.getDocument().addDocumentListener(new text_listener());

    }
    public static void main(String[] args){
        FlatIntelliJLaf.setup();
        FrameMain202209160316 fr = new FrameMain202209160316();
        Notifications.getInstance().setJFrame(fr);
        fr.pack();
        Image myimage=SvgUtils.getSvg("calculator",13,13).getImage();
        fr.setIconImage(myimage);
        fr.setTitle("毕天宇 202209160316");
        fr.setLocation(500,500);
        fr.setSize(850,650);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
    }
}

