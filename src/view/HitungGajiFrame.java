package view;

import controller.PayrollController;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class HitungGajiFrame extends JFrame {
    private PayrollController controller;
    private JComboBox<String> cmbKaryawan;
    private JTextField txtJumlahHadir, txtJumlahAlpha, txtJumlahHariKerja, txtBonus;
    private JTextArea txtDetail;
    private JLabel lblGajiTotal;
    private JButton btnHitung, btnSimpan, btnReset;
    private Karyawan selectedKaryawan;

    public HitungGajiFrame() {
        controller = PayrollController.getInstance();
        initComponents();
        loadKaryawan();
    }

    private void initComponents() {
        setTitle("Perhitungan Gaji Karyawan");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("PERHITUNGAN GAJI KARYAWAN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(new Color(41, 128, 185));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        JPanel inputPanel = createInputPanel();

        JPanel detailPanel = createDetailPanel();

        JPanel buttonPanel = createButtonPanel();

        JPanel totalPanel = createTotalPanel();

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(detailPanel, BorderLayout.CENTER);

        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(totalPanel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Pilih Karyawan & Input Data Kehadiran",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(52, 152, 219)));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblKaryawan = new JLabel("Pilih Karyawan:");
        lblKaryawan.setFont(new Font("Arial", Font.BOLD, 13));
        panel.add(lblKaryawan, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        cmbKaryawan = new JComboBox<>();
        cmbKaryawan.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbKaryawan.addActionListener(e -> onKaryawanSelected());
        panel.add(cmbKaryawan, gbc);

        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Jumlah Hadir:"), gbc);
        gbc.gridx = 1;
        txtJumlahHadir = new JTextField(10);
        panel.add(txtJumlahHadir, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(new JLabel("Jumlah Alpha:"), gbc);
        gbc.gridx = 3;
        txtJumlahAlpha = new JTextField(10);
        panel.add(txtJumlahAlpha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Hari Kerja:"), gbc);
        gbc.gridx = 1;
        txtJumlahHariKerja = new JTextField(10);
        panel.add(txtJumlahHariKerja, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(new JLabel("Bonus:"), gbc);
        gbc.gridx = 3;
        txtBonus = new JTextField(10);
        panel.add(txtBonus, gbc);

        return panel;
    }

    private JPanel createDetailPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(46, 204, 113), 2),
                "Detail Perhitungan Gaji",
                0, 0, new Font("Arial", Font.BOLD, 14), new Color(46, 204, 113)));
        panel.setBackground(Color.WHITE);

        txtDetail = new JTextArea(10, 50);
        txtDetail.setEditable(false);
        txtDetail.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtDetail.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(txtDetail);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTotalPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(41, 128, 185));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel lblText = new JLabel("TOTAL GAJI: ");
        lblText.setFont(new Font("Arial", Font.BOLD, 18));
        lblText.setForeground(Color.WHITE);

        lblGajiTotal = new JLabel("Rp 0");
        lblGajiTotal.setFont(new Font("Arial", Font.BOLD, 24));
        lblGajiTotal.setForeground(Color.YELLOW);

        panel.add(lblText);
        panel.add(lblGajiTotal);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(Color.WHITE);

        btnHitung = createButton("Hitung Gaji", new Color(46, 204, 113));
        btnSimpan = createButton("Simpan Data", new Color(52, 152, 219));
        btnReset = createButton("Reset", new Color(149, 165, 166));

        btnHitung.addActionListener(e -> hitungGaji());
        btnSimpan.addActionListener(e -> simpanData());
        btnReset.addActionListener(e -> resetForm());

        panel.add(btnHitung);
        panel.add(btnSimpan);
        panel.add(btnReset);

        return panel;
    }

private JButton createButton(String text, Color color) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBackground(color);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setOpaque(true);              // WAJIB
    button.setBorderPainted(false);      // WAJIB
    button.setPreferredSize(new Dimension(140, 40));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    Color hover = color.darker();
    Color pressed = hover.darker();

    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(hover);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(color);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            button.setBackground(pressed);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            button.setBackground(hover);
        }
    });

    return button;
}

    private void loadKaryawan() {
        cmbKaryawan.removeAllItems();
        for (Karyawan k : controller.getDaftarKaryawan()) {
            cmbKaryawan.addItem(k.getId() + " - " + k.getNama());
        }
    }

    private void onKaryawanSelected() {
        if (cmbKaryawan.getSelectedItem() == null)
            return;

        String selected = (String) cmbKaryawan.getSelectedItem();
        String id = selected.split(" - ")[0];
        selectedKaryawan = controller.cariKaryawan(id);

        if (selectedKaryawan instanceof KaryawanTetap) {
            txtJumlahHadir.setEnabled(true);
            txtJumlahAlpha.setEnabled(true);
            txtJumlahHariKerja.setEnabled(false);
            txtBonus.setEnabled(false);

            KaryawanTetap kt = (KaryawanTetap) selectedKaryawan;
            txtJumlahHadir.setText(String.valueOf(kt.getJumlahHadir()));
            txtJumlahAlpha.setText(String.valueOf(kt.getJumlahAlpha()));
        } else {
            txtJumlahHadir.setEnabled(false);
            txtJumlahAlpha.setEnabled(false);
            txtJumlahHariKerja.setEnabled(true);
            txtBonus.setEnabled(true);

            KaryawanKontrak kk = (KaryawanKontrak) selectedKaryawan;
            txtJumlahHariKerja.setText(String.valueOf(kk.getJumlahHariKerja()));
            txtBonus.setText(String.valueOf(kk.getBonus()));
        }

        txtDetail.setText("");
        lblGajiTotal.setText("Rp 0");
    }

    private void hitungGaji() {
        if (selectedKaryawan == null) {
            JOptionPane.showMessageDialog(this, "Pilih karyawan terlebih dahulu!");
            return;
        }

        try {
            StringBuilder detail = new StringBuilder();
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

            detail.append("==============================================\n");
            detail.append("           SLIP GAJI KARYAWAN\n");
            detail.append("==============================================\n\n");
            detail.append("ID Karyawan    : ").append(selectedKaryawan.getId()).append("\n");
            detail.append("Nama           : ").append(selectedKaryawan.getNama()).append("\n");
            detail.append("Jenis Karyawan : ").append(selectedKaryawan.getJenisKaryawan()).append("\n");
            detail.append("\n----------------------------------------------\n");
            detail.append("RINCIAN GAJI:\n");
            detail.append("----------------------------------------------\n");

            if (selectedKaryawan instanceof KaryawanTetap) {
                KaryawanTetap kt = (KaryawanTetap) selectedKaryawan;
                kt.setJumlahHadir(Integer.parseInt(txtJumlahHadir.getText()));
                kt.setJumlahAlpha(Integer.parseInt(txtJumlahAlpha.getText()));

                double gajiPokok = kt.getGajiPokok();
                double tunjangan = kt.getTunjangan();
                double potonganAlpha = (gajiPokok / 30) * kt.getJumlahAlpha();

                detail.append("Gaji Pokok     : ").append(nf.format(gajiPokok)).append("\n");
                detail.append("Tunjangan      : ").append(nf.format(tunjangan)).append("\n");
                detail.append("Jumlah Hadir   : ").append(kt.getJumlahHadir()).append(" hari\n");
                detail.append("Jumlah Alpha   : ").append(kt.getJumlahAlpha()).append(" hari\n");
                detail.append("Potongan Alpha : ").append(nf.format(potonganAlpha)).append("\n");
            } else {
                KaryawanKontrak kk = (KaryawanKontrak) selectedKaryawan;
                kk.setJumlahHariKerja(Integer.parseInt(txtJumlahHariKerja.getText()));
                kk.setBonus(Double.parseDouble(txtBonus.getText()));

                double upahPerHari = kk.getUpahPerHari();
                double totalUpah = upahPerHari * kk.getJumlahHariKerja();

                detail.append("Upah per Hari  : ").append(nf.format(upahPerHari)).append("\n");
                detail.append("Jumlah Hari    : ").append(kk.getJumlahHariKerja()).append(" hari\n");
                detail.append("Total Upah     : ").append(nf.format(totalUpah)).append("\n");
                detail.append("Bonus          : ").append(nf.format(kk.getBonus())).append("\n");
            }

            double totalGaji = selectedKaryawan.hitungGaji();

            detail.append("\n==============================================\n");
            detail.append("TOTAL GAJI     : ").append(nf.format(totalGaji)).append("\n");
            detail.append("==============================================\n");

            txtDetail.setText(detail.toString());
            lblGajiTotal.setText(nf.format(totalGaji));

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void simpanData() {
        if (selectedKaryawan == null) {
            JOptionPane.showMessageDialog(this, "Pilih karyawan terlebih dahulu!");
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Data gaji berhasil disimpan!",
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetForm() {
        cmbKaryawan.setSelectedIndex(0);
        txtJumlahHadir.setText("0");
        txtJumlahAlpha.setText("0");
        txtJumlahHariKerja.setText("0");
        txtBonus.setText("0");
        txtDetail.setText("");
        lblGajiTotal.setText("Rp 0");
    }
}