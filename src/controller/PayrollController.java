package controller;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class PayrollController {
    private static PayrollController instance;
    private List<Karyawan> daftarKaryawan;
    private String currentUser;

    private PayrollController() {
        daftarKaryawan = new ArrayList<>();
        initializeDummyData();
    }

    public static PayrollController getInstance() {
        if (instance == null) {
            instance = new PayrollController();
        }
        return instance;
    }

    private void initializeDummyData() {
        tambahKaryawan(new KaryawanTetap("K001", "Budi Santoso", "Jl. Merdeka No. 10",
                "081234567890", "budi@email.com", "Laki-laki", "2020-01-15",
                5000000, 1000000));

        tambahKaryawan(new KaryawanKontrak("K002", "Siti Nurhaliza", "Jl. Sudirman No. 25",
                "082345678901", "siti@email.com", "Perempuan", "2023-06-01",
                200000));

        KaryawanTetap kt = (KaryawanTetap) daftarKaryawan.get(0);
        kt.setJumlahHadir(22);
        kt.setJumlahAlpha(2);

        KaryawanKontrak kk = (KaryawanKontrak) daftarKaryawan.get(1);
        kk.setJumlahHariKerja(20);
        kk.setBonus(500000);
    }

    public boolean login(String username, String password) {
        if (username.equals("admin") && password.equals("admin123")) {
            currentUser = username;
            return true;
        }
        return false;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public void tambahKaryawan(Karyawan karyawan) {
        daftarKaryawan.add(karyawan);
    }

    public boolean hapusKaryawan(String id) {
        return daftarKaryawan.removeIf(k -> k.getId().equals(id));
    }

    public Karyawan cariKaryawan(String id) {
        for (Karyawan k : daftarKaryawan) {
            if (k.getId().equals(id)) {
                return k;
            }
        }
        return null;
    }

    public boolean updateKaryawan(String id, Karyawan karyawanBaru) {
        for (int i = 0; i < daftarKaryawan.size(); i++) {
            if (daftarKaryawan.get(i).getId().equals(id)) {
                daftarKaryawan.set(i, karyawanBaru);
                return true;
            }
        }
        return false;
    }

    public List<Karyawan> getDaftarKaryawan() {
        return new ArrayList<>(daftarKaryawan);
    }

    public String generateIdKaryawan() {
        int maxNum = 0;
        for (Karyawan k : daftarKaryawan) {
            String id = k.getId();
            if (id.startsWith("K")) {
                try {
                    int num = Integer.parseInt(id.substring(1));
                    if (num > maxNum)
                        maxNum = num;
                } catch (NumberFormatException e) {
                }
            }
        }
        return String.format("K%03d", maxNum + 1);
    }

    public double hitungTotalGaji() {
        double total = 0;
        for (Karyawan k : daftarKaryawan) {
            total += k.hitungGaji();
        }
        return total;
    }
}