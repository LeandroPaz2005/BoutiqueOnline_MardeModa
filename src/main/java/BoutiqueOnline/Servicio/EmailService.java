
package BoutiqueOnline.servicio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendWelcomeEmailToGmailUser(String userEmail, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userEmail); // Enviar al cliente registrado
            message.setSubject("🎉 ¡Bienvenido a BoutiqueOnline!");
            message.setText(buildWelcomeMessage(userName));
            message.setFrom(fromEmail); // Debe coincidir con tu spring.mail.username

            mailSender.send(message);
            System.out.println("✅ Email de bienvenida enviado al cliente: " + userEmail);
        } catch (Exception e) {
            System.err.println("❌ Error enviando email de bienvenida: " + e.getMessage());
        }
    }

    private String buildWelcomeMessage(String userName) {
        return String.format(
            "¡Hola %s!\n\n" +
            "🎉 ¡Bienvenido a BoutiqueOnline!\n\n" +
            "Gracias por registrarte. Ahora puedes:\n\n" +
            "✅ Explorar nuestro catálogo\n" +
            "✅ Comprar de forma segura\n" +
            "✅ Recibir promociones exclusivas\n\n" +
            "¡Disfruta tu experiencia!\n\n" +
            "---\n" +
            "Equipo BoutiqueOnline\n" +
            "🌐 www.boutiqueonline.com\n" +
            "📧 soporte@boutiqueonline.com",
            userName != null ? userName : "cliente"
        );
    }
}
