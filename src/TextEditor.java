import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextEditor extends JFrame implements ActionListener {


    JTextArea textArea;
    JTextArea newTextArea;
    JButton selectButton = new JButton();
 //   JButton saveButton = new JButton();

    TextEditor() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Text Editor");
        this.setSize(800,800);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);

        selectButton.addActionListener(this);

        this.add(selectButton);

        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(600,600));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        new JTextArea();

      //  newTextArea = readFile("output");
      //  if(newTextArea != null) {
     //       textArea.setText(newTextArea.getText());
     //   }

        this.add(textArea);
        this.pack();
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
                if(newTextArea != null) {
                    textArea.setText(newTextArea.getText());
                }
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
}
