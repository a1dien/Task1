package edu.inno;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.slf4j.LoggerFactory.getLogger;
public class TestAccountName {
    final Logger log = getLogger(lookup().lookupClass());
    @Test
    void assertCreateAccountWithEmptyName(){
        log.debug("Проверка создания аккаунта с пустым именем.");
        try{
            Account account = new Account(" ");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(),equalTo("Имя владельца не может быть пустым."));
        }
    }
}
