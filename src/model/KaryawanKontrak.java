package model;

public class KaryawanKontrak extends Karyawan {
    private double upahPerHari;
    private int jumlahHariKerja;
    private double bonus;

    public KaryawanKontrak(String id, String nama, String alamat, String telepon,
            String email, String jenisKelamin, String tanggalMasuk,
            double upahPerHari) {
        super(id, nama, alamat, telepon, email, jenisKelamin, tanggalMasuk);
        this.upahPerHari = upahPerHari;
        this.jumlahHariKerja = 0;
        this.bonus = 0;
    }

    @Override
    public double hitungGaji() {
        return (upahPerHari * jumlahHariKerja) + bonus;
    }

    @Override
    public String getJenisKaryawan() {
        return "Karyawan Kontrak";
    }

    public double getUpahPerHari() {
        return upahPerHari;
    }

    public void setUpahPerHari(double upahPerHari) {
        this.upahPerHari = upahPerHari;
    }

    public int getJumlahHariKerja() {
        return jumlahHariKerja;
    }

    public void setJumlahHariKerja(int jumlahHariKerja) {
        this.jumlahHariKerja = jumlahHariKerja;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }
}