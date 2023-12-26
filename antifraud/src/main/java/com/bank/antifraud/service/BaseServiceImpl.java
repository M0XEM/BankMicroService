package com.bank.antifraud.service;

import com.bank.antifraud.entity.abstractclasses.SuspiciousTransfer;
import com.bank.antifraud.entity.abstractclasses.Transfer;
import com.bank.antifraud.exception.SuspiciousAccountTransferNotFoundException;
import com.bank.antifraud.repository.suspicioustransfer.BaseSuspiciousRepository;
import com.bank.antifraud.repository.transfer.BaseTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public abstract class BaseServiceImpl<T extends SuspiciousTransfer, S extends Transfer> {

    abstract BaseSuspiciousRepository<T> getSuspiciousRepository();
    abstract BaseTransferRepository<S> getTransferRepository();
    abstract T getEntity();


    @Transactional
    public void create(T t) {
        try {
            getSuspiciousRepository().save(t);
            log.info("Подозрительная транзакция с id - {} сохранена в базе данных",
                    getSuspiciousRepository().findByTransferId(t.getTransferId()).getId());
        } catch (Exception e) {
            log.error("Ошибка в сохранении подозрительной транзакции с transferId - {}", t.getTransferId());
        }
    }

    @Transactional
    public Optional<T> update(T t) {

        try {
            if (getSuspiciousRepository().existsById(t.getId())) {
                log.info("Подозрительная транзакция с id - {} сохраняется в базу данных",
                        getSuspiciousRepository().findByTransferId(t.getTransferId()).getId());
                return Optional.of(getSuspiciousRepository().save(t));
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("Ошибка в обновлении подозрительной транзакции с transferId - {}", t.getTransferId());
        }
        return Optional.empty();
    }

    public T read(long id) {
        Optional<T> suspiciousTransfer = getSuspiciousRepository().findById(id);
        return suspiciousTransfer.orElseThrow(() -> new SuspiciousAccountTransferNotFoundException("There is no SuspiciousAccountTransfer with ID - " + id));
    }

    public List<T> readAll() {
        return getSuspiciousRepository().findAll();
    }

    @Transactional
    public void delete(long id) {

        try {
            getSuspiciousRepository().deleteById(id);
            log.info("Подозрительная транзакция с id - {} удалена из базы данных", id);
        } catch (Exception e) {
            log.error("Ошибка в удалении подозрительной транзакции с id - {}", id);
        }
    }

    //Поиск подозрительной транзакции по id в таблице
    public boolean hasFoundSuspiciousTransfer(long id) {
        return Optional.ofNullable(getSuspiciousRepository().findByTransferId(id)).isEmpty();
    }

    //Поиск транзакции по номеру в таблице
    public boolean hasFoundTransfer(long number) {
        return Optional.ofNullable(getTransferRepository().findByNumber(number)).isEmpty();
    }

    //Логика обработки транзакции
    @Transactional
    public T checkAccountTransfer(S s) {
        //Для анализа достаем интересующую нас информацию из Transfer
        long accountNumber = s.getNumber();
        double amount = s.getAmount();
        String purpose = s.getPurpose();

        //Поля SuspiciousTransfer по умолчанию
        boolean isSuspicious = false;
        boolean isBlocked = false;
        String blockedReason = "Не заблокирован";
        String suspiciousReason = "Подозрений нет";

        //Логика обрабатывает 3 аспекта транзакции: сумма перевода, текст перевода и совершались ли ранее на этот номер переводы
        if ((amount > 500000) && hasFoundTransfer(accountNumber) || purpose.contains("Запрещенный текст")) {
            isBlocked = true;
            isSuspicious = true;
            suspiciousReason = "Большая сумма перевода и на этот номер ранее не было переводов";
            blockedReason = "Операция заблокирована из-за множества подозрений";
            if (purpose.contains("Запрещенный текст")) {
                suspiciousReason = "Запрещенный текст";
                blockedReason = "Запрещенный текст";
            }
        } else if (amount > 500000){
            isSuspicious = true;
            suspiciousReason = "Большая сумма перевода";
        } else if (hasFoundTransfer(accountNumber)) {
            isSuspicious = true;
            suspiciousReason = "На этот номер ранее не было переводов";
        }

        //Если транзакция с таким номером уже есть в таблице, то беру ее id и присваиваю новой транзакции (чтобы метод .save() обновил строку, а не создал новую)
        if (!hasFoundTransfer(accountNumber)) {
            long id = getTransferRepository().findByNumber(accountNumber).getId();
            s.setId(id);
        }

        try {
            getTransferRepository().save(s);
            log.info("Транзакции с id - {} успешно сохранена в базу данных", s.getId());
        }catch (Exception e) {
            log.error("Ошибка при сохранении транзакции с id - {}", s.getId());
        }

        //Заполняю сущность SuspiciousTransfer в соответствии с условием
        long accountTransferId = getTransferRepository().findByNumber(accountNumber).getId();

        T suspiciousTransfer = getEntity();
        suspiciousTransfer.setTransferId(accountTransferId);
        suspiciousTransfer.setSuspicious(isSuspicious);
        suspiciousTransfer.setBlocked(isBlocked);
        suspiciousTransfer.setBlockedReason(blockedReason);
        suspiciousTransfer.setSuspiciousReason(suspiciousReason);

        //Аналогично примеру выше, чтобы .save() обновлял строку
        if (!hasFoundSuspiciousTransfer(accountTransferId)) {
            long id = getSuspiciousRepository().findByTransferId(accountTransferId).getId();
            suspiciousTransfer.setId(id);
        }

        try {
            getSuspiciousRepository().save(suspiciousTransfer);
            log.info("Подозрительная транзакция с id - {} сохранена в базе данных",
                    getSuspiciousRepository().findByTransferId(suspiciousTransfer.getTransferId()).getId());
        } catch (Exception e) {
            log.error("Ошибка в сохранении подозрительной транзакции с transferId - {}", suspiciousTransfer.getTransferId());
        }

        return suspiciousTransfer;
    }

}
