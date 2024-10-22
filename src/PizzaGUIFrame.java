import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {
    private final JTextArea textArea;
    private final JComboBox<String> sizeComboBox;
    private final JRadioButton thinCrustButton, regularCrustButton, deepDishButton;
    private final JCheckBox[] toppings;
    private final ButtonGroup crustGroup;
    private final static String[] sizes = {"Small", "Medium", "Large", "Super"};
    private final static double[] sizePrices = {8.00, 12.00, 16.00, 20.00};
    private final static String[] toppingsOptions = {"Pepperoni", "Pineapple", "Sausage", "Mushrooms", "Ham", "Onions"};
    private final static double TAX_RATE = 0.07;

    public PizzaGUIFrame() {
        setTitle("Pizza Order Form");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crust selection panel
        JPanel crustPanel = new JPanel(new GridLayout(1, 3));
        crustPanel.setBorder(BorderFactory.createTitledBorder("Crust Type"));
        thinCrustButton = new JRadioButton("Thin");
        regularCrustButton = new JRadioButton("Regular");
        deepDishButton = new JRadioButton("Deep-dish");
        crustGroup = new ButtonGroup();
        crustGroup.add(thinCrustButton);
        crustGroup.add(regularCrustButton);
        crustGroup.add(deepDishButton);
        crustPanel.add(thinCrustButton);
        crustPanel.add(regularCrustButton);
        crustPanel.add(deepDishButton);

        // Size selection panel
        JPanel sizePanel = new JPanel();
        sizePanel.setBorder(BorderFactory.createTitledBorder("Size"));
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);

        // Toppings selection panel
        JPanel toppingsPanel = new JPanel();
        toppingsPanel.setLayout(new GridLayout(3, 2));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder("Toppings"));
        toppings = new JCheckBox[toppingsOptions.length];
        for (int i = 0; i < toppingsOptions.length; i++) {
            toppings[i] = new JCheckBox(toppingsOptions[i]);
            toppingsPanel.add(toppings[i]);
        }

        // Order display panel
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton orderButton = new JButton("Order");
        JButton clearButton = new JButton("Clear");
        JButton quitButton = new JButton("Quit");
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        // Add panels to frame
        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.EAST);
        add(orderPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add listeners
        orderButton.addActionListener(this::handleOrder);
        clearButton.addActionListener(e -> clearForm());
        quitButton.addActionListener(e -> exitForm());
    }

    private void handleOrder(ActionEvent e) {
        StringBuilder receipt = new StringBuilder();
        double totalCost = 0.0;

        // Get selected crust and its cost
        JRadioButton selectedCrust = null;
        if (thinCrustButton.isSelected()) selectedCrust = thinCrustButton;
        else if (regularCrustButton.isSelected()) selectedCrust = regularCrustButton;
        else if (deepDishButton.isSelected()) selectedCrust = deepDishButton;

        if (selectedCrust != null) {
            receipt.append("Crust: ").append(selectedCrust.getText()).append("\n");
        }

        // Get selected size and its cost
        int selectedIndex = sizeComboBox.getSelectedIndex();
        totalCost += sizePrices[selectedIndex];
        receipt.append("Size: ").append(sizes[selectedIndex]).append(" - $").append(sizePrices[selectedIndex]).append("\n");

        // Add toppings
        receipt.append("Toppings:\n");
        for (JCheckBox topping : toppings) {
            if (topping.isSelected()) {
                receipt.append(" - ").append(topping.getText()).append(" - $1.00\n");
                totalCost += 1.00;
            }
        }

        // Calculate subtotal, tax, and total
        receipt.append("-----------------------------------------\n");
        receipt.append(String.format("Sub-total: $%.2f\n", totalCost));
        double tax = totalCost * TAX_RATE;
        receipt.append(String.format("Tax: $%.2f\n", tax));
        double finalTotal = totalCost + tax;
        receipt.append(String.format("Total: $%.2f\n", finalTotal));
        receipt.append("=========================================\n");

        textArea.setText(receipt.toString());
    }

    private void clearForm() {
        crustGroup.clearSelection();
        sizeComboBox.setSelectedIndex(0);
        for (JCheckBox topping : toppings) {
            topping.setSelected(false);
        }
        textArea.setText("");
    }

    private void exitForm() {
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}