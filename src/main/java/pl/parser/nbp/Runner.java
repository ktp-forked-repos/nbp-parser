package pl.parser.nbp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.parser.nbp.core.NBPExchangeManager;
import pl.parser.nbp.model.CurrencyCode;

import java.time.LocalDate;
import java.util.Arrays;

import static java.lang.String.format;
import static java.util.Objects.isNull;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private NBPExchangeManager nbpExchangeManager;

    @Override
    public void run(String... strings) {
        if (isArgumentValid(strings)) {
            nbpExchangeManager.handleExchangeRate(CurrencyCode.valueOf(strings[0]), LocalDate.parse(strings[1]), LocalDate.parse(strings[2]));
        }
    }

    private boolean isArgumentValid(String[] args) {
        if (isNull(args) || args.length < 3) {
            sendMessageToUser("No enough arguments");
            System.exit(0);
        }
        try {
            CurrencyCode.valueOf(args[0]);
            LocalDate start = LocalDate.parse(args[1]);
            LocalDate end   = LocalDate.parse(args[2]);
            if (start.isAfter(end)) {
                sendMessageToUser("Start date cannot be after end");
                return Boolean.FALSE;
            }
            if (LocalDate.now().minusYears(15).isAfter(start)) {
                sendMessageToUser("We can only calculate for 15 years back");
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            sendMessageToUser(format("Cannot validate input arguments: %s", e.getMessage()));
            return Boolean.FALSE;
        }
    }

    private void sendMessageToUser(String message) {
        System.out.println(message);
        System.out.println("Example of correct program execution: ");
        System.out.println("java -jar NbpParser.jar EUR 2013-01-28 2013-01-31");
    }
}
