```java
LunarBreakerAPI lbAPI = LunarBreakerAPI.getInstance();
String message = ChatColor.GREEN + "You have joined the HCTeams queue.";

lbAPI.sendNotificationOrFallback(
    event.getPlayer(),
    new Notification(message, Duration.ofSeconds(1),
    () -> event.getPlayer().sendMessage(message)
);
```
