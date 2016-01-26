package src;

public class team extends javax.swing.JFrame {

    public team() {
        initComponents();
    }

    private void initComponents() {

    	setTitle("About");
        jLabel1 = new javax.swing.JLabel();

        jLabel1.setIcon(new javax.swing.ImageIcon("summarizer.png"));
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 600, 420);
        setResizable(false);
       
        pack();
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new team().setVisible(true);
            }
        });
    }

    private javax.swing.JLabel jLabel1;

}
