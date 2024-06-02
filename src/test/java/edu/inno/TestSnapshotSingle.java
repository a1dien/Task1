package edu.inno;

import edu.inno.exception.UndoException;
import edu.inno.snapshot.AccountManager;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.slf4j.LoggerFactory.getLogger;

public class TestSnapshotSingle {
    final Logger log = getLogger(lookup().lookupClass());
    String nameF = "Vasya";
    String nameS = "Boris";
    String nameT = "Petya";
    Account account = new Account(nameF);
    AccountManager manager = new AccountManager();

    @Test
    void assertRestoreSnapshot() {
        log.debug("Изменяем имя и делаем снимок");
        account.setName(nameS);
        manager.add(account.saveMemento());
        assertThat(account.toString(), equalTo("Account(name=" + nameS + ", deposit={})"));
        log.debug("Изменяем имя 3й раз и восстанавливаем снимок");
        account.setName(nameT);
        assertThat(account.toString(), equalTo("Account(name=" + nameT + ", deposit={})"));
        account.restoreMemento(manager.get(0));
        assertThat(account.toString(), equalTo("Account(name=" + nameS + ", deposit={})"));
        log.debug("Проверяем, что мы можем еще десериализовать объект до первоначального состояния.");
        try {
            account = account.undo();
            assertThat(account.toString(), equalTo("Account(name=" + nameF + ", deposit={})"));
        } catch (UndoException e) {
            throw new RuntimeException(e);
        }
    }

}
