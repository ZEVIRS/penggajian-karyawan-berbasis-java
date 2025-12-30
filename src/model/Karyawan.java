package model;

public abstract class Karyawan {
    protected String id;
    protected String nama;
    protected String alamat;
    protected String telepon;
    protected String email;
    protected String jenisKelamin;
    protected String tanggalMasuk;
    
    public Karyawan(String id, String nama, String alamat, String telepon, 
                    String email, String jenisKelamin, String tanggalMasuk) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.telepon = telepon;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
        this.tanggalMasuk = tanggalMasuk;
    }
    
    public abstract double hitungGaji();
    public abstract String getJenisKaryawan();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    
    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }
    
    public String getTanggalMasuk() { return tanggalMasuk; }
    public void setTanggalMasuk(String tanggalMasuk) { this.tanggalMasuk = tanggalMasuk; }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Nama: " + nama + ", Jenis: " + getJenisKaryawan();
    }
}