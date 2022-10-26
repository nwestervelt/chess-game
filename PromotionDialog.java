//class for the dialog box that appears when a pawn is being promoted
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PromotionDialog extends JDialog
{
    private int selectedButton;

    //static constants for selectedButton
    public static final int QUEEN = 1, KNIGHT = 2, BISHOP = 3, ROOK = 4;

    private JRadioButton queenRButton, knightRButton, bishopRButton, rookRButton;

    public PromotionDialog(JFrame parent)
    {
        super(parent, true);

        JPanel radioPanel = new JPanel();
        add(radioPanel, BorderLayout.CENTER);

        //create group for radio buttons
        ButtonGroup radioGroup = new ButtonGroup();

        //create radio buttons for each piece type
        queenRButton = new JRadioButton("Queen");
        queenRButton.setSelected(true);
        radioPanel.add(queenRButton);
        radioGroup.add(queenRButton);

        knightRButton = new JRadioButton("Knight");
        radioPanel.add(knightRButton);
        radioGroup.add(knightRButton);

        bishopRButton = new JRadioButton("Bishop");
        radioPanel.add(bishopRButton);
        radioGroup.add(bishopRButton);

        rookRButton = new JRadioButton("Rook");
        radioPanel.add(rookRButton);
        radioGroup.add(rookRButton);

        //create okay button
        JButton okayButton = new JButton("Okay");
        okayButton.addActionListener(new ActionHandler());
        add(okayButton, BorderLayout.SOUTH);

        //setup this dialog box's appearance/behavior
        setSize(300, 200);
        setTitle("Promotion");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    private class ActionHandler implements ActionListener
    {
        //action fired when "Okay" button clicked
        public void actionPerformed(ActionEvent e)
        {
            if(queenRButton.isSelected()) selectedButton = QUEEN;
            else if(knightRButton.isSelected()) selectedButton = KNIGHT;
            else if(bishopRButton.isSelected()) selectedButton = BISHOP;
            else if(rookRButton.isSelected()) selectedButton = ROOK;

            setVisible(false);
        }
    }
    public int getSelectedButton()
    {
        return selectedButton;
    }
}