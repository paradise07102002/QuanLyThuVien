package edu.huflit.doanqlthuvien.OOP;

public class Chat {
    private int id_tin_nhan;
    private int id_chat_user;
    private int id_chat_admin;
    private int id_gui;
    private String noi_dung;
    private String thoi_gian_gui;
    private int count_new_messsage;

    public int getId_tin_nhan() {
        return id_tin_nhan;
    }

    public void setId_tin_nhan(int id_tin_nhan) {
        this.id_tin_nhan = id_tin_nhan;
    }

    public int getId_chat_user() {
        return id_chat_user;
    }

    public void setId_chat_user(int id_chat_user) {
        this.id_chat_user = id_chat_user;
    }

    public int getId_chat_admin() {
        return id_chat_admin;
    }

    public void setId_chat_admin(int id_chat_admin) {
        this.id_chat_admin = id_chat_admin;
    }

    public int getId_gui() {
        return id_gui;
    }

    public void setId_gui(int id_gui) {
        this.id_gui = id_gui;
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

    public int getCount_new_messsage() {
        return count_new_messsage;
    }

    public void setCount_new_messsage(int count_new_messsage) {
        this.count_new_messsage = count_new_messsage;
    }
}
