package com.bank.profile.service.classes;

import com.bank.profile.exception.NotFoundExceptionEntity;
import com.bank.profile.mapper.interfaces.BaseMapper;
import com.bank.profile.model.BaseModel;
import com.bank.profile.service.interfaces.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;

@Slf4j
public abstract class BaseServiceImpl<Dto, Model extends BaseModel>
        implements BaseService<Dto, Model> {

    private final JpaRepository<Model, Long> repository;
    private final BaseMapper<Dto, Model> mapper;
    private final Class<?> clazz;

    public BaseServiceImpl(JpaRepository<Model, Long> repository, BaseMapper<Dto, Model> mapper, Class<?> clazz) {
        this.repository = repository;
        this.mapper = mapper;
        this.clazz = clazz;
    }

    @Override
    public Model getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundExceptionEntity("getById is null"));
    }

    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
            log.info(clazz.getSimpleName() + " с id - \"{}\" сохранен в базе данных", id);
        } catch (Exception e) {
            log.info(clazz.getSimpleName() + " с id - \"{}\" сохранен в базе данных", id);
            throw new NotFoundExceptionEntity("Отделения банка с заданным id не существует");
        }
    }

    @Override
    public void create(Dto dto) {
        Model model = mapper.toEntity(dto);
        Model save = repository.save(model);
        log.info(clazz.getSimpleName() + " с id - \"{}\" сохранен в базе данных", save.getId());
    }

    @Override
    public void update(Long id, Dto dto) {
        if (repository.existsById(id)) {
            Model model = mapper.toEntity(dto);
            model.setId(id);
            repository.save(model);
            log.info(clazz.getSimpleName() + " с id - \"{}\" обновлен в базе данных", id);
        } else {
            throw new NotFoundExceptionEntity("update is not found");
        }
    }
}