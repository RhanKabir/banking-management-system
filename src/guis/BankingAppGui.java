package guis;

import dbs_objs.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingAppGui extends BaseFrame implements ActionListener {

    private JTextField currentBalanceField;
    public JTextField getCurrentBalanceField(){
        return currentBalanceField;
    }

    public BankingAppGui(User user){
        super("Banking App", user);
    }

    @Override
    protected void addGuiComponents() {

        String welcomeMessage = "<html>" +
                "<body style='text-align:center'>" +
                "<b>Welcome, " + user.getUsername() + "</b><br>" +
                "Please choose an option below to continue</body></html>";

        JLabel welcomeMessageLabel = new JLabel(welcomeMessage);
        welcomeMessageLabel.setBounds(0, 20, getWidth() - 10, 40);
        welcomeMessageLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        welcomeMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(welcomeMessageLabel);

        JLabel currentBalanceLabel = new JLabel("Current Balance");
        currentBalanceLabel.setBounds(0, 80, getWidth() - 10, 40);
        currentBalanceLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        currentBalanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(currentBalanceLabel);

        currentBalanceField = new JTextField("$" + user.getCurrentBalance());
        currentBalanceField.setBounds(15, 120, getWidth() - 50, 40);
        currentBalanceField.setFont(new Font("Dialog", Font.BOLD, 20));
        currentBalanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        currentBalanceField.setEditable(false);

        add(currentBalanceField);

        JButton depositButton = new JButton("Deposit");
        depositButton.setBounds(15, 190, getWidth() - 50, 40);
        depositButton.setFont(new Font("Dialog", Font.BOLD, 20));
        depositButton.addActionListener(this);

        add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(15, 250, getWidth() - 50, 40);
        withdrawButton.setFont(new Font("Dialog", Font.BOLD, 20));
        withdrawButton.addActionListener(this);

        add(withdrawButton);

        JButton pastTransactionButton = new JButton("Past Transactions");
        pastTransactionButton.setBounds(15, 310, getWidth() - 50, 40);
        pastTransactionButton.setFont(new Font("Dialog", Font.BOLD, 20));
        pastTransactionButton.addActionListener(this);

        add(pastTransactionButton);

        JButton transferButton = new JButton("Transfer");
        transferButton.setBounds(15, 370, getWidth() - 50, 40);
        transferButton.setFont(new Font("Dialog", Font.BOLD, 20));
        transferButton.addActionListener(this);

        add(transferButton);

        JButton LogoutButton = new JButton("Logout");
        LogoutButton.setBounds(15, 490, getWidth() - 50, 40);
        LogoutButton.setFont(new Font("Dialog", Font.BOLD, 20));
        LogoutButton.addActionListener(this);

        add(LogoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();

        if(buttonPressed.equalsIgnoreCase("Logout")){
            new LoginGui().setVisible(true);

            this.dispose();

            return;
        }

        BankingAppDialog bankingAppDialog = new BankingAppDialog(this, user);

        bankingAppDialog.setTitle(buttonPressed);

        if(buttonPressed.equalsIgnoreCase("Deposit") || buttonPressed.equalsIgnoreCase("Withdraw")
                || buttonPressed.equalsIgnoreCase("Transfer")){
        bankingAppDialog.addCurrentBalanceAmount();
        bankingAppDialog.addActionButton(buttonPressed);

            if(buttonPressed.equalsIgnoreCase("Transfer")){
                bankingAppDialog.addUserField();
            }

            bankingAppDialog.setVisible(true);
        }else if(buttonPressed.equalsIgnoreCase("Past Transactions")){
            bankingAppDialog.addPastTransactionComponents();
            bankingAppDialog.setVisible(true);
        }

    }
}
