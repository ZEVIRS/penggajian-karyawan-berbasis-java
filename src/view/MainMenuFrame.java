package view;

import controller.PayrollController;
import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private PayrollController controller;

    public MainMenuFrame() {
        controller = PayrollController.getInstance();
        initComponents();
    }

    private void initComponents() {
        setTitle("Menu Utama - Sistem Penggajian");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("SISTEM PENGGAJIAN KARYAWAN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblUser = new JLabel("User: " + controller.getCurrentUser());
        lblUser.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUser.setForeground(Color.WHITE);

        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(lblUser, BorderLayout.SOUTH);

        JPanel menuPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        menuPanel.setBackground(new Color(240, 240, 240));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnDataKaryawan = createMenuButton("Data Karyawan",
                new Color(52, 152, 219), "icon_employee.png");
        btnDataKaryawan.addActionListener(e -> openDataKaryawan());

        JButton btnHitungGaji = createMenuButton("Hitung Gaji",
                new Color(46, 204, 113), "icon_salary.png");
        btnHitungGaji.addActionListener(e -> openHitungGaji());

        JButton btnLaporan = createMenuButton("Laporan Gaji",
                new Color(155, 89, 182), "icon_report.png");
        btnLaporan.addActionListener(e -> openLaporan());

        JButton btnStatistik = createMenuButton("Statistik",
                new Color(230, 126, 34), "icon_stats.png");
        btnStatistik.addActionListener(e -> showStatistik());

        JButton btnPengaturan = createMenuButton("Pengaturan",
                new Color(149, 165, 166), "icon_settings.png");
        btnPengaturan.addActionListener(e -> showPengaturan());

        JButton btnKeluar = createMenuButton("Logout",
                new Color(231, 76, 60), "icon_logout.png");
        btnKeluar.addActionListener(e -> handleLogout());

        menuPanel.add(btnDataKaryawan);
        menuPanel.add(btnHitungGaji);
        menuPanel.add(btnLaporan);
        menuPanel.add(btnStatistik);
        menuPanel.add(btnPengaturan);
        menuPanel.add(btnKeluar);

JPanel footerPanel = new JPanel();
footerPanel.setBackground(new Color(240, 240, 240));
footerPanel.setLayout(new GridLayout(2, 1));

Font footerFont = new Font("Arial", Font.ITALIC, 14); // SATU FONT

JLabel lblFooterTitle = new JLabel("© 2025 Sistem Penggajian Karyawan", SwingConstants.CENTER);
JLabel lblFooterGroup = new JLabel("Kelompok 18", SwingConstants.CENTER);

lblFooterTitle.setFont(footerFont);
lblFooterGroup.setFont(footerFont);

lblFooterTitle.setForeground(Color.GRAY);
lblFooterGroup.setForeground(Color.GRAY);

footerPanel.add(lblFooterTitle);
footerPanel.add(lblFooterGroup);

mainPanel.add(headerPanel, BorderLayout.NORTH);
mainPanel.add(menuPanel, BorderLayout.CENTER);
mainPanel.add(footerPanel, BorderLayout.SOUTH);

add(mainPanel);

}

    private JButton createMenuButton(String text, Color color, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 80));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void openDataKaryawan() {
        new DataKaryawanFrame().setVisible(true);
    }

    private void openHitungGaji() {
        new HitungGajiFrame().setVisible(true);
    }

    private void openLaporan() {
        new LaporanFrame().setVisible(true);
    }

    private void showStatistik() {
        int totalKaryawan = controller.getDaftarKaryawan().size();
        double totalGaji = controller.hitungTotalGaji();

        String message = String.format(
                "=== STATISTIK PERUSAHAAN ===\n\n" +
                        "Total Karyawan: %d orang\n" +
                        "Total Gaji Bulanan: Rp %.2f\n" +
                        "Rata-rata Gaji: Rp %.2f",
                totalKaryawan,
                totalGaji,
                totalKaryawan > 0 ? totalGaji / totalKaryawan : 0);

        JOptionPane.showMessageDialog(this, message,
                "Statistik", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showPengaturan() {
        JOptionPane.showMessageDialog(this,
                "Fitur pengaturan sedang dalam pengembangan",
                "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin logout?",
                "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.logout();
            this.dispose();
            new LoginFrame().setVisible(true);
        }
    }
}