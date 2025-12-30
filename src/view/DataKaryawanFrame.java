package view;

import controller.PayrollController;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DataKaryawanFrame extends JFrame {
    private PayrollController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtNama, txtAlamat, txtTelepon, txtEmail, txtTanggalMasuk;
    private JTextField txtGajiPokok, txtTunjangan, txtUpahPerHari;
    private JComboBox<String> cmbJenisKelamin, cmbJenisKaryawan;
    private JButton btnTambah, btnUpdate, btnHapus, btnClear, btnRefresh;

    public DataKaryawanFrame() {
        controller = PayrollController.getInstance();
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Data Karyawan");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitle = new JLabel("MANAJEMEN DATA KARYAWAN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel formPanel = createFormPanel();

        JPanel tablePanel = createTablePanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                formPanel, tablePanel);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.5);

        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Form Input Karyawan"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID Karyawan:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        txtId.setEnabled(false);
        panel.add(txtId, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Nama:"), gbc);
        gbc.gridx = 3;
        txtNama = new JTextField(15);
        panel.add(txtNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Jenis Kelamin:"), gbc);
        gbc.gridx = 1;
        cmbJenisKelamin = new JComboBox<>(new String[] { "Laki-laki", "Perempuan" });
        panel.add(cmbJenisKelamin, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Jenis Karyawan:"), gbc);
        gbc.gridx = 3;
        cmbJenisKaryawan = new JComboBox<>(new String[] { "Karyawan Tetap", "Karyawan Kontrak" });
        cmbJenisKaryawan.addActionListener(e -> toggleFields());
        panel.add(cmbJenisKaryawan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Alamat:"), gbc);
        gbc.gridx = 1;
        txtAlamat = new JTextField(15);
        panel.add(txtAlamat, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Telepon:"), gbc);
        gbc.gridx = 3;
        txtTelepon = new JTextField(15);
        panel.add(txtTelepon, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = new JTextField(15);
        panel.add(txtEmail, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Tanggal Masuk:"), gbc);
        gbc.gridx = 3;
        txtTanggalMasuk = new JTextField(15);
        txtTanggalMasuk.setToolTipText("Format: YYYY-MM-DD");
        panel.add(txtTanggalMasuk, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Gaji Pokok:"), gbc);
        gbc.gridx = 1;
        txtGajiPokok = new JTextField(15);
        panel.add(txtGajiPokok, gbc);

        gbc.gridx = 2;
        panel.add(new JLabel("Tunjangan:"), gbc);
        gbc.gridx = 3;
        txtTunjangan = new JTextField(15);
        panel.add(txtTunjangan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Upah Per Hari:"), gbc);
        gbc.gridx = 1;
        txtUpahPerHari = new JTextField(15);
        panel.add(txtUpahPerHari, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnTambah = createButton("Tambah", new Color(39, 174, 96));
        btnUpdate = createButton("Update", new Color(41, 128, 185));
        btnHapus = createButton("Hapus", new Color(192, 57, 43));
        btnClear = createButton("Clear", new Color(127, 140, 141));
        btnRefresh = createButton("Refresh", new Color(230, 126, 34));

        btnTambah.addActionListener(e -> tambahKaryawan());
        btnUpdate.addActionListener(e -> updateKaryawan());
        btnHapus.addActionListener(e -> hapusKaryawan());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> loadData());

        buttonPanel.add(btnTambah);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);

        panel.add(buttonPanel, gbc);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Daftar Karyawan"));

        String[] columns = { "ID", "Nama", "Jenis", "Jenis Kelamin", "Telepon", "Email" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedData();
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

 private JButton createButton(String text, Color color) {
    JButton button = new JButton(text);
    button.setBackground(color);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setOpaque(true);
    button.setBorderPainted(false);
    button.setPreferredSize(new Dimension(110, 35));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    Color hoverColor = color.darker();

    button.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            button.setBackground(hoverColor);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            button.setBackground(color);
        }
    });

    return button;
}


    private void toggleFields() {
        boolean isTetap = cmbJenisKaryawan.getSelectedItem().equals("Karyawan Tetap");
        txtGajiPokok.setEnabled(isTetap);
        txtTunjangan.setEnabled(isTetap);
        txtUpahPerHari.setEnabled(!isTetap);

        if (isTetap) {
            txtUpahPerHari.setText("");
        } else {
            txtGajiPokok.setText("");
            txtTunjangan.setText("");
        }
    }

    private void tambahKaryawan() {
        try {
            String id = controller.generateIdKaryawan();
            String nama = txtNama.getText().trim();
            String alamat = txtAlamat.getText().trim();
            String telepon = txtTelepon.getText().trim();
            String email = txtEmail.getText().trim();
            String jenisKelamin = (String) cmbJenisKelamin.getSelectedItem();
            String tanggalMasuk = txtTanggalMasuk.getText().trim();

            if (nama.isEmpty() || alamat.isEmpty()) {
                throw new Exception("Nama dan alamat harus diisi!");
            }

            Karyawan karyawan;
            if (cmbJenisKaryawan.getSelectedItem().equals("Karyawan Tetap")) {
                double gajiPokok = Double.parseDouble(txtGajiPokok.getText());
                double tunjangan = Double.parseDouble(txtTunjangan.getText());
                karyawan = new KaryawanTetap(id, nama, alamat, telepon, email,
                        jenisKelamin, tanggalMasuk, gajiPokok, tunjangan);
            } else {
                double upahPerHari = Double.parseDouble(txtUpahPerHari.getText());
                karyawan = new KaryawanKontrak(id, nama, alamat, telepon, email,
                        jenisKelamin, tanggalMasuk, upahPerHari);
            }

            controller.tambahKaryawan(karyawan);
            JOptionPane.showMessageDialog(this, "Karyawan berhasil ditambahkan!");
            clearForm();
            loadData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateKaryawan() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih karyawan yang akan diupdate!");
            return;
        }

        try {
            String id = txtId.getText();
            tambahKaryawan();
            controller.hapusKaryawan(id);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapusKaryawan() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih karyawan yang akan dihapus!");
            return;
        }

        String id = (String) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Hapus karyawan " + id + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.hapusKaryawan(id);
            JOptionPane.showMessageDialog(this, "Karyawan berhasil dihapus!");
            clearForm();
            loadData();
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
        txtEmail.setText("");
        txtTanggalMasuk.setText("");
        txtGajiPokok.setText("");
        txtTunjangan.setText("");
        txtUpahPerHari.setText("");
        table.clearSelection();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Karyawan> list = controller.getDaftarKaryawan();
        for (Karyawan k : list) {
            tableModel.addRow(new Object[] {
                    k.getId(),
                    k.getNama(),
                    k.getJenisKaryawan(),
                    k.getJenisKelamin(),
                    k.getTelepon(),
                    k.getEmail()
            });
        }
    }

    private void loadSelectedData() {
        int row = table.getSelectedRow();
        if (row == -1)
            return;

        String id = (String) tableModel.getValueAt(row, 0);
        Karyawan k = controller.cariKaryawan(id);

        if (k != null) {
            txtId.setText(k.getId());
            txtNama.setText(k.getNama());
            txtAlamat.setText(k.getAlamat());
            txtTelepon.setText(k.getTelepon());
            txtEmail.setText(k.getEmail());
            cmbJenisKelamin.setSelectedItem(k.getJenisKelamin());
            txtTanggalMasuk.setText(k.getTanggalMasuk());

            if (k instanceof KaryawanTetap) {
                cmbJenisKaryawan.setSelectedItem("Karyawan Tetap");
                KaryawanTetap kt = (KaryawanTetap) k;
                txtGajiPokok.setText(String.valueOf(kt.getGajiPokok()));
                txtTunjangan.setText(String.valueOf(kt.getTunjangan()));
            } else {
                cmbJenisKaryawan.setSelectedItem("Karyawan Kontrak");
                KaryawanKontrak kk = (KaryawanKontrak) k;
                txtUpahPerHari.setText(String.valueOf(kk.getUpahPerHari()));
            }
        }
    }
}