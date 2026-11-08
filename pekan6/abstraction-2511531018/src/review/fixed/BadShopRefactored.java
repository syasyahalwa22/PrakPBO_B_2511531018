package review.fixed;

import java.util.ArrayList;
import java.util.List;

// PERBAIKAN 1: Interface dipisahkan sesuai tanggung jawab
interface PaymentOperation {
    boolean pay(long amount);
}

interface InvoiceOperation {
    String printInvoice();
}

interface ShippingOperation {
    long ship(int weightGrams);
}

class GiftVoucher implements PaymentOperation, InvoiceOperation {
    private double balance = 100000.0;

    @Override
    public boolean pay(long amount) {
        if (amount > balance || amount <= 0) {
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public String printInvoice() {
        return "Voucher, remaining balance " + balance;
    }
}

// PERBAIKAN 2: Menggunakan enum untuk jenis pengiriman
enum ShippingType {
    REGULAR,
    EXPRESS,
    SAME_DAY
}

abstract class Courier {
    public long cost(ShippingType type, int kilograms) {
        switch (type) {
            case REGULAR:
                return kilograms * 10000L;
            case EXPRESS:
                return kilograms * 18000L;
            case SAME_DAY:
                return kilograms * 30000L;
            default:
                return 0;
        }
    }
}

class JneCourier extends Courier {
}

class Cart {
    private final List<Item> items = new ArrayList<>();

    class Item {
        private final String name;
        private final long price;
        private final int quantity;

        Item(String name, long price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        long subtotal() {
            return price * quantity;
        }

        String getName() {
            return name;
        }
    }

    void add(Item item) {
        items.add(item);
    }

    long total() {
        long total = 0;
        for (Item item : items) {
            total += item.subtotal();
        }
        return total;
    }
}

// PERBAIKAN 3: Address dibuat immutable
final class Address {
    private final String street;
    private final String city;

    Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    @Override
    public String toString() {
        return street + ", " + city;
    }
}

class SimpleOrder {
    private final String id;
    private final Address address;

    SimpleOrder(String id, Address address) {
        this.id = id;
        this.address = address;
    }

    @Override
    public String toString() {
        return id + " -> " + address;
    }
}

interface Discount {
    default long apply(long price) {
        if (this instanceof MemberDiscount) {
            return price - price * 5 / 100;
        } else if (this instanceof FlashSaleDiscount) {
            return price - price * 30 / 100;
        }
        return price;
    }
}

class MemberDiscount implements Discount {
}

class FlashSaleDiscount implements Discount {
}

public class BadShopRefactored {
    public static void main(String[] args) {

        Cart cart = new Cart();
        cart.add(cart.new Item("Kopi Gayo", 85000, 2));

        System.out.println("Total cart: " + cart.total());

        Courier courier = new JneCourier();

        System.out.println(
            "Ongkir Ekspres 2 kg: "
            + courier.cost(ShippingType.EXPRESS, 2)
        );

        Address home = new Address("Jl. Sudirman 10", "Padang");
        Address otherHome = new Address("Jl. Sudirman 10", "Bukittinggi");

        SimpleOrder first = new SimpleOrder("ORD-1", home);
        SimpleOrder second = new SimpleOrder("ORD-2", otherHome);

        System.out.println(first);
        System.out.println(second);

        Discount flashSale = new FlashSaleDiscount();
        System.out.println("Harga flash sale: " + flashSale.apply(100000));

        PaymentOperation voucher = new GiftVoucher();

        System.out.println(
            voucher.pay(30000) + " | "
            + ((InvoiceOperation) voucher).printInvoice()
        );
    }
}