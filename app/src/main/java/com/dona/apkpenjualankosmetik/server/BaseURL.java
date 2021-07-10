package com.dona.apkpenjualankosmetik.server;

public class BaseURL {

    public static String baseURL = "http://192.168.160.94:5050/";

    public static String login = baseURL + "user/login";
    public static String register = baseURL + "user/registrasi";

    // bus
    public static String dataKosmetik = baseURL + "kosmetik/datakosmetik";
    public static String editDataKosmetik = baseURL + "kosmetik/ubah/";
    public static String editDataGambarKosmetik = baseURL + "kosmetik/ubahgambar/";
    public static String hapusData = baseURL + "kosmetik/hapus/";
    public static String inputBus = baseURL + "kosmetik/input";

    //cart
    public static String insertCart = baseURL+ "cart/list";
    public static String dataCart = baseURL+ "cart/dataCart/";
    public static String dataCartaja = baseURL+ "cart/dataCartaja/";
    public static String hapusCart = baseURL+ "cart/hapusCart/";

    // transaksi
    public static String dataTransaksi = baseURL + "transaksi/dataTransaksi";
    public static String hapusTransaksi = baseURL+ "transaksi/hapusTransaksi";
    public static String ubahTransaksi = baseURL +"transaks/ubahTransaksi";
    public static String ubahBUktiTF = baseURL + "transaksi/ubahBuktiTF";
}