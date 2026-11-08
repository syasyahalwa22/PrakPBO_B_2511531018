package shop.notification;

public class EmailChannel extends AbstractChannel {
    public EmailChannel() {
    }

    @Override
    public String channelName() {
        return "Email";
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        String email = contact.getEmail();

        if (email.contains("@")) {
            return email;
        }
        return null;
    }
    @Override
    protected String prepare(String message) {
        return message;
    }
    @Override
    protected boolean deliver(String recipient, String message) {
        System.out.println("Email dikirim ke " + recipient);
        System.out.println("Pesan: " + message);
        return true;
    }

}
