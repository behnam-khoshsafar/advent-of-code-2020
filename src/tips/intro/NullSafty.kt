package tips.intro

fun sendMessageToClient(
    client: Client?, message: String?, mailer: Mailer
) {
    val email = client?.personalInfo?.email
    if (email != null && message != null)
        mailer.sendMessage(email, message) // compiler knows you checked the null in if statement
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}

/*
public void sendMessageToClient(
    @Nullable Client client,
    @Nullable String message,
    @NotNull Mailer mailer
) {
    if (client == null || message == null) return;

    PersonalInfo personalInfo = client.getPersonalInfo();
    if (personalInfo == null) return;

    String email = personalInfo.getEmail();
    if (email == null) return;

    mailer.sendMessage(email, message);
}
* */