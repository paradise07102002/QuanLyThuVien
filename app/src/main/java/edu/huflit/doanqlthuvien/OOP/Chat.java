package edu.huflit.doanqlthuvien.OOP;

public class Chat {
    private int id_tin_nhan;
    private int id_nguoi_gui;
    private int id_nguoi_nhan;
    private String noi_dung;
    private String thoi_gian_gui;

    public int getId_tin_nhan() {
        return id_tin_nhan;
    }

    public void setId_tin_nhan(int id_tin_nhan) {
        this.id_tin_nhan = id_tin_nhan;
    }

    public int getId_nguoi_gui() {
        return id_nguoi_gui;
    }

    public void setId_nguoi_gui(int id_nguoi_gui) {
        this.id_nguoi_gui = id_nguoi_gui;
    }

    public int getId_nguoi_nhan() {
        return id_nguoi_nhan;
    }

    public void setId_nguoi_nhan(int id_nguoi_nhan) {
        this.id_nguoi_nhan = id_nguoi_nhan;
    }

    public String getNoi_dung() {
        return noi_dung;
    }

    public void setNoi_dung(String noi_dung) {
        this.noi_dung = noi_dung;
    }

    public String getThoi_gian_gui() {
        return thoi_gian_gui;
    }

    public void setThoi_gian_gui(String thoi_gian_gui) {
        this.thoi_gian_gui = thoi_gian_gui;
    }
}
