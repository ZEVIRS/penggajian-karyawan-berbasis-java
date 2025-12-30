package model;

public class KaryawanTetap extends Karyawan {
    private double gajiPokok;
    private double tunjangan;
    private int jumlahHadir;
    private int jumlahAlpha;

    public KaryawanTetap(String id, String nama, String alamat, String telepon,
            String email, String jenisKelamin, String tanggalMasuk,
            double gajiPokok, double tunjangan) {
        super(id, nama, alamat, telepon, email, jenisKelamin, tanggalMasuk);
        this.gajiPokok = gajiPokok;
        this.tunjangan = tunjangan;
        this.jumlahHadir = 0;
        this.jumlahAlpha = 0;
    }

    @Override
    public double hitungGaji() {
        double potonganAlpha = (gajiPokok / 30) * jumlahAlpha;
        return gajiPokok + tunjangan - potonganAlpha;
    }

    @Override
    public String getJenisKaryawan() {
        return "Karyawan Tetap";
    }

    public double getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(double gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public double getTunjangan() {
        return tunjangan;
    }

    public void setTunjangan(double tunjangan) {
        this.tunjangan = tunjangan;
    }

    public int getJumlahHadir() {
        return jumlahHadir;
    }

    public void setJumlahHadir(int jumlahHadir) {
        this.jumlahHadir = jumlahHadir;
    }

    public int getJumlahAlpha() {
        return jumlahAlpha;
    }

    public void setJumlahAlpha(int jumlahAlpha) {
        this.jumlahAlpha = jumlahAlpha;
    }
}