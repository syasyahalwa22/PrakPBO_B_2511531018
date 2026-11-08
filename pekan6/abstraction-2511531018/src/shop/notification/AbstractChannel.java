package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount = 0;
    private String lastDelivered = "";
    protected abstract String resolveRecipient(CustomerContact contact);
    protected abstract String prepare(String message);
    protected abstract boolean deliver(String recipient, String message);

    @Override
    public final boolean send(CustomerContact contact, String message) {
        if (contact == null || message == null || message.isBlank()) {
            return false;
        }
        String recipient = resolveRecipient(contact);
        if (recipient == null || recipient.isBlank()) {
            return false;
        }
        String preparedMessage = prepare(message);
        if (deliver(recipient, preparedMessage)) {
            sentCount++;
            lastDelivered = preparedMessage;
            return true;
        }
        return false;
    }

    public int getSentCount() {
        return sentCount;
    }
    public String getLastDelivered() {
        return lastDelivered;
    }

}
