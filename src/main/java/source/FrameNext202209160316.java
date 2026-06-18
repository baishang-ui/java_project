package source;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;

public class FrameNext202209160316 extends JFrame {
    source.FrameMain202209160316 fr_main;
    String str_name316;
    String str_num316;
    JPanel panel;
    JTextField text_name;
    JTextField text_num;
    JButton button_return;

    public FrameNext202209160316(String str_name, String str_num){

        this.str_name316 = str_name;
        this.str_num316 = str_num;
        panel = new JPanel();
        this.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);
        text_name = new JTextField();
        text_num = new JTextField();
        button_return = new JButton();
        button_return.setText("返回");
        text_name.setBounds(10, 10, 150, 50);
        text_num.setBounds(10, 70, 150, 50);
        button_return.setBounds(170, 10, 100, 50);
        text_name.setText(this.str_name316);
        text_num.setText(this.str_num316);

        button_return.addActionListener(e -> exit());
        panel.add(text_name);
        panel.add(text_num);
        panel.add(button_return);
        class text_listener1 implements DocumentListener {

            @Override
            public void insertUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    String s = doc.getText(0, doc.getLength());
                    str_name316 = s;
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    str_name316 = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    str_name316 = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
        class text_listener2 implements DocumentListener {

            @Override
            public void insertUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    String s = doc.getText(0, doc.getLength());
                    str_num316 = s;
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    str_num316 = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e){
                Document doc = e.getDocument();
                try {
                    str_num316 = doc.getText(0, doc.getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        text_name.getDocument().addDocumentListener(new text_listener1());
        text_num.getDocument().addDocumentListener(new text_listener2());
    }

    private void exit(){
        this.fr_main.str_return = this.str_name316 + this.str_num316 + "返回！";
        this.dispose();
    }
}
