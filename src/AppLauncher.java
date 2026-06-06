import dbs_objs.User;
import guis.BankingAppGui;
import guis.LoginGui;
import guis.RegisterGui;

import javax.swing.*;
import java.math.BigDecimal;

public class AppLauncher {
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                LoginGui gui = new LoginGui();
                gui.setVisible(true);

//                RegisterGui gui1 = new RegisterGui();
//                gui1.setVisible(true);

//                BankingAppGui gui2 = new BankingAppGui(new User(1, "rikab", "haguman123", new BigDecimal(20.00)));
//                gui2.setVisible(true);
            }
        });
    }
}
