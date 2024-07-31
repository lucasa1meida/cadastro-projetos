package orla.cadastroprojetos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles("test")
public class CadastroProjetosApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadastroProjetosApplication.class, args);
    }

}
