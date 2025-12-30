package view;

import controller.PayrollController;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;


public class LaporanFrame extends JFrame {
    private PayrollController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotalKaryawan, lblTotalGaji, lblRataGaji;
    private JComboBox<String> cmbFilter;
    private JButton btnRefresh, btnCetak, btnExport;

    public LaporanFrame() {
        controller = PayrollController.getInstance();
        initComponents();
        loadData();
        updateStatistik();
    }

    private void initComponents() {
        setTitle("Laporan Gaji Karyawan");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 240, 240));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(142, 68, 173));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        JLabel lblTitle = new JLabel("LAPORAN GAJI KARYAWAN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle);

        JPanel filterPanel = createFilterPanel();

        JPanel tablePanel = createTablePanel();

        JPanel statistikPanel = createStatistikPanel();

        JPanel buttonPanel = createButtonPanel();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(filterPanel, BorderLayout.NORTH);
        topPanel.add(tablePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statistikPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Filter"));

        JLabel lblFilter = new JLabel("Tampilkan:");
        lblFilter.setFont(new Font("Arial", Font.BOLD, 12));

        cmbFilter = new JComboBox<>(new String[] {
                "Semua Karyawan",
                "Karyawan Tetap",
                "Karyawan Kontrak"
        });
        cmbFilter.addActionListener(e -> loadData());

        panel.add(lblFilter);
        panel.add(cmbFilter);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder("Daftar Gaji Karyawan"));

        String[] columns = {
                "ID", "Nama", "Jenis Karyawan",
                "Gaji Pokok/Upah", "Tunjangan/Bonus",
                "Potongan", "Total Gaji"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 11));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 152, 219));
        table.getTableHeader().setForeground(Color.WHITE);

        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatistikPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel cardKaryawan = createStatCard("Total Karyawan",
                "0 Orang", new Color(52, 152, 219));
        lblTotalKaryawan = (JLabel) cardKaryawan.getComponent(1);

        JPanel cardGaji = createStatCard("Total Gaji",
                "Rp 0", new Color(46, 204, 113));
        lblTotalGaji = (JLabel) cardGaji.getComponent(1);

        JPanel cardRata = createStatCard("Rata-rata Gaji",
                "Rp 0", new Color(155, 89, 182));
        lblRataGaji = (JLabel) cardRata.getComponent(1);

        panel.add(cardKaryawan);
        panel.add(cardGaji);
        panel.add(cardRata);

        return panel;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.BOLD, 20));
        lblValue.setForeground(Color.WHITE);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(new Color(240, 240, 240));

        btnRefresh = createButton("Refresh", new Color(52, 152, 219));
        btnCetak = createButton("Cetak Laporan", new Color(46, 204, 113));
        btnExport = createButton("Export Excel", new Color(230, 126, 34));

        btnRefresh.addActionListener(e -> {
            loadData();
            updateStatistik();
        });

        btnCetak.addActionListener(e -> cetakLaporan());
        btnExport.addActionListener(e -> exportExcel());

        panel.add(btnRefresh);
        panel.add(btnCetak);
        panel.add(btnExport);

        return panel;
    }

private JButton createButton(String text, Color color) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 13));
    button.setBackground(color);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setOpaque(true);           // WAJIB
    button.setBorderPainted(false);   // WAJIB
    button.setPreferredSize(new Dimension(140, 35));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    Color hover = color.darker();

    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(hover);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(color);
        }
    });

    return button;
}


    private void loadData() {
        tableModel.setRowCount(0);
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        String filter = (String) cmbFilter.getSelectedItem();
        List<Karyawan> list = controller.getDaftarKaryawan();

        for (Karyawan k : list) {
            if (filter.equals("Karyawan Tetap") && !(k instanceof KaryawanTetap))
                continue;
            if (filter.equals("Karyawan Kontrak") && !(k instanceof KaryawanKontrak))
                continue;

            String gajiPokok = "";
            String tunjangan = "";
            String potongan = "Rp 0";

            if (k instanceof KaryawanTetap) {
                KaryawanTetap kt = (KaryawanTetap) k;
                gajiPokok = nf.format(kt.getGajiPokok());
                tunjangan = nf.format(kt.getTunjangan());
                double pot = (kt.getGajiPokok() / 30) * kt.getJumlahAlpha();
                potongan = nf.format(pot);
            } else if (k instanceof KaryawanKontrak) {
                KaryawanKontrak kk = (KaryawanKontrak) k;
                gajiPokok = nf.format(kk.getUpahPerHari() * kk.getJumlahHariKerja());
                tunjangan = nf.format(kk.getBonus());
            }

            double totalGaji = k.hitungGaji();

            tableModel.addRow(new Object[] {
                    k.getId(),
                    k.getNama(),
                    k.getJenisKaryawan(),
                    gajiPokok,
                    tunjangan,
                    potongan,
                    nf.format(totalGaji)
            });
        }
    }

    private void updateStatistik() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

        int totalKaryawan = 0;
        double totalGaji = 0;

        String filter = (String) cmbFilter.getSelectedItem();
        List<Karyawan> list = controller.getDaftarKaryawan();

        for (Karyawan k : list) {
            if (filter.equals("Karyawan Tetap") && !(k instanceof KaryawanTetap))
                continue;
            if (filter.equals("Karyawan Kontrak") && !(k instanceof KaryawanKontrak))
                continue;

            totalKaryawan++;
            totalGaji += k.hitungGaji();
        }

        double rataGaji = totalKaryawan > 0 ? totalGaji / totalKaryawan : 0;

        lblTotalKaryawan.setText(totalKaryawan + " Orang");
        lblTotalGaji.setText(nf.format(totalGaji));
        lblRataGaji.setText(nf.format(rataGaji));
    }

    private void cetakLaporan() {
        try {
            boolean complete = table.print(
                    JTable.PrintMode.FIT_WIDTH,
                    null,
                    null);

            if (complete) {
                JOptionPane.showMessageDialog(this,
                        "Laporan berhasil dicetak!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mencetak laporan: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportExcel() {
        JOptionPane.showMessageDialog(this,
                "Fitur export Excel akan segera tersedia!",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }
}