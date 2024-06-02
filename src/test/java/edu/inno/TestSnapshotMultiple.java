package edu.inno;

import edu.inno.resources.Currency;
import edu.inno.snapshot.AccountManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;
public class TestSnapshotMultiple {
    final Logger log = getLogger(lookup().lookupClass());


    @Test
    void assertRestoreMultipleAccount() {
        log.debug("Проверка создания 3 аккаунтов, снятия снимков и загрузки после изменений.");
        Account account1 = new Account("Vasya");
        AccountManager manager1 = new AccountManager();
        manager1.add(account1.saveMemento());

        Account account2 = new Account("Boris");
        AccountManager manager2 = new AccountManager();
        manager2.add(account2.saveMemento());

        Account account3 = new Account("Petya");
        AccountManager manager3 = new AccountManager();
        manager3.add(account3.saveMemento());

        account1.setName("Vasya1");
        manager1.add(account1.saveMemento());
        account1.addMoney(Currency.RUB, 1000);
        manager1.add(account1.saveMemento());

        account2.setName("Boris1");
        manager2.add(account2.saveMemento());
        account2.addMoney(Currency.USD, 1);
        manager2.add(account2.saveMemento());

        account3.setName("Petya1");
        manager3.add(account3.saveMemento());
        account3.addMoney(Currency.EUR, 10);
        manager3.add(account3.saveMemento());

        assertThat(account1.getMoney().toString(), equalTo("{" + Currency.RUB + "=" + 1000 + "}"));
        assertThat(account2.getMoney().toString(), equalTo("{" + Currency.USD + "=" + 1 + "}"));
        assertThat(account3.getMoney().toString(), equalTo("{" + Currency.EUR + "=" + 10 + "}"));

        account1.restoreMemento(manager1.get(1));
        account2.restoreMemento(manager2.get(1));
        account3.restoreMemento(manager3.get(1));

        assertThat(account1.toString(), equalTo("Account(name=" + "Vasya1" + ", deposit={})"));
        assertThat(account2.toString(), equalTo("Account(name=" + "Boris1" + ", deposit={})"));
        assertThat(account3.toString(), equalTo("Account(name=" + "Petya1" + ", deposit={})"));


    }
}
