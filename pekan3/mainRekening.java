package pekan3;

import java.util.ArrayList;
import java.util.Scanner;

public class mainRekening {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening> daftarRekening = new ArrayList<>();
		Rekening akunAktif =null;
		boolean isRunning = true;
		   
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		  
		while (isRunning) {
		System.out.println("\nMenu Utama:");
		System.out.println("1. Buka Rekening Baru");
		System.out.println("2. Setor Tunai");
		System.out.println("3. Tarik Tunai");
		System.out.println("4. Cek Informasi Rekening");
		System.out.println("5. Ganti Akun");
		System.out.println("6. Cek Mutasi (Riwayat)");
		System.out.println("0. Keluar");
		System.out.print("Pilih Menu: ");
		
		int pilihan = input.nextInt();
		input.nextLine();
	 	
		switch (pilihan) {
		case 1:
			System.out.print("Masukkan No Rekening: ");
			String no = input.nextLine();
			System.out.print("Masukkan Nama Pemilik: ");
			String nama = input.nextLine();
			System.out.print("Masukkan Saldo Awal: ");
			double saldo = input.nextDouble();
			input.nextLine();
			System.out.print("Buat PIN (6 digit): ");
			String pin = input.nextLine();
			
			akunAktif = new Rekening(no, nama, saldo, pin);
			daftarRekening.add(akunAktif);
			break;
			
		case 2:
			if (akunAktif == null) {
				System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
			} else {
				System.out.print("Masukkan nominal setor: ");
				double setor = input.nextDouble();
				akunAktif.setorTunai(setor);
			}
			break;
			
		case 3:
			if (akunAktif == null) {
				System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");
			} else {
				System.out.print("Masukkan PIN: ");
				String pinYangDiinput = input.nextLine();
				 
			if (akunAktif.otentikasi(pinYangDiinput)) {
				System.out.print("Masukkan nominal tarik tunai: ");
				double tarik = input.nextDouble();
				akunAktif.tarikTunai(tarik);
			
			akunAktif.tarikTunai(tarik);
	        } else {
	            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!"); }
			} 
			break;
			
		case 4:
			if (akunAktif == null) {
				System.out.println("Error: Anda belum membuka rekening!");
			} else {
				akunAktif.cekInformasi();
			}
		
			break;
        
		case 5:
            System.out.println("\n--- GANTI AKUN ---");

            if (daftarRekening.isEmpty()) {
                System.out.println("Belum ada rekening yang tersedia.");
                break;
            }

            System.out.print("Masukkan nomor rekening: ");
            String nomorCari = input.nextLine();

            boolean ditemukan = false;

            for (Rekening rekening : daftarRekening) {

                if (rekening.getNomorRekening().equals(nomorCari)) {
                    akunAktif = rekening;
                    ditemukan = true;

                    System.out.println("Berhasil berganti ke rekening atas nama "
                          + akunAktif.getNamaPemilik());

                    break;
                }
            }

            if (!ditemukan) {
                System.out.println("Rekening tidak ditemukan.");
            }
 
            break;
		
		case 6:
		    if (akunAktif == null) {
		        System.out.println("Error: Anda belum membuka rekening!");
		    } else {
		        System.out.print("Masukkan PIN: ");
		        String pinYangDiinput = input.nextLine();

		        if (akunAktif.otentikasi(pinYangDiinput)) {
		            akunAktif.cekMutasi();
		        } else {
		            System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
		        }
		    }
		    break;
			
		case 0:
			isRunning = false;
			System.out.println("Sistem ditutup. Terima kasih!");
			break;
			
		default:
			System.out.println("Pilihan tidak valid!");
			}
		}
		input.close();


		

}
}