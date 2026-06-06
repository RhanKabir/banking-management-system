package guis;

import dbs_objs.MyJDBC;
import dbs_objs.Transaction;
import dbs_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BankingAppDialog extends JDialog implements ActionListener {
    private User user;
    private BankingAppGui bankingAppGui;
    private JLabel balanceLabel, enterAmountLabel, userTransferLabel;
    public JTextField enterAmountField, userTransferField;
    public JButton actionButton;
    private JPanel pastTransactionPanel;
    private ArrayList<Transaction> pastTransactions;

    public BankingAppDialog(BankingAppGui bankingAppGui, User user){
        setSize(400, 400);

        setModal(true);

        setLocationRelativeTo(bankingAppGui);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setResizable(false);

        setLayout(null);

        this.bankingAppGui = bankingAppGui;

        this.user = user;

    }

    public void addCurrentBalanceAmount(){

        balanceLabel = new JLabel("Balance: $" + user.getCurrentBalance());
        balanceLabel.setBounds(0, 10, getWidth() - 20, 20);
        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(balanceLabel);

        enterAmountLabel = new JLabel("Enter Amount: ");
        enterAmountLabel.setBounds(15, 50, getWidth() - 20, 20);
        enterAmountLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        add(enterAmountLabel);

        enterAmountField = new JTextField();
        enterAmountField.setBounds(15, 90, getWidth() - 50, 40);
        enterAmountField.setHorizontalAlignment(SwingConstants.CENTER);
        enterAmountField.setFont(new Font("Dialog", Font.BOLD, 20));
        add(enterAmountField);

    }

    public void addActionButton(String actionButtonType){
        actionButton = new JButton(actionButtonType);
        actionButton.setBounds(15, 300, getWidth() - 50, 40);
        actionButton.setHorizontalAlignment(SwingConstants.CENTER);
        actionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        actionButton.addActionListener(this);
        add(actionButton);
    }

    public void addUserField(){
        userTransferLabel = new JLabel("Enter user: ");
        userTransferLabel.setBounds(15, 160, getWidth() - 20, 20);
        userTransferLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        add(userTransferLabel);

        userTransferField = new JTextField();
        userTransferField.setBounds(15, 200, getWidth() - 50, 40);
        userTransferField.setFont(new Font("Dialog", Font.BOLD, 20));
        userTransferField.setHorizontalAlignment(SwingConstants.CENTER);
        add(userTransferField);
    }

    public void addPastTransactionComponents(){
        pastTransactionPanel = new JPanel();

        pastTransactionPanel.setLayout(new BoxLayout(pastTransactionPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(pastTransactionPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        scrollPane.setBounds(0, 20, getWidth() - 15, getHeight() - 15);

        pastTransactions = MyJDBC.getPastTransaction(user);

        for(int i = 0; i < pastTransactions.size(); i++){
            Transaction pastTransaction = pastTransactions.get(i);

            JPanel pastTransactionContainer = new JPanel();
            pastTransactionContainer.setLayout(new BorderLayout());

            JLabel transactionTypeLabel = new JLabel(pastTransaction.getTransactionType());
            transactionTypeLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel transactionAmountLabel = new JLabel(String.valueOf(pastTransaction.getTransactionAmount()));
            transactionAmountLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            JLabel transactionDateLabel = new JLabel(String.valueOf(pastTransaction.getTransactionDate()));
            transactionDateLabel.setFont(new Font("Dialog", Font.BOLD, 20));

            pastTransactionContainer.add(transactionTypeLabel, BorderLayout.WEST);
            pastTransactionContainer.add(transactionAmountLabel, BorderLayout.EAST);
            pastTransactionContainer.add(transactionDateLabel, BorderLayout.SOUTH);

            pastTransactionContainer.setBackground(Color.WHITE);

            pastTransactionContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            pastTransactionPanel.add(pastTransactionContainer);
        }

        add(scrollPane);
    }

    private void handleTransaction(String transactionType, float amountVal){
        Transaction transaction;

        if(transactionType.equalsIgnoreCase("Deposit")){
            user.setCurrentBalance(user.getCurrentBalance().add(new BigDecimal(amountVal)));

            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(amountVal), null);
        }
        else{
            user.setCurrentBalance(user.getCurrentBalance().subtract(new BigDecimal(amountVal)));
            transaction = new Transaction(user.getId(), transactionType, new BigDecimal(-amountVal), null);
        }

        if(MyJDBC.addTransactionToDatabase(transaction) && MyJDBC.updateCurrentBalance(user)){
            JOptionPane.showMessageDialog(this, transactionType + "Success");
            resetFieldsAndUpdateCurrentBalance();
        }else{
            JOptionPane.showMessageDialog(this, transactionType + "Failed");
        }

    }

    private void resetFieldsAndUpdateCurrentBalance(){
        enterAmountField.setText("");
        if(userTransferField != null){
            userTransferField.setText("");
        }

        balanceLabel.setText("Balance: $" + user.getCurrentBalance());
        bankingAppGui.getCurrentBalanceField().setText("Balance: $" + user.getCurrentBalance());
    }

    private void handleTransfer(User user, String transferredUser, float amount){
        if(MyJDBC.transfer(user ,transferredUser, amount)){
            JOptionPane.showMessageDialog(this, "Successful transfer");
            resetFieldsAndUpdateCurrentBalance();
        }else{
            JOptionPane.showMessageDialog(this, "Transfer failed");
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        float amountVal = Float.parseFloat(enterAmountField.getText());

        if(buttonPressed.equalsIgnoreCase("Deposit")){

            handleTransaction(buttonPressed, amountVal);
        }else{
            int result = user.getCurrentBalance().compareTo(BigDecimal.valueOf(amountVal));

            if(result < 0){
                JOptionPane.showMessageDialog(this, "Input greater than balance");
                return;
            }

            if(buttonPressed.equalsIgnoreCase("Withdraw")){
                handleTransaction(buttonPressed, amountVal);
            }else{
                String transferredUser = userTransferField.getText();

                handleTransfer(user, transferredUser, amountVal);
            }


        }

    }
}
