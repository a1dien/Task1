package edu.inno;

import edu.inno.resources.Currency;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class TestAccountAddMoney {
    final Logger log = getLogger(lookup().lookupClass());
    Currency currency = Currency.RUB;
    Currency currency2 = Currency.EUR;
    int amount = 1000;
    int amount2 = 1111;
    int amount3 = -1;
    Account account = new Account("Vasya");

    @Test
    void assertAddMoneyNew() {
        account.addMoney(currency, amount);
        log.debug("Проверка после добавления 1000 рублей");
        assertThat(account.getMoney(currency), equalTo(amount));
        assertThat(account.getMoney().toString(), equalTo("{" + currency + "=" + amount + "}"));
    }

    @Test
    void assertAddMoneyReplace(){
        int newAmount = amount + 1;
        account.addMoney(currency, newAmount);
        log.debug("Проверка после добавления 1001 рублей при уже сохраненной 1000");
        assertThat(account.getMoney(currency), equalTo(newAmount));
        assertThat(account.getMoney().toString(), equalTo("{" + currency + "=" + newAmount + "}"));
    }
    @Test
    void assertAddMoneyNewCurrency() {
        account.addMoney(currency2, amount2);
        log.debug("Проверка после добавления 1111 долларов");
        assertThat(account.getMoney(currency2), equalTo(amount2));
        assertThat(account.getMoney().toString(), equalTo("{" + currency2 + "=" + amount2 + "}"));
    }

    @Test
    void assertAddMoneyNegative() {
        log.debug("Проверка добавления отрицательной суммы на счет");
        try {
            account.addMoney(currency, amount3);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(),equalTo("Сумма на счете может быть равна или больше нуля."));
        }
    }

}
