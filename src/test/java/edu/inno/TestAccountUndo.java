package edu.inno;
import edu.inno.exception.UndoException;
import edu.inno.resources.Currency;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.slf4j.LoggerFactory.getLogger;
public class TestAccountUndo {
    final Logger log = getLogger(lookup().lookupClass());
    String nameF = "Vasya";
    String nameS = "Petya";
    Currency currency = Currency.RUB;
    int amount = 1000;
    Account account = new Account(nameF);


    @Test
    void assertUndoName() throws UndoException {
        log.debug("Проверка отката имени.");
        account.setName(nameS);
        log.debug("Проверка, что у счета новое имя");
        assertThat(account.toString(), equalTo("Account(name=" + nameS + ", deposit={})"));
        account = account.undo();
        log.debug("Проверка, что у счета вернулось старое имя");
        assertThat(account.toString(), equalTo("Account(name=" + nameF + ", deposit={})"));
    }
    @Test
    void assertUndoMoney() throws UndoException {
        log.debug("Проверка отката депозита.");
        account.addMoney(currency, amount);
        log.debug("Проверка, что у счета добавились деньги.");
        assertThat(account.toString(), equalTo("Account(name="
                + nameF
                + ", deposit={"
                + currency
                + "="
                + amount + "})"));
        account = account.undo();
        log.debug("Проверка, что у счета больше нет денег.");
        assertThat(account.toString(), equalTo("Account(name=" + nameF + ", deposit={})"));
    }
    @Test
    void assertUndoError() {
        log.debug(" Попытка отменить изменения, если их не было — это ошибка.");
        try{
            account.undo();
        } catch (UndoException e) {
            assertThat(e.getMessage(),equalTo("Откат дальше невозможен. Счет находится в своем изначальном состоянии."));
        }
    }
}
