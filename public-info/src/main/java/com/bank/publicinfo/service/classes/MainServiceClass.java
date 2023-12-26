package com.bank.publicinfo.service.classes;

import com.bank.publicinfo.entity.MainEntity;
import com.bank.publicinfo.exception.NotFoundException;
import com.bank.publicinfo.mapper.MainMapper;
import com.bank.publicinfo.service.interfaces.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Slf4j
@RequiredArgsConstructor
@Transactional
public abstract class MainServiceClass<Dto, Entity extends MainEntity> implements MainService<Dto> {
    private final MainMapper<Dto, Entity> mapper;
    private final JpaRepository<Entity, Long> repository;
    private final Class<?> classEntity;

    @Override
    @Transactional(readOnly = true)
    public Dto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("Нет объекта типа {0} с id - \"{1}\"", classEntity.getSimpleName(), id))));
    }

    @Override
    public Dto save(Dto dto) {
        Entity entity = repository.save(mapper.toEntity(dto));
        log.info(MessageFormat.format("Объект типа {0} сохранен в базе данных под id - \"{1}\"", classEntity.getSimpleName(), entity.getId()));
        return mapper.toDto(entity);
    }

    @Override
    public Dto update(Long id, Dto dto) {
        Entity entity = mapper.toEntity(dto);
        entity.setId(id);
        entity = repository.save(entity);
        log.info(MessageFormat.format("Объект типа {0} с id - \"{1}\" обновлен в базе данных", classEntity.getSimpleName(), entity.getId()));
        return mapper.toDto(entity);
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
            log.info(MessageFormat.format("Объект типа {0} с id - \"{1}\" удален из базы данных", classEntity.getSimpleName(), id));
        } catch (Exception e) {
            log.error(MessageFormat.format("Объект типа {0} с id - \"{1}\" не существует в базе данных", classEntity.getSimpleName(), id));
            throw new NotFoundException("Объекта с заданным id не существует");
        }
    }
}
