import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

public class TextEditor extends JFrame implements ActionListener {


    JTextArea textArea;
    JTextArea newTextArea;

    File currentFile = null;

    JMenuBar userMenuBar = new JMenuBar();
    JMenu userMenu = new JMenu("File");
    JMenuItem userOpen = new JMenuItem("Open File");
    JMenuItem userSave = new JMenuItem("Save File");
    JMenuItem userSaveAs = new JMenuItem("Save File As");
    JMenuItem userExit = new JMenuItem("Exit");

    JSpinner userFontSize = new JSpinner();
    int initialFontSize = 13;


    String[] availableColors = new String[] {"BLACK", "BLUE", "CYAN", "GRAY", "GREEN", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW"};
    JComboBox userColors = new JComboBox(availableColors);
    String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    JComboBox userFonts = new JComboBox(availableFonts);


    TextEditor() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Text Editor");
        this.setSize(800,800);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        userMenu.add(userOpen);
        userMenu.add(userSave);
        userMenu.add(userSaveAs);
        userMenu.add(userExit);
        userMenuBar.add(userMenu);
        this.setJMenuBar(userMenuBar);

        userOpen.addActionListener(this);
        userSave.addActionListener(this);
        userSaveAs.addActionListener(this);
        userExit.addActionListener(this);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(200,200,200));
        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setForeground(Color.black);
        textArea.setFont(new Font ("Times New Roman",Font.BOLD, 12));
        textArea.setText("");


        userFontSize.setPreferredSize(new Dimension(50,50));
        userFontSize.setValue(initialFontSize);
        userFontSize.addChangeListener(e -> {
            int newFontSize = (int) userFontSize.getValue();
            textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,newFontSize));
        });


        userColors.setPreferredSize(new Dimension(100,50));
        userColors.setSelectedItem("BLACK");
        userColors.addActionListener(this);

        userFonts.addActionListener(this);
        userFonts.setSelectedItem("Times New Roman");

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setEnabled(true);
        scrollPane.setPreferredSize(new Dimension(600,600));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(textArea);

        this.add(userColors);
        this.add(userFonts);
        this.add(userFontSize);
        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == userOpen) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                newTextArea = readFile(file);
                if (newTextArea != null) {
                    textArea.setText(newTextArea.getText());
                }
                currentFile = file;
            }

        }
        else if (e.getSource() == userSaveAs) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showSaveDialog(null);
            if(response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                saveFile(file, textArea.getText());
                currentFile = file;
            }

        }
       else if (e.getSource() == userSave) {
          if (currentFile != null) {
                saveFile(currentFile, textArea.getText());
           }
      } else if (e.getSource() == userFonts) {
           String newFont = (String) userFonts.getSelectedItem();
           textArea.setFont(new Font(newFont, Font.PLAIN, textArea.getFont().getSize()));
        }
       else if (e.getSource() == userColors) {
           Color userColor;
           String newColor = (String) userColors.getSelectedItem();
           System.out.println(newColor);

            try {
                Field field = Class.forName("java.awt.Color").getField(newColor);
                userColor = (Color) field.get(null);
            } catch (Exception k) {
                userColor = null;
            }

            if(userColor == null) {
                textArea.setForeground(Color.black);
            } else {
                textArea.setForeground(userColor);
            }
        }
       else if(e.getSource() == userExit) {

          closeApp();

        }


    }

    public static JTextArea readFile(File fileName) { //Reads the text files

        JTextArea newText = new JTextArea();
        try {
           BufferedReader reader =  new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            String line = reader.readLine();
            while (line != null) {
                newText.append(line);
                newText.append("\n");
                line = reader.readLine();

            }
        } catch (IOException e) {
           return null;
        }
        return newText;
    }
    public void saveFile(File file, String textAreaString) { //Literally overwrites what was previously saved, careful saving
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(textAreaString);
            writer.close();
        } catch (IOException a) {
            System.out.println("Something wrong");
        }
    }
    public void closeApp() {
        this.dispose();
        this.setVisible(false);
    }


}
