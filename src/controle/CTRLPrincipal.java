package controle;
import visao.FRMLogin;

public class CTRLPrincipal {
    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(() -> {
            
            new FRMLogin().setVisible(true);
        });
}
}
