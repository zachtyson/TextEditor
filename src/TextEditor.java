import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextEditor extends JFrame implements ActionListener {


    JTextArea textArea;
    JTextArea newTextArea;
    JButton selectButton = new JButton("Select");
    JButton saveButton = new JButton("Save");

    TextEditor() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Text Editor");
        this.setSize(800,800);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        selectButton.addActionListener(this);
        saveButton.addActionListener(this);

        this.add(selectButton);
        this.add(saveButton);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(600,600));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(200,200,200));
        textArea.setBorder(BorderFactory.createBevelBorder(1));
        textArea.setForeground(Color.black);
        textArea.setFont(new Font ("Times New Roman",Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600,600));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        this.add(textArea);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectButton) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showOpenDialog(null);
            if(response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                newTextArea = readFile(file);
                System.out.println(file);

            }
        }
        if (e.getSource() == saveButton) {
            JFileChooser fileChooser = new JFileChooser();
            int response = fileChooser.showSaveDialog(null);
            if(response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                saveFile(file, textArea.getText());
            }
        }
    }

    public static JTextArea readFile(File fileName) {

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
    public void saveFile(File file, String textAreaString) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(textAreaString);
            writer.close();
        } catch (IOException a) {
            System.out.println("Something wrong");
        }
    }

}
