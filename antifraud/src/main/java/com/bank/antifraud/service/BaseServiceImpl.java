package com.bank.antifraud.service;

import com.bank.antifraud.entity.abstractclasses.SuspiciousTransfer;
import com.bank.antifraud.entity.abstractclasses.Transfer;
import com.bank.antifraud.exception.SuspiciousTransferNotFoundException;
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
    public void update(T t) {

        try {
            log.info("Подозрительная транзакция с id - {} сохраняется в базу данных",
                    getSuspiciousRepository().findByTransferId(t.getTransferId()).getId());
            getSuspiciousRepository().save(t);
        } catch (Exception e) {
            log.error("Ошибка в обновлении подозрительной транзакции с transferId - {}", t.getTransferId());
        }
    }


    public T read(long id) {
        Optional<T> suspiciousTransfer = getSuspiciousRepository().findById(id);
        return suspiciousTransfer.orElseThrow(() -> new SuspiciousTransferNotFoundException("There is no SuspiciousAccountTransfer with ID - " + id));
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
        return Optional.ofNullable(getSuspiciousRepository().findByTransferId(id)).isPresent();
    }


    //Поиск транзакции по номеру в таблице
    public boolean hasFoundTransfer(long number) {
        return Optional.ofNullable(getTransferRepository().findByNumber(number)).isPresent();
    }


    //Логика обработки транзакции (на вход подается транзакция и информация о том, совершилсь ли ранее по ее номеру переводы)
    public T checkTransfer(S s, boolean isPresent) {
        //Достается необходимая для анализа информацию из транзакции
        double amount = s.getAmount();
        String purpose = s.getPurpose();

        //Поля SuspiciousTransfer по умолчанию
        boolean isSuspicious = false;
        boolean isBlocked = false;
        String blockedReason = "Не заблокирован";
        String suspiciousReason = "Подозрений нет";

        //Логика обрабатывает 3 аспекта транзакции: сумма перевода, текст перевода и совершались ли ранее на этот номер переводы
        if ((amount > 500000) && !isPresent || purpose.contains("Запрещенный текст")) {
            isBlocked = true;
            isSuspicious = true;
            suspiciousReason = "Большая сумма перевода и на этот номер ранее не было переводов";
            blockedReason = "Операция заблокирована из-за множества подозрений";
            if (purpose.contains("Запрещенный текст")) {
                suspiciousReason = "Запрещенный текст";
                blockedReason = "Запрещенный текст";
            }
        } else if (amount > 500000) {
            isSuspicious = true;
            suspiciousReason = "Большая сумма перевода";
        } else if (!isPresent) {
            isSuspicious = true;
            suspiciousReason = "На этот номер ранее не было переводов";
        }

        //Все информация о транзации заносится в сущность подозрительной транзакции
        T suspiciousTransfer = getEntity();
        suspiciousTransfer.setTransferId(s.getId());
        suspiciousTransfer.setSuspicious(isSuspicious);
        suspiciousTransfer.setBlocked(isBlocked);
        suspiciousTransfer.setBlockedReason(blockedReason);
        suspiciousTransfer.setSuspiciousReason(suspiciousReason);
        return suspiciousTransfer;
    }


    //Данный метод сохраняет транзакцию и результат ее обработки в базу данных
    @Transactional
    public T doOperationsWithTransfer(S s) {

        //Проверяется, была ли изначально транзакция с этим номером в базе данных
        boolean isPresent = hasFoundTransfer(s.getNumber());

        //Если транзакция с таким номером уже есть в таблице, то берется ее id и присваиваю новой транзакции (чтобы метод .save() обновил существующую строку, а не создал новую)
        if (isPresent) {
            s.setId(getTransferRepository().findByNumber(s.getNumber()).getId());
        }

        try {
            getTransferRepository().save(s);
            log.info("Транзакции с id - {} успешно сохранена в базу данных", s.getId());
        } catch (Exception e) {
            log.error("Ошибка при сохранении транзакции с id - {}", s.getId());
        }

        //Вызов метода обработки транзакции
        T suspiciousTransfer = checkTransfer(getTransferRepository().findByNumber(s.getNumber()), isPresent);

        //Аналогично примеру выше, чтобы .save() обновлял строку
        if (hasFoundSuspiciousTransfer(suspiciousTransfer.getTransferId())) {
            long id = getSuspiciousRepository().findByTransferId(suspiciousTransfer.getTransferId()).getId();
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
