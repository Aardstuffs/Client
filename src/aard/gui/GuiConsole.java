package aard.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import aard.launch.Start;

@SuppressWarnings("serial")
public class GuiConsole extends Gui {
    private JTextField inp;
    private JTextArea out;
    
    public OutputAccess outAccess;
    
    @SuppressWarnings("unused") // It gets used
    private InputBoxData inpData;
    
    public GuiConsole() {
        super();
        
        inp = new JTextField(1);
        inp.setFont(Start.FONT_CONSOLE);
        inp.setBackground(Color.BLACK);
        inp.setForeground(Color.WHITE);
        inp.addKeyListener(inpData = new InputBoxData(HISTORY_SIZE));
        
        out = new JTextArea(256, 128);
        out.setFont(Start.FONT_CONSOLE);
        out.setBackground(Color.BLACK);
        out.setForeground(Color.WHITE);
        out.setEditable(false);
        out.setLineWrap(true);
        out.setWrapStyleWord(true);
        JScrollPane scrollpane = new JScrollPane(out, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        this.add(inp, c);
        
        c.fill = GridBagConstraints.BOTH;
        c.gridy = 0;
        c.weightx = 1D;
        c.weighty = 1D;
        this.add(scrollpane, c);
        
        this.outAccess = new OutputAccess();
    }
    
    private class InputBoxData implements KeyListener {
        public List<String> histLines;
        private int lineN;
        private String curInp;
        
        public InputBoxData(final int histSize) {
            histLines = new ArrayList<String>();
        }
        
        private void addHistory(String line) {
            while (histLines.size() >= 255)
                histLines.remove(histLines.size() - 1);
            histLines.add(0, line);
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n') e.consume();
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                String t = inp.getText().trim();
                if (!t.equals("")) {
                    out.append(t + "\n");
                    inp.setText("");
                    
                    this.addHistory(t);
                    out.setCaretPosition(out.getDocument().getLength());
                }
                inp.setText("");
                e.consume();
                lineN = -1;
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (lineN == -1) curInp = inp.getText();
                if (++lineN > histLines.size() - 1) lineN = histLines.size() - 1;
                inp.setText(histLines.get(lineN));
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (lineN == -1) {
                    curInp = inp.getText();
                } else {
                    if (--lineN == -1) inp.setText(curInp);
                    else inp.setText(histLines.get(lineN));
                }
            }
        }
    }
    
    public class OutputAccess {
        private Thread main;
        
        private OutputAccess() {
            this.main = Thread.currentThread();
        }
        
        public void write(String s) {
            new Append(s);
        }
        
        public void print(String s) {
            new Append(s + NEWLINE);
        }
        
        private class Append implements Runnable {
            private String s;
            
            public Append(String s) {
                this.s = s;
                if (Thread.currentThread().equals(main)) {
                    this.run();
                } else {
                    SwingUtilities.invokeLater(this);
                }
            }
            
            @Override
            public void run() {
                out.append(s);
                out.setCaretPosition(out.getDocument().getLength());
            }
        }
    }
    
    public static final int HISTORY_SIZE = 256;
    public static final String NEWLINE = System.getProperty("line.separator");
}
