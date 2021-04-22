/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.*;
import View.viewUser_Pengajar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author ACER
 */
public class HandlerUser_Pengajar implements ActionListener {
    private viewUser_Pengajar pengajar;

    public HandlerUser_Pengajar() {
        pengajar = new viewUser_Pengajar();
        pengajar.setVisible(true);
        pengajar.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if(source.equals(pengajar.getComboTahuntbhPengajar())){
            showComboTanggalTbhPengajar();
            showComboBulanTbhPengajar();
        } else if(source.equals(pengajar.getComboBulantbhPengajar())){
            showComboTanggalTbhPengajar();
        } else if (source.equals(pengajar.getTambahjadwalpengajar())){
            jadwalPengajar k = new jadwalPengajar();
            Database db = new Database();
            cek x = new cek();
            String d;
            if (Integer.parseInt(pengajar.getComboTanggaltbhPengajar().getSelectedItem().toString()) < 10  ) {
                d = "0"+((String)pengajar.getComboTanggaltbhPengajar().getSelectedItem());
            }else {
                d = ((String)pengajar.getComboTanggaltbhPengajar().getSelectedItem());
            }
            String Tanggal = pengajar.getComboTahuntbhPengajar().getSelectedItem()+"-"+x.cekNomorBulan((String)pengajar.getComboBulantbhPengajar().getSelectedItem())+"-"+d;
            if (x.cekjadwalPengajar_idpengajar_tangal(pengajar.getTampidPengajar().getText(), Tanggal)==false && x.cekkelasWithmentor_idpengajar_tangal(pengajar.getTampidPengajar().getText(), Tanggal) == false){ 
                String tampId = pengajar.getTampidPengajar().getText()+"-"+x.randomNumberGenerator();
                k.setIdJadwal(tampId);
                k.setJadwal(Tanggal);
                k.setIdPengajar(pengajar.getTampidPengajar().getText());
                db.addJadwalPengajar(k);
                showTableJadwalPengajar();
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Anda telah membuat jadwal kosong di tanggal tersebut // anda telah memiliki jadwal di tanggal tersebut");
            }
        } else if (source.equals(pengajar.getKembaliUserPengajar())) {
            HandlerUser_LogIn f = new HandlerUser_LogIn();
            pengajar.dispose();
        } else if (source.equals(pengajar.getTambahMateri())) {
            if (pengajar.getIdKelasTambah().getText().isEmpty()==false) {
                cek x = new cek();
                withMentor w = x.cariKelas_idKelasWithmentor(pengajar.getIdKelasTambah().getText());
                if (w!=null) {
                    HandlerTambah_Materi h = new HandlerTambah_Materi();
                    h.showUser_DetailKelasJoinWindow(w, pengajar.getTampidPengajar().getText());
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Id Kelas Tidak Ditemukan");
                }
                pengajar.getIdKelasTambah().setText("");
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Silahkan isi textfield terlebih dahulu");
            }
        }
    }
    
     public void showComboTanggalTbhPengajar(){
        Date now =new Date();        
        pengajar.getComboTanggaltbhPengajar().removeAllItems();
        cek c = new cek();
        
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
        if ((String) pengajar.getComboTahuntbhPengajar().getSelectedItem()!=null) {
            int tahun = Integer.parseInt((String) pengajar.getComboTahuntbhPengajar().getSelectedItem());
            String thn = dateFormatter.format(now);

            String bulan = ((String) pengajar.getComboBulantbhPengajar().getSelectedItem());
            int bln = now.getMonth()+1;
            String bulannow = c.cekNamaBulan(bln);

            if(bulan=="Januari" || bulan=="Maret" || bulan=="Mei" || bulan=="Juli" || bulan=="Agustus" || bulan=="Oktober" || bulan=="Desember"){
                if(tahun==Integer.parseInt(thn) && bulannow==bulan){
                    for (int i = now.getDate()+1; i <= 31; i++) {
                        pengajar.getComboTanggaltbhPengajar().addItem(Integer.toString(i));
                    }
                } else {
                    for (int i = 1; i <= 31; i++) {
                        pengajar.getComboTanggaltbhPengajar().addItem(Integer.toString(i));

                    }
                }
            } else if(bulan=="Februari" && c.cekKabisat(tahun)==true){
                if(tahun==Integer.parseInt(thn)&& bulannow==bulan){
                    for (int i = now.getDate()+1; i <= 29; i++) {
                        pengajar.getComboTanggaltbhPengajar().addItem(Integer.toString(i));
                    }
                } else{
                    for (int i = 1; i <= 29; i++) {
                        pengajar.getComboTanggaltbhPengajar().addItem(Integer.toString(i));
                    }
                }
            } else if(bulan=="Februari" && c.cekKabisat(tahun)==false){
                if(tahun==Integer.parseInt(thn)&& bulannow==bulan){
                    for (int i = now.getDate()+1; i <= 28; i++) {
                        pengajar.getComboTanggaltbhPengajar().addItem(Integer.toString(i));
                    }
                } else{
                    for (int i = 1; i <= 28; i++) {
                        pengajar.getComboTanggaltbhPengajar().addItem(Integer.toString(i));
                    }
                }
            } else {
               if(tahun==Integer.parseInt(thn)&& bulannow==bulan){
                    for (int i = now.getDate()+1; i <= 30; i++) {
                        pengajar.getComboTanggaltbhPengajar().addItem(Integer.toString(i));
                    }
                } else {
                    for (int i = 1; i <= 30; i++) {
                        pengajar.getComboTanggaltbhPengajar().addItem(Integer.toString(i));
                    }
                }
            }
        }
    }
     
    public void setPengajarWindow(Pengajar m) {
        pengajar.getTampidPengajar().setText(m.getIdPengajar());
        
        showTableJadwalPengajar();
        showComboBulanTbhPengajar();
        showComboTahun();
        showtanggal();
        showComboTanggalTbhPengajar();
        showTableKelas();
    }
     
    public void namaBulanPengajar(int x){
        cek c = new cek();
        for (int i = x; i <= 12; i++) {
            pengajar.getComboBulantbhPengajar().addItem(c.cekNamaBulan(i));
        }
    }
     
    public void showComboBulanTbhPengajar(){
        pengajar.getComboBulantbhPengajar().removeAllItems();
        Date now =new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
        if ((String) pengajar.getComboTahuntbhPengajar().getSelectedItem()!=null) {
            int tahun = Integer.parseInt((String) pengajar.getComboTahuntbhPengajar().getSelectedItem());
            String thn = dateFormatter.format(now);
            
            if (tahun==Integer.parseInt(thn)) {
                namaBulanPengajar(now.getMonth()+1);
            } else{
                namaBulanPengajar(1);
            }
        }
    }
    
    public void showTableJadwalPengajar(){
        cek c = new cek();
        if (!"...".equals(pengajar.getTampidPengajar().getText())) {
            Database db = new Database();
            int i = 0;
            ResultSet rs = null;
            try{

                rs = db.getData("select * from jadwal_pengajar where idPengajar='"+pengajar.getTampidPengajar().getText()+"';");
                DefaultTableModel model = (DefaultTableModel) pengajar.getTableJadwal().getModel();
                model.setRowCount(0);
                while (rs.next()){
                    model.setRowCount(i+1);
                    pengajar.getTableJadwal().setValueAt(rs.getString("idJadwal"), i, 0);
                    pengajar.getTableJadwal().setValueAt(rs.getString("jadwal"), i, 1);
                    pengajar.getTableJadwal().setValueAt(rs.getString("idPengajar"), i, 2);
                    i=i+1;
                }
                rs.close();
            } catch(Exception e){
                javax.swing.JOptionPane.showMessageDialog(null, "Error");
            }
        }
    }
    
    public void showComboTahun(){
        pengajar.getComboTahuntbhPengajar().removeAllItems();
        Date now = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
        int tahun =Integer.parseInt(dateFormatter.format(now));
        for (int i = 0; i <= 1; i++) {
            pengajar.getComboTahuntbhPengajar().addItem(Integer.toString(tahun+i));
        }
    }
    
    public void showtanggal(){
        Date now = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEEEE dd / MM / yyyy");
        pengajar.getTanggal().setText(dateFormatter.format(now));
    }
    
    public void showTableKelas(){
        Database db = new Database();
        int i = 0;
        ResultSet rs = null;
        try{
            rs = db.getData("select * from kelas_withmentor where idPengajar ='"+pengajar.getTampidPengajar().getText()+"';");
            DefaultTableModel model = (DefaultTableModel) pengajar.getTabelKelasPengajar().getModel();
            cek x = new cek();
            model.setRowCount(0);
            while (rs.next()){
                model.setRowCount(i+1);
                Curriculum c = x.cariCurriculum_idCurriculum(rs.getString("idCurriculum"));
                Matkul m = c.getMatkul();
                pengajar.getTabelKelasPengajar().setValueAt(rs.getString("idKelas"), i, 0);
                pengajar.getTabelKelasPengajar().setValueAt(rs.getString("namaKelas"), i, 1);
                pengajar.getTabelKelasPengajar().setValueAt(rs.getString("deskripsiKelas"), i, 2);
                pengajar.getTabelKelasPengajar().setValueAt(m.getIdMatkul()+"  "+m.getNamaMatkul(), i, 3);
                pengajar.getTabelKelasPengajar().setValueAt(rs.getString("jumAnggota")+"/"+rs.getString("maxAnggota"), i, 4);
                pengajar.getTabelKelasPengajar().setValueAt(rs.getString("tanggal"), i, 5);
                pengajar.getTabelKelasPengajar().setValueAt(rs.getString("jam"), i, 6);
                pengajar.getTabelKelasPengajar().setValueAt(rs.getString("Lokasi"), i, 7);
                i=i+1;
            }
            rs.close();
        } catch(Exception e){
            javax.swing.JOptionPane.showMessageDialog(null, "Error");
        }
    }
    
    public static void main(String[] args) {
        new HandlerUser_Pengajar();
    }
    
    
}
