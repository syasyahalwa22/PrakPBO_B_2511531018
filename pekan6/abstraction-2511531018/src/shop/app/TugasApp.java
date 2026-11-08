package shop.app;

import java.util.List;

import shop.StudentInfo;
import shop.model.Product;
import shop.model.PhysicalProduct;
import shop.model.DigitalProduct;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.payment.*;

import shop.notification.*;

public class TugasApp {

    public static void main(String[] args) {

        PhysicalProduct coffee = new PhysicalProduct("KOPI-001", "Kopi", 85000, 300);
        DigitalProduct ebook = new DigitalProduct("EBK-003", "Ebook", 50000, "https://example.com/ebook");

        List<Product> items = List.of(coffee, ebook);

        CheckoutService checkout = new CheckoutService();
        ExpressShipping shipping = new ExpressShipping();

        EWalletPayment ewallet = new EWalletPayment("DompetKu", 300000);
        BankTransferPayment transfer = new BankTransferPayment("Bank Nagari");
        CreditCardPayment card = new CreditCardPayment("Siti Rahma", 1000000);

        System.out.println("== T1: Total dengan biaya admin (Ekspres) ==");
        System.out.println("E-Wallet DompetKu : Rp" + checkout.grandTotal(items, shipping, ewallet));
        System.out.println("Transfer Bank Nagari : Rp" + checkout.grandTotal(items, shipping, transfer));
        System.out.println("Kartu Kredit (Siti Rahma) : Rp" + checkout.grandTotal(items, shipping, card));

        System.out.println();

        System.out.println("Kebijakan kartu kredit : " + card.policy());
        System.out.println("Kebijakan e-wallet : " + ewallet.policy());

        System.out.println("\n== T2: Notifikasi status pesanan ==");

        NotificationService service = new NotificationService();

        service.register(new EmailChannel());
        service.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        service.register(new WhatsAppChannel());

        service.register(new NotificationChannel() {

            @Override
            public String channelName() {
                return "LOG";
            }

            @Override
            public boolean send(CustomerContact contact, String message) {
                System.out.println("[LOG] " + contact.getName()
                    + ": " + message);
                return true;
            }
        });

        CustomerContact contact1 = new CustomerContact(
            "Siti",
            "siti@example.com",
            "081234567890"
        );

        CustomerContact contact2 = new CustomerContact(
            "Budi",
            null,
            "081234567891"
        );

        CustomerContact contact3 = new CustomerContact(
            "Ani",
            null,
            "+6281234567892"
        );

        String message = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. "
            + "Nomor resi akan muncul di aplikasi dalam 1x24 jam. "
            + "Terima kasih telah berbelanja produk UMKM Sumatera Barat di toko kami!";

        System.out.println("\nKontak pertama:");
        int result1 = service.broadcast(contact1, message);
        System.out.println("Terkirim ke " + result1 + " kanal");

        System.out.println("\nKontak kedua:");
        int result2 = service.broadcast(contact2, message);
        System.out.println("Terkirim ke " + result2 + " kanal");

        System.out.println("\nKontak ketiga:");
        int result3 = service.broadcast(contact3, message);
        System.out.println("Terkirim ke " + result3 + " kanal");
    }
}