package view;

import controller.PayrollController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private PayrollController controller;

    public LoginFrame() {
        controller = PayrollController.getInstance();
        initComponents();
    }

    private void initComponents() {
        setTitle("Login - Sistem Penggajian Karyawan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // ROOT PANEL
        JPanel root = new JPanel(new BorderLayout(0, 15));
        root.setBackground(new Color(245, 246, 250));
        root.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // HEADER
        JPanel header = new JPanel();
        header.setBackground(new Color(41, 128, 185));
        header.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel lblTitle = new JLabel("SISTEM PENGGAJIAN KARYAWAN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        header.setLayout(new BorderLayout());
        header.add(lblTitle, BorderLayout.CENTER);

        // CARD PANEL (FORM)
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)));

        // USERNAME
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUsername = new JTextField();
        styleField(txtUsername);

        // PASSWORD
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPassword = new JPasswordField();
        styleField(txtPassword);

        // BUTTON
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(46, 204, 113));
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setOpaque(true);
        btnLogin.setContentAreaFilled(true);
        btnLogin.setBorderPainted(false);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnLogin.setPreferredSize(new Dimension(350, 42));
        btnLogin.addActionListener(e -> handleLogin());

        // add components to card
        card.add(lblUser);
        card.add(Box.createVerticalStrut(6));
        card.add(txtUsername);
        card.add(Box.createVerticalStrut(15));

        card.add(lblPass);
        card.add(Box.createVerticalStrut(6));
        card.add(txtPassword);
        card.add(Box.createVerticalStrut(22));

        card.add(btnLogin);

        // INFO PANEL
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(new Color(245, 246, 250));

        JLabel lblInfo = new JLabel("Default: admin / admin123");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(120, 120, 120));
        infoPanel.add(lblInfo);

        // center wrapper to keep card centered
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(new Color(245, 246, 250));
        centerWrapper.add(card);

        // FIXED LAYOUT: header NORTH, form CENTER, info SOUTH
        root.add(header, BorderLayout.NORTH);
        root.add(centerWrapper, BorderLayout.CENTER);
        root.add(infoPanel, BorderLayout.SOUTH);

        setContentPane(root);

        pack();
        setSize(470, 360);
        setLocationRelativeTo(null);

        // Enter key listener
        txtPassword.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setPreferredSize(new Dimension(350, 40));

        field.setOpaque(true);
        field.setBackground(Color.WHITE);

        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
    }

    private void handleLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username dan password tidak boleh kosong!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (controller.login(username, password)) {
            JOptionPane.showMessageDialog(this,
                    "Login berhasil! Selamat datang, " + username,
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            new MainMenuFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username atau password salah!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginFrame().setVisible(true);
        });
    }
}