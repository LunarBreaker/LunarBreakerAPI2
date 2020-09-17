```java
public class Example {
    public void sendQueueNotification(Player player, String message) {
        String message = ChatColor.GREEN + "You have joined the " + queueName + " queue.";
        
        LunarBreakerAPI.getInstance().getNotificationHandler().sendNotificationOrFallback(
                player,
                new Notification(message, Duration.ofSeconds(1)),
                () -> player.sendMessage(message)
        );
    }
}
```
