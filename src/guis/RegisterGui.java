package guis;

import dbs_objs.MyJDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterGui extends BaseFrame{

    public RegisterGui(){
        super("Banking App Register");
    }

    @Override
    protected void addGuiComponents() {
        JLabel bankingAppLabel = new JLabel("User Register");

        bankingAppLabel.setBounds(0, 20, super.getWidth(), 40);
        bankingAppLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        bankingAppLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(bankingAppLabel);

        JLabel usernameLabel = new JLabel("Username: ");

        usernameLabel.setBounds(20, 120, super.getWidth() - 30, 24);
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        add(usernameLabel);

        JTextField usernameField = new JTextField();

        usernameField.setBounds(20, 160, getWidth() - 50, 40);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 28));

        add(usernameField);

        JLabel passwordLabel = new JLabel("Password: ");

        passwordLabel.setBounds(20, 220, super.getWidth() - 30, 24);
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();

        passwordField.setBounds(20, 260, getWidth() - 50, 40);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 28));

        add(passwordField);

        JLabel rePasswordLabel = new JLabel("Retype password:");
        rePasswordLabel.setBounds(20, 320, super.getWidth() - 30, 24);
        rePasswordLabel.setFont(new Font("Dialog", Font.PLAIN, 20));

        add(rePasswordLabel);

        JPasswordField rePasswordField = new JPasswordField();

        rePasswordField.setBounds(20, 360, getWidth() - 50, 40);
        rePasswordField.setFont(new Font("Dialog", Font.PLAIN, 28));

        add(rePasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 470, getWidth() - 50, 40);
        registerButton.setFont(new Font("Dialog", Font.BOLD, 20));
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                String rePassword = String.valueOf(rePasswordField.getPassword());

                if(validateUserInput(username, password, rePassword)){
                    if(MyJDBC.register(username, password)){
                        RegisterGui.this.dispose();
                        LoginGui newLoginGui = new LoginGui();
                        newLoginGui.setVisible(true);

                        JOptionPane.showMessageDialog(newLoginGui, "Account Registered");
                    }
                    else{
                        JOptionPane.showMessageDialog(RegisterGui.this, "Username Taken");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(RegisterGui.this,
                            "Username must be at least 6 characters\n" +
                                    "and/or Passwords must match"
                    );
                }

            }
        });

        add(registerButton);

        JLabel loginLabel = new JLabel("<html><a href =\"#\">Sign-in here</a>");
        loginLabel.setBounds(20, 410, getWidth() - 10, 40);
        loginLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                RegisterGui.this.dispose();
                new LoginGui().setVisible(true);
            }
        });

        add(loginLabel);

    }

    private boolean validateUserInput(String username, String password, String rePassword){

        if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
            return false;
        }

        if(username.length() < 6){
            return false;
        }

        if(!password.equals(rePassword)){
            return false;
        }

        return true;
    }
}
